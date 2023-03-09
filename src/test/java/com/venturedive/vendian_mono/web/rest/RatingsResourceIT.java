package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.PerformanceCycles;
import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.domain.RatingAttributes;
import com.venturedive.vendian_mono.domain.Ratings;
import com.venturedive.vendian_mono.repository.RatingsRepository;
import com.venturedive.vendian_mono.service.criteria.RatingsCriteria;
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
 * Integration tests for the {@link RatingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatingsResourceIT {

    private static final Integer DEFAULT_RATEEID = 1;
    private static final Integer UPDATED_RATEEID = 2;
    private static final Integer SMALLER_RATEEID = 1 - 1;

    private static final String DEFAULT_RATEETYPE = "AAAAAAAA";
    private static final String UPDATED_RATEETYPE = "BBBBBBBB";

    private static final Instant DEFAULT_DUEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_FREEZE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FREEZE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FREEZE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FREEZE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RatingsRepository ratingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingsMockMvc;

    private Ratings ratings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ratings createEntity(EntityManager em) {
        Ratings ratings = new Ratings()
            .rateeid(DEFAULT_RATEEID)
            .rateetype(DEFAULT_RATEETYPE)
            .duedate(DEFAULT_DUEDATE)
            .freeze(DEFAULT_FREEZE)
            .freezeContentType(DEFAULT_FREEZE_CONTENT_TYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return ratings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ratings createUpdatedEntity(EntityManager em) {
        Ratings ratings = new Ratings()
            .rateeid(UPDATED_RATEEID)
            .rateetype(UPDATED_RATEETYPE)
            .duedate(UPDATED_DUEDATE)
            .freeze(UPDATED_FREEZE)
            .freezeContentType(UPDATED_FREEZE_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return ratings;
    }

    @BeforeEach
    public void initTest() {
        ratings = createEntity(em);
    }

    @Test
    @Transactional
    void createRatings() throws Exception {
        int databaseSizeBeforeCreate = ratingsRepository.findAll().size();
        // Create the Ratings
        restRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isCreated());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeCreate + 1);
        Ratings testRatings = ratingsList.get(ratingsList.size() - 1);
        assertThat(testRatings.getRateeid()).isEqualTo(DEFAULT_RATEEID);
        assertThat(testRatings.getRateetype()).isEqualTo(DEFAULT_RATEETYPE);
        assertThat(testRatings.getDuedate()).isEqualTo(DEFAULT_DUEDATE);
        assertThat(testRatings.getFreeze()).isEqualTo(DEFAULT_FREEZE);
        assertThat(testRatings.getFreezeContentType()).isEqualTo(DEFAULT_FREEZE_CONTENT_TYPE);
        assertThat(testRatings.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRatings.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testRatings.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createRatingsWithExistingId() throws Exception {
        // Create the Ratings with an existing ID
        ratings.setId(1L);

        int databaseSizeBeforeCreate = ratingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingsRepository.findAll().size();
        // set the field null
        ratings.setCreatedat(null);

        // Create the Ratings, which fails.

        restRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isBadRequest());

        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingsRepository.findAll().size();
        // set the field null
        ratings.setUpdatedat(null);

        // Create the Ratings, which fails.

        restRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isBadRequest());

        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRatings() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList
        restRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratings.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateeid").value(hasItem(DEFAULT_RATEEID)))
            .andExpect(jsonPath("$.[*].rateetype").value(hasItem(DEFAULT_RATEETYPE)))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())))
            .andExpect(jsonPath("$.[*].freezeContentType").value(hasItem(DEFAULT_FREEZE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].freeze").value(hasItem(Base64Utils.encodeToString(DEFAULT_FREEZE))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    @Transactional
    void getRatings() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get the ratings
        restRatingsMockMvc
            .perform(get(ENTITY_API_URL_ID, ratings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ratings.getId().intValue()))
            .andExpect(jsonPath("$.rateeid").value(DEFAULT_RATEEID))
            .andExpect(jsonPath("$.rateetype").value(DEFAULT_RATEETYPE))
            .andExpect(jsonPath("$.duedate").value(DEFAULT_DUEDATE.toString()))
            .andExpect(jsonPath("$.freezeContentType").value(DEFAULT_FREEZE_CONTENT_TYPE))
            .andExpect(jsonPath("$.freeze").value(Base64Utils.encodeToString(DEFAULT_FREEZE)))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getRatingsByIdFiltering() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        Long id = ratings.getId();

        defaultRatingsShouldBeFound("id.equals=" + id);
        defaultRatingsShouldNotBeFound("id.notEquals=" + id);

        defaultRatingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRatingsShouldNotBeFound("id.greaterThan=" + id);

        defaultRatingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRatingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRatingsByRateeidIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateeid equals to DEFAULT_RATEEID
        defaultRatingsShouldBeFound("rateeid.equals=" + DEFAULT_RATEEID);

        // Get all the ratingsList where rateeid equals to UPDATED_RATEEID
        defaultRatingsShouldNotBeFound("rateeid.equals=" + UPDATED_RATEEID);
    }

    @Test
    @Transactional
    void getAllRatingsByRateeidIsInShouldWork() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateeid in DEFAULT_RATEEID or UPDATED_RATEEID
        defaultRatingsShouldBeFound("rateeid.in=" + DEFAULT_RATEEID + "," + UPDATED_RATEEID);

        // Get all the ratingsList where rateeid equals to UPDATED_RATEEID
        defaultRatingsShouldNotBeFound("rateeid.in=" + UPDATED_RATEEID);
    }

    @Test
    @Transactional
    void getAllRatingsByRateeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateeid is not null
        defaultRatingsShouldBeFound("rateeid.specified=true");

        // Get all the ratingsList where rateeid is null
        defaultRatingsShouldNotBeFound("rateeid.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingsByRateeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateeid is greater than or equal to DEFAULT_RATEEID
        defaultRatingsShouldBeFound("rateeid.greaterThanOrEqual=" + DEFAULT_RATEEID);

        // Get all the ratingsList where rateeid is greater than or equal to UPDATED_RATEEID
        defaultRatingsShouldNotBeFound("rateeid.greaterThanOrEqual=" + UPDATED_RATEEID);
    }

    @Test
    @Transactional
    void getAllRatingsByRateeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateeid is less than or equal to DEFAULT_RATEEID
        defaultRatingsShouldBeFound("rateeid.lessThanOrEqual=" + DEFAULT_RATEEID);

        // Get all the ratingsList where rateeid is less than or equal to SMALLER_RATEEID
        defaultRatingsShouldNotBeFound("rateeid.lessThanOrEqual=" + SMALLER_RATEEID);
    }

    @Test
    @Transactional
    void getAllRatingsByRateeidIsLessThanSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateeid is less than DEFAULT_RATEEID
        defaultRatingsShouldNotBeFound("rateeid.lessThan=" + DEFAULT_RATEEID);

        // Get all the ratingsList where rateeid is less than UPDATED_RATEEID
        defaultRatingsShouldBeFound("rateeid.lessThan=" + UPDATED_RATEEID);
    }

    @Test
    @Transactional
    void getAllRatingsByRateeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateeid is greater than DEFAULT_RATEEID
        defaultRatingsShouldNotBeFound("rateeid.greaterThan=" + DEFAULT_RATEEID);

        // Get all the ratingsList where rateeid is greater than SMALLER_RATEEID
        defaultRatingsShouldBeFound("rateeid.greaterThan=" + SMALLER_RATEEID);
    }

    @Test
    @Transactional
    void getAllRatingsByRateetypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateetype equals to DEFAULT_RATEETYPE
        defaultRatingsShouldBeFound("rateetype.equals=" + DEFAULT_RATEETYPE);

        // Get all the ratingsList where rateetype equals to UPDATED_RATEETYPE
        defaultRatingsShouldNotBeFound("rateetype.equals=" + UPDATED_RATEETYPE);
    }

    @Test
    @Transactional
    void getAllRatingsByRateetypeIsInShouldWork() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateetype in DEFAULT_RATEETYPE or UPDATED_RATEETYPE
        defaultRatingsShouldBeFound("rateetype.in=" + DEFAULT_RATEETYPE + "," + UPDATED_RATEETYPE);

        // Get all the ratingsList where rateetype equals to UPDATED_RATEETYPE
        defaultRatingsShouldNotBeFound("rateetype.in=" + UPDATED_RATEETYPE);
    }

    @Test
    @Transactional
    void getAllRatingsByRateetypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateetype is not null
        defaultRatingsShouldBeFound("rateetype.specified=true");

        // Get all the ratingsList where rateetype is null
        defaultRatingsShouldNotBeFound("rateetype.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingsByRateetypeContainsSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateetype contains DEFAULT_RATEETYPE
        defaultRatingsShouldBeFound("rateetype.contains=" + DEFAULT_RATEETYPE);

        // Get all the ratingsList where rateetype contains UPDATED_RATEETYPE
        defaultRatingsShouldNotBeFound("rateetype.contains=" + UPDATED_RATEETYPE);
    }

    @Test
    @Transactional
    void getAllRatingsByRateetypeNotContainsSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where rateetype does not contain DEFAULT_RATEETYPE
        defaultRatingsShouldNotBeFound("rateetype.doesNotContain=" + DEFAULT_RATEETYPE);

        // Get all the ratingsList where rateetype does not contain UPDATED_RATEETYPE
        defaultRatingsShouldBeFound("rateetype.doesNotContain=" + UPDATED_RATEETYPE);
    }

    @Test
    @Transactional
    void getAllRatingsByDuedateIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where duedate equals to DEFAULT_DUEDATE
        defaultRatingsShouldBeFound("duedate.equals=" + DEFAULT_DUEDATE);

        // Get all the ratingsList where duedate equals to UPDATED_DUEDATE
        defaultRatingsShouldNotBeFound("duedate.equals=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllRatingsByDuedateIsInShouldWork() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where duedate in DEFAULT_DUEDATE or UPDATED_DUEDATE
        defaultRatingsShouldBeFound("duedate.in=" + DEFAULT_DUEDATE + "," + UPDATED_DUEDATE);

        // Get all the ratingsList where duedate equals to UPDATED_DUEDATE
        defaultRatingsShouldNotBeFound("duedate.in=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllRatingsByDuedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where duedate is not null
        defaultRatingsShouldBeFound("duedate.specified=true");

        // Get all the ratingsList where duedate is null
        defaultRatingsShouldNotBeFound("duedate.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where createdat equals to DEFAULT_CREATEDAT
        defaultRatingsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the ratingsList where createdat equals to UPDATED_CREATEDAT
        defaultRatingsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRatingsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultRatingsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the ratingsList where createdat equals to UPDATED_CREATEDAT
        defaultRatingsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRatingsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where createdat is not null
        defaultRatingsShouldBeFound("createdat.specified=true");

        // Get all the ratingsList where createdat is null
        defaultRatingsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultRatingsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the ratingsList where updatedat equals to UPDATED_UPDATEDAT
        defaultRatingsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRatingsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultRatingsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the ratingsList where updatedat equals to UPDATED_UPDATEDAT
        defaultRatingsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRatingsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where updatedat is not null
        defaultRatingsShouldBeFound("updatedat.specified=true");

        // Get all the ratingsList where updatedat is null
        defaultRatingsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where deletedat equals to DEFAULT_DELETEDAT
        defaultRatingsShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the ratingsList where deletedat equals to UPDATED_DELETEDAT
        defaultRatingsShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllRatingsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultRatingsShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the ratingsList where deletedat equals to UPDATED_DELETEDAT
        defaultRatingsShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllRatingsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        // Get all the ratingsList where deletedat is not null
        defaultRatingsShouldBeFound("deletedat.specified=true");

        // Get all the ratingsList where deletedat is null
        defaultRatingsShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingsByRaterIsEqualToSomething() throws Exception {
        Employees rater;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            ratingsRepository.saveAndFlush(ratings);
            rater = EmployeesResourceIT.createEntity(em);
        } else {
            rater = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(rater);
        em.flush();
        ratings.setRater(rater);
        ratingsRepository.saveAndFlush(ratings);
        Long raterId = rater.getId();

        // Get all the ratingsList where rater equals to raterId
        defaultRatingsShouldBeFound("raterId.equals=" + raterId);

        // Get all the ratingsList where rater equals to (raterId + 1)
        defaultRatingsShouldNotBeFound("raterId.equals=" + (raterId + 1));
    }

    @Test
    @Transactional
    void getAllRatingsByRatingattributesRatingIsEqualToSomething() throws Exception {
        RatingAttributes ratingattributesRating;
        if (TestUtil.findAll(em, RatingAttributes.class).isEmpty()) {
            ratingsRepository.saveAndFlush(ratings);
            ratingattributesRating = RatingAttributesResourceIT.createEntity(em);
        } else {
            ratingattributesRating = TestUtil.findAll(em, RatingAttributes.class).get(0);
        }
        em.persist(ratingattributesRating);
        em.flush();
        ratings.addRatingattributesRating(ratingattributesRating);
        ratingsRepository.saveAndFlush(ratings);
        Long ratingattributesRatingId = ratingattributesRating.getId();

        // Get all the ratingsList where ratingattributesRating equals to ratingattributesRatingId
        defaultRatingsShouldBeFound("ratingattributesRatingId.equals=" + ratingattributesRatingId);

        // Get all the ratingsList where ratingattributesRating equals to (ratingattributesRatingId + 1)
        defaultRatingsShouldNotBeFound("ratingattributesRatingId.equals=" + (ratingattributesRatingId + 1));
    }

    @Test
    @Transactional
    void getAllRatingsByPerformancecycleIsEqualToSomething() throws Exception {
        PerformanceCycles performancecycle;
        if (TestUtil.findAll(em, PerformanceCycles.class).isEmpty()) {
            ratingsRepository.saveAndFlush(ratings);
            performancecycle = PerformanceCyclesResourceIT.createEntity(em);
        } else {
            performancecycle = TestUtil.findAll(em, PerformanceCycles.class).get(0);
        }
        em.persist(performancecycle);
        em.flush();
        ratings.addPerformancecycle(performancecycle);
        ratingsRepository.saveAndFlush(ratings);
        Long performancecycleId = performancecycle.getId();

        // Get all the ratingsList where performancecycle equals to performancecycleId
        defaultRatingsShouldBeFound("performancecycleId.equals=" + performancecycleId);

        // Get all the ratingsList where performancecycle equals to (performancecycleId + 1)
        defaultRatingsShouldNotBeFound("performancecycleId.equals=" + (performancecycleId + 1));
    }

    @Test
    @Transactional
    void getAllRatingsByProjectcycleIsEqualToSomething() throws Exception {
        ProjectCycles projectcycle;
        if (TestUtil.findAll(em, ProjectCycles.class).isEmpty()) {
            ratingsRepository.saveAndFlush(ratings);
            projectcycle = ProjectCyclesResourceIT.createEntity(em);
        } else {
            projectcycle = TestUtil.findAll(em, ProjectCycles.class).get(0);
        }
        em.persist(projectcycle);
        em.flush();
        ratings.addProjectcycle(projectcycle);
        ratingsRepository.saveAndFlush(ratings);
        Long projectcycleId = projectcycle.getId();

        // Get all the ratingsList where projectcycle equals to projectcycleId
        defaultRatingsShouldBeFound("projectcycleId.equals=" + projectcycleId);

        // Get all the ratingsList where projectcycle equals to (projectcycleId + 1)
        defaultRatingsShouldNotBeFound("projectcycleId.equals=" + (projectcycleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRatingsShouldBeFound(String filter) throws Exception {
        restRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratings.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateeid").value(hasItem(DEFAULT_RATEEID)))
            .andExpect(jsonPath("$.[*].rateetype").value(hasItem(DEFAULT_RATEETYPE)))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())))
            .andExpect(jsonPath("$.[*].freezeContentType").value(hasItem(DEFAULT_FREEZE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].freeze").value(hasItem(Base64Utils.encodeToString(DEFAULT_FREEZE))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restRatingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRatingsShouldNotBeFound(String filter) throws Exception {
        restRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRatingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRatings() throws Exception {
        // Get the ratings
        restRatingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRatings() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();

        // Update the ratings
        Ratings updatedRatings = ratingsRepository.findById(ratings.getId()).get();
        // Disconnect from session so that the updates on updatedRatings are not directly saved in db
        em.detach(updatedRatings);
        updatedRatings
            .rateeid(UPDATED_RATEEID)
            .rateetype(UPDATED_RATEETYPE)
            .duedate(UPDATED_DUEDATE)
            .freeze(UPDATED_FREEZE)
            .freezeContentType(UPDATED_FREEZE_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRatings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRatings))
            )
            .andExpect(status().isOk());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
        Ratings testRatings = ratingsList.get(ratingsList.size() - 1);
        assertThat(testRatings.getRateeid()).isEqualTo(UPDATED_RATEEID);
        assertThat(testRatings.getRateetype()).isEqualTo(UPDATED_RATEETYPE);
        assertThat(testRatings.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testRatings.getFreeze()).isEqualTo(UPDATED_FREEZE);
        assertThat(testRatings.getFreezeContentType()).isEqualTo(UPDATED_FREEZE_CONTENT_TYPE);
        assertThat(testRatings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRatings.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testRatings.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingRatings() throws Exception {
        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();
        ratings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRatings() throws Exception {
        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();
        ratings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRatings() throws Exception {
        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();
        ratings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingsMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRatingsWithPatch() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();

        // Update the ratings using partial update
        Ratings partialUpdatedRatings = new Ratings();
        partialUpdatedRatings.setId(ratings.getId());

        partialUpdatedRatings
            .rateeid(UPDATED_RATEEID)
            .rateetype(UPDATED_RATEETYPE)
            .duedate(UPDATED_DUEDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatings))
            )
            .andExpect(status().isOk());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
        Ratings testRatings = ratingsList.get(ratingsList.size() - 1);
        assertThat(testRatings.getRateeid()).isEqualTo(UPDATED_RATEEID);
        assertThat(testRatings.getRateetype()).isEqualTo(UPDATED_RATEETYPE);
        assertThat(testRatings.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testRatings.getFreeze()).isEqualTo(DEFAULT_FREEZE);
        assertThat(testRatings.getFreezeContentType()).isEqualTo(DEFAULT_FREEZE_CONTENT_TYPE);
        assertThat(testRatings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRatings.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testRatings.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateRatingsWithPatch() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();

        // Update the ratings using partial update
        Ratings partialUpdatedRatings = new Ratings();
        partialUpdatedRatings.setId(ratings.getId());

        partialUpdatedRatings
            .rateeid(UPDATED_RATEEID)
            .rateetype(UPDATED_RATEETYPE)
            .duedate(UPDATED_DUEDATE)
            .freeze(UPDATED_FREEZE)
            .freezeContentType(UPDATED_FREEZE_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatings))
            )
            .andExpect(status().isOk());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
        Ratings testRatings = ratingsList.get(ratingsList.size() - 1);
        assertThat(testRatings.getRateeid()).isEqualTo(UPDATED_RATEEID);
        assertThat(testRatings.getRateetype()).isEqualTo(UPDATED_RATEETYPE);
        assertThat(testRatings.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testRatings.getFreeze()).isEqualTo(UPDATED_FREEZE);
        assertThat(testRatings.getFreezeContentType()).isEqualTo(UPDATED_FREEZE_CONTENT_TYPE);
        assertThat(testRatings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRatings.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testRatings.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingRatings() throws Exception {
        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();
        ratings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRatings() throws Exception {
        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();
        ratings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRatings() throws Exception {
        int databaseSizeBeforeUpdate = ratingsRepository.findAll().size();
        ratings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ratings in the database
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRatings() throws Exception {
        // Initialize the database
        ratingsRepository.saveAndFlush(ratings);

        int databaseSizeBeforeDelete = ratingsRepository.findAll().size();

        // Delete the ratings
        restRatingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ratings.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ratings> ratingsList = ratingsRepository.findAll();
        assertThat(ratingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
