package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.FeedbackEmails;
import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import com.venturedive.vendian_mono.repository.FeedbackEmailsRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackEmailsCriteria;
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
 * Integration tests for the {@link FeedbackEmailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeedbackEmailsResourceIT {

    private static final String DEFAULT_TO = "AAAAAAAAAA";
    private static final String UPDATED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final Instant DEFAULT_SENTAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SENTAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/feedback-emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeedbackEmailsRepository feedbackEmailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackEmailsMockMvc;

    private FeedbackEmails feedbackEmails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackEmails createEntity(EntityManager em) {
        FeedbackEmails feedbackEmails = new FeedbackEmails()
            .to(DEFAULT_TO)
            .body(DEFAULT_BODY)
            .status(DEFAULT_STATUS)
            .sentat(DEFAULT_SENTAT)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return feedbackEmails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackEmails createUpdatedEntity(EntityManager em) {
        FeedbackEmails feedbackEmails = new FeedbackEmails()
            .to(UPDATED_TO)
            .body(UPDATED_BODY)
            .status(UPDATED_STATUS)
            .sentat(UPDATED_SENTAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return feedbackEmails;
    }

    @BeforeEach
    public void initTest() {
        feedbackEmails = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedbackEmails() throws Exception {
        int databaseSizeBeforeCreate = feedbackEmailsRepository.findAll().size();
        // Create the FeedbackEmails
        restFeedbackEmailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isCreated());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeCreate + 1);
        FeedbackEmails testFeedbackEmails = feedbackEmailsList.get(feedbackEmailsList.size() - 1);
        assertThat(testFeedbackEmails.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testFeedbackEmails.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testFeedbackEmails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFeedbackEmails.getSentat()).isEqualTo(DEFAULT_SENTAT);
        assertThat(testFeedbackEmails.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFeedbackEmails.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createFeedbackEmailsWithExistingId() throws Exception {
        // Create the FeedbackEmails with an existing ID
        feedbackEmails.setId(1L);

        int databaseSizeBeforeCreate = feedbackEmailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackEmailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackEmailsRepository.findAll().size();
        // set the field null
        feedbackEmails.setCreatedat(null);

        // Create the FeedbackEmails, which fails.

        restFeedbackEmailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackEmailsRepository.findAll().size();
        // set the field null
        feedbackEmails.setUpdatedat(null);

        // Create the FeedbackEmails, which fails.

        restFeedbackEmailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeedbackEmails() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList
        restFeedbackEmailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackEmails.getId().intValue())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sentat").value(hasItem(DEFAULT_SENTAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getFeedbackEmails() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get the feedbackEmails
        restFeedbackEmailsMockMvc
            .perform(get(ENTITY_API_URL_ID, feedbackEmails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedbackEmails.getId().intValue()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.sentat").value(DEFAULT_SENTAT.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getFeedbackEmailsByIdFiltering() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        Long id = feedbackEmails.getId();

        defaultFeedbackEmailsShouldBeFound("id.equals=" + id);
        defaultFeedbackEmailsShouldNotBeFound("id.notEquals=" + id);

        defaultFeedbackEmailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedbackEmailsShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedbackEmailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedbackEmailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByToIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where to equals to DEFAULT_TO
        defaultFeedbackEmailsShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the feedbackEmailsList where to equals to UPDATED_TO
        defaultFeedbackEmailsShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByToIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where to in DEFAULT_TO or UPDATED_TO
        defaultFeedbackEmailsShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the feedbackEmailsList where to equals to UPDATED_TO
        defaultFeedbackEmailsShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where to is not null
        defaultFeedbackEmailsShouldBeFound("to.specified=true");

        // Get all the feedbackEmailsList where to is null
        defaultFeedbackEmailsShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByToContainsSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where to contains DEFAULT_TO
        defaultFeedbackEmailsShouldBeFound("to.contains=" + DEFAULT_TO);

        // Get all the feedbackEmailsList where to contains UPDATED_TO
        defaultFeedbackEmailsShouldNotBeFound("to.contains=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByToNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where to does not contain DEFAULT_TO
        defaultFeedbackEmailsShouldNotBeFound("to.doesNotContain=" + DEFAULT_TO);

        // Get all the feedbackEmailsList where to does not contain UPDATED_TO
        defaultFeedbackEmailsShouldBeFound("to.doesNotContain=" + UPDATED_TO);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where body equals to DEFAULT_BODY
        defaultFeedbackEmailsShouldBeFound("body.equals=" + DEFAULT_BODY);

        // Get all the feedbackEmailsList where body equals to UPDATED_BODY
        defaultFeedbackEmailsShouldNotBeFound("body.equals=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByBodyIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where body in DEFAULT_BODY or UPDATED_BODY
        defaultFeedbackEmailsShouldBeFound("body.in=" + DEFAULT_BODY + "," + UPDATED_BODY);

        // Get all the feedbackEmailsList where body equals to UPDATED_BODY
        defaultFeedbackEmailsShouldNotBeFound("body.in=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where body is not null
        defaultFeedbackEmailsShouldBeFound("body.specified=true");

        // Get all the feedbackEmailsList where body is null
        defaultFeedbackEmailsShouldNotBeFound("body.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByBodyContainsSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where body contains DEFAULT_BODY
        defaultFeedbackEmailsShouldBeFound("body.contains=" + DEFAULT_BODY);

        // Get all the feedbackEmailsList where body contains UPDATED_BODY
        defaultFeedbackEmailsShouldNotBeFound("body.contains=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByBodyNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where body does not contain DEFAULT_BODY
        defaultFeedbackEmailsShouldNotBeFound("body.doesNotContain=" + DEFAULT_BODY);

        // Get all the feedbackEmailsList where body does not contain UPDATED_BODY
        defaultFeedbackEmailsShouldBeFound("body.doesNotContain=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where status equals to DEFAULT_STATUS
        defaultFeedbackEmailsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the feedbackEmailsList where status equals to UPDATED_STATUS
        defaultFeedbackEmailsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFeedbackEmailsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the feedbackEmailsList where status equals to UPDATED_STATUS
        defaultFeedbackEmailsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where status is not null
        defaultFeedbackEmailsShouldBeFound("status.specified=true");

        // Get all the feedbackEmailsList where status is null
        defaultFeedbackEmailsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where status is greater than or equal to DEFAULT_STATUS
        defaultFeedbackEmailsShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the feedbackEmailsList where status is greater than or equal to UPDATED_STATUS
        defaultFeedbackEmailsShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where status is less than or equal to DEFAULT_STATUS
        defaultFeedbackEmailsShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the feedbackEmailsList where status is less than or equal to SMALLER_STATUS
        defaultFeedbackEmailsShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where status is less than DEFAULT_STATUS
        defaultFeedbackEmailsShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the feedbackEmailsList where status is less than UPDATED_STATUS
        defaultFeedbackEmailsShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where status is greater than DEFAULT_STATUS
        defaultFeedbackEmailsShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the feedbackEmailsList where status is greater than SMALLER_STATUS
        defaultFeedbackEmailsShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsBySentatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where sentat equals to DEFAULT_SENTAT
        defaultFeedbackEmailsShouldBeFound("sentat.equals=" + DEFAULT_SENTAT);

        // Get all the feedbackEmailsList where sentat equals to UPDATED_SENTAT
        defaultFeedbackEmailsShouldNotBeFound("sentat.equals=" + UPDATED_SENTAT);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsBySentatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where sentat in DEFAULT_SENTAT or UPDATED_SENTAT
        defaultFeedbackEmailsShouldBeFound("sentat.in=" + DEFAULT_SENTAT + "," + UPDATED_SENTAT);

        // Get all the feedbackEmailsList where sentat equals to UPDATED_SENTAT
        defaultFeedbackEmailsShouldNotBeFound("sentat.in=" + UPDATED_SENTAT);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsBySentatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where sentat is not null
        defaultFeedbackEmailsShouldBeFound("sentat.specified=true");

        // Get all the feedbackEmailsList where sentat is null
        defaultFeedbackEmailsShouldNotBeFound("sentat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where createdat equals to DEFAULT_CREATEDAT
        defaultFeedbackEmailsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the feedbackEmailsList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackEmailsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultFeedbackEmailsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the feedbackEmailsList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackEmailsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where createdat is not null
        defaultFeedbackEmailsShouldBeFound("createdat.specified=true");

        // Get all the feedbackEmailsList where createdat is null
        defaultFeedbackEmailsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultFeedbackEmailsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the feedbackEmailsList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackEmailsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultFeedbackEmailsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the feedbackEmailsList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackEmailsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        // Get all the feedbackEmailsList where updatedat is not null
        defaultFeedbackEmailsShouldBeFound("updatedat.specified=true");

        // Get all the feedbackEmailsList where updatedat is null
        defaultFeedbackEmailsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackEmailsByRespondentIsEqualToSomething() throws Exception {
        FeedbackRespondents respondent;
        if (TestUtil.findAll(em, FeedbackRespondents.class).isEmpty()) {
            feedbackEmailsRepository.saveAndFlush(feedbackEmails);
            respondent = FeedbackRespondentsResourceIT.createEntity(em);
        } else {
            respondent = TestUtil.findAll(em, FeedbackRespondents.class).get(0);
        }
        em.persist(respondent);
        em.flush();
        feedbackEmails.setRespondent(respondent);
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);
        Long respondentId = respondent.getId();

        // Get all the feedbackEmailsList where respondent equals to respondentId
        defaultFeedbackEmailsShouldBeFound("respondentId.equals=" + respondentId);

        // Get all the feedbackEmailsList where respondent equals to (respondentId + 1)
        defaultFeedbackEmailsShouldNotBeFound("respondentId.equals=" + (respondentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedbackEmailsShouldBeFound(String filter) throws Exception {
        restFeedbackEmailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackEmails.getId().intValue())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sentat").value(hasItem(DEFAULT_SENTAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restFeedbackEmailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedbackEmailsShouldNotBeFound(String filter) throws Exception {
        restFeedbackEmailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedbackEmailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeedbackEmails() throws Exception {
        // Get the feedbackEmails
        restFeedbackEmailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedbackEmails() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();

        // Update the feedbackEmails
        FeedbackEmails updatedFeedbackEmails = feedbackEmailsRepository.findById(feedbackEmails.getId()).get();
        // Disconnect from session so that the updates on updatedFeedbackEmails are not directly saved in db
        em.detach(updatedFeedbackEmails);
        updatedFeedbackEmails
            .to(UPDATED_TO)
            .body(UPDATED_BODY)
            .status(UPDATED_STATUS)
            .sentat(UPDATED_SENTAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedbackEmails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeedbackEmails))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackEmails testFeedbackEmails = feedbackEmailsList.get(feedbackEmailsList.size() - 1);
        assertThat(testFeedbackEmails.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testFeedbackEmails.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testFeedbackEmails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeedbackEmails.getSentat()).isEqualTo(UPDATED_SENTAT);
        assertThat(testFeedbackEmails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackEmails.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingFeedbackEmails() throws Exception {
        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();
        feedbackEmails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedbackEmails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedbackEmails() throws Exception {
        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();
        feedbackEmails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedbackEmails() throws Exception {
        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();
        feedbackEmails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackEmailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackEmailsWithPatch() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();

        // Update the feedbackEmails using partial update
        FeedbackEmails partialUpdatedFeedbackEmails = new FeedbackEmails();
        partialUpdatedFeedbackEmails.setId(feedbackEmails.getId());

        partialUpdatedFeedbackEmails.to(UPDATED_TO).sentat(UPDATED_SENTAT);

        restFeedbackEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackEmails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackEmails))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackEmails testFeedbackEmails = feedbackEmailsList.get(feedbackEmailsList.size() - 1);
        assertThat(testFeedbackEmails.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testFeedbackEmails.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testFeedbackEmails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFeedbackEmails.getSentat()).isEqualTo(UPDATED_SENTAT);
        assertThat(testFeedbackEmails.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFeedbackEmails.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateFeedbackEmailsWithPatch() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();

        // Update the feedbackEmails using partial update
        FeedbackEmails partialUpdatedFeedbackEmails = new FeedbackEmails();
        partialUpdatedFeedbackEmails.setId(feedbackEmails.getId());

        partialUpdatedFeedbackEmails
            .to(UPDATED_TO)
            .body(UPDATED_BODY)
            .status(UPDATED_STATUS)
            .sentat(UPDATED_SENTAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackEmails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackEmails))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
        FeedbackEmails testFeedbackEmails = feedbackEmailsList.get(feedbackEmailsList.size() - 1);
        assertThat(testFeedbackEmails.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testFeedbackEmails.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testFeedbackEmails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeedbackEmails.getSentat()).isEqualTo(UPDATED_SENTAT);
        assertThat(testFeedbackEmails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackEmails.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingFeedbackEmails() throws Exception {
        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();
        feedbackEmails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedbackEmails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedbackEmails() throws Exception {
        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();
        feedbackEmails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedbackEmails() throws Exception {
        int databaseSizeBeforeUpdate = feedbackEmailsRepository.findAll().size();
        feedbackEmails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackEmails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackEmails in the database
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedbackEmails() throws Exception {
        // Initialize the database
        feedbackEmailsRepository.saveAndFlush(feedbackEmails);

        int databaseSizeBeforeDelete = feedbackEmailsRepository.findAll().size();

        // Delete the feedbackEmails
        restFeedbackEmailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedbackEmails.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedbackEmails> feedbackEmailsList = feedbackEmailsRepository.findAll();
        assertThat(feedbackEmailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
