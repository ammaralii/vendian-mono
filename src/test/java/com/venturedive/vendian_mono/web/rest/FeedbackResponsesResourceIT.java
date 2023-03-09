package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.FeedbackQuestions;
import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import com.venturedive.vendian_mono.domain.FeedbackResponses;
import com.venturedive.vendian_mono.repository.FeedbackResponsesRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackResponsesCriteria;
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
 * Integration tests for the {@link FeedbackResponsesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeedbackResponsesResourceIT {

    private static final byte[] DEFAULT_ANSWER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANSWER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ANSWER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANSWER_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_RATING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RATING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RATING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RATING_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/feedback-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeedbackResponsesRepository feedbackResponsesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackResponsesMockMvc;

    private FeedbackResponses feedbackResponses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackResponses createEntity(EntityManager em) {
        FeedbackResponses feedbackResponses = new FeedbackResponses()
            .answer(DEFAULT_ANSWER)
            .answerContentType(DEFAULT_ANSWER_CONTENT_TYPE)
            .rating(DEFAULT_RATING)
            .ratingContentType(DEFAULT_RATING_CONTENT_TYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return feedbackResponses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedbackResponses createUpdatedEntity(EntityManager em) {
        FeedbackResponses feedbackResponses = new FeedbackResponses()
            .answer(UPDATED_ANSWER)
            .answerContentType(UPDATED_ANSWER_CONTENT_TYPE)
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return feedbackResponses;
    }

    @BeforeEach
    public void initTest() {
        feedbackResponses = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedbackResponses() throws Exception {
        int databaseSizeBeforeCreate = feedbackResponsesRepository.findAll().size();
        // Create the FeedbackResponses
        restFeedbackResponsesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isCreated());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeCreate + 1);
        FeedbackResponses testFeedbackResponses = feedbackResponsesList.get(feedbackResponsesList.size() - 1);
        assertThat(testFeedbackResponses.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testFeedbackResponses.getAnswerContentType()).isEqualTo(DEFAULT_ANSWER_CONTENT_TYPE);
        assertThat(testFeedbackResponses.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testFeedbackResponses.getRatingContentType()).isEqualTo(DEFAULT_RATING_CONTENT_TYPE);
        assertThat(testFeedbackResponses.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFeedbackResponses.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createFeedbackResponsesWithExistingId() throws Exception {
        // Create the FeedbackResponses with an existing ID
        feedbackResponses.setId(1L);

        int databaseSizeBeforeCreate = feedbackResponsesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackResponsesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackResponsesRepository.findAll().size();
        // set the field null
        feedbackResponses.setCreatedat(null);

        // Create the FeedbackResponses, which fails.

        restFeedbackResponsesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackResponsesRepository.findAll().size();
        // set the field null
        feedbackResponses.setUpdatedat(null);

        // Create the FeedbackResponses, which fails.

        restFeedbackResponsesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isBadRequest());

        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeedbackResponses() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        // Get all the feedbackResponsesList
        restFeedbackResponsesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackResponses.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerContentType").value(hasItem(DEFAULT_ANSWER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANSWER))))
            .andExpect(jsonPath("$.[*].ratingContentType").value(hasItem(DEFAULT_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(Base64Utils.encodeToString(DEFAULT_RATING))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getFeedbackResponses() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        // Get the feedbackResponses
        restFeedbackResponsesMockMvc
            .perform(get(ENTITY_API_URL_ID, feedbackResponses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedbackResponses.getId().intValue()))
            .andExpect(jsonPath("$.answerContentType").value(DEFAULT_ANSWER_CONTENT_TYPE))
            .andExpect(jsonPath("$.answer").value(Base64Utils.encodeToString(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.ratingContentType").value(DEFAULT_RATING_CONTENT_TYPE))
            .andExpect(jsonPath("$.rating").value(Base64Utils.encodeToString(DEFAULT_RATING)))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getFeedbackResponsesByIdFiltering() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        Long id = feedbackResponses.getId();

        defaultFeedbackResponsesShouldBeFound("id.equals=" + id);
        defaultFeedbackResponsesShouldNotBeFound("id.notEquals=" + id);

        defaultFeedbackResponsesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedbackResponsesShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedbackResponsesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedbackResponsesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeedbackResponsesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        // Get all the feedbackResponsesList where createdat equals to DEFAULT_CREATEDAT
        defaultFeedbackResponsesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the feedbackResponsesList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackResponsesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackResponsesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        // Get all the feedbackResponsesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultFeedbackResponsesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the feedbackResponsesList where createdat equals to UPDATED_CREATEDAT
        defaultFeedbackResponsesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackResponsesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        // Get all the feedbackResponsesList where createdat is not null
        defaultFeedbackResponsesShouldBeFound("createdat.specified=true");

        // Get all the feedbackResponsesList where createdat is null
        defaultFeedbackResponsesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackResponsesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        // Get all the feedbackResponsesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultFeedbackResponsesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the feedbackResponsesList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackResponsesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackResponsesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        // Get all the feedbackResponsesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultFeedbackResponsesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the feedbackResponsesList where updatedat equals to UPDATED_UPDATEDAT
        defaultFeedbackResponsesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllFeedbackResponsesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        // Get all the feedbackResponsesList where updatedat is not null
        defaultFeedbackResponsesShouldBeFound("updatedat.specified=true");

        // Get all the feedbackResponsesList where updatedat is null
        defaultFeedbackResponsesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbackResponsesByRespondentIsEqualToSomething() throws Exception {
        FeedbackRespondents respondent;
        if (TestUtil.findAll(em, FeedbackRespondents.class).isEmpty()) {
            feedbackResponsesRepository.saveAndFlush(feedbackResponses);
            respondent = FeedbackRespondentsResourceIT.createEntity(em);
        } else {
            respondent = TestUtil.findAll(em, FeedbackRespondents.class).get(0);
        }
        em.persist(respondent);
        em.flush();
        feedbackResponses.setRespondent(respondent);
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);
        Long respondentId = respondent.getId();

        // Get all the feedbackResponsesList where respondent equals to respondentId
        defaultFeedbackResponsesShouldBeFound("respondentId.equals=" + respondentId);

        // Get all the feedbackResponsesList where respondent equals to (respondentId + 1)
        defaultFeedbackResponsesShouldNotBeFound("respondentId.equals=" + (respondentId + 1));
    }

    @Test
    @Transactional
    void getAllFeedbackResponsesByQuestionIsEqualToSomething() throws Exception {
        FeedbackQuestions question;
        if (TestUtil.findAll(em, FeedbackQuestions.class).isEmpty()) {
            feedbackResponsesRepository.saveAndFlush(feedbackResponses);
            question = FeedbackQuestionsResourceIT.createEntity(em);
        } else {
            question = TestUtil.findAll(em, FeedbackQuestions.class).get(0);
        }
        em.persist(question);
        em.flush();
        feedbackResponses.setQuestion(question);
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);
        Long questionId = question.getId();

        // Get all the feedbackResponsesList where question equals to questionId
        defaultFeedbackResponsesShouldBeFound("questionId.equals=" + questionId);

        // Get all the feedbackResponsesList where question equals to (questionId + 1)
        defaultFeedbackResponsesShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedbackResponsesShouldBeFound(String filter) throws Exception {
        restFeedbackResponsesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedbackResponses.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerContentType").value(hasItem(DEFAULT_ANSWER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANSWER))))
            .andExpect(jsonPath("$.[*].ratingContentType").value(hasItem(DEFAULT_RATING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(Base64Utils.encodeToString(DEFAULT_RATING))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restFeedbackResponsesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedbackResponsesShouldNotBeFound(String filter) throws Exception {
        restFeedbackResponsesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedbackResponsesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeedbackResponses() throws Exception {
        // Get the feedbackResponses
        restFeedbackResponsesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedbackResponses() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();

        // Update the feedbackResponses
        FeedbackResponses updatedFeedbackResponses = feedbackResponsesRepository.findById(feedbackResponses.getId()).get();
        // Disconnect from session so that the updates on updatedFeedbackResponses are not directly saved in db
        em.detach(updatedFeedbackResponses);
        updatedFeedbackResponses
            .answer(UPDATED_ANSWER)
            .answerContentType(UPDATED_ANSWER_CONTENT_TYPE)
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackResponsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedbackResponses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeedbackResponses))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
        FeedbackResponses testFeedbackResponses = feedbackResponsesList.get(feedbackResponsesList.size() - 1);
        assertThat(testFeedbackResponses.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testFeedbackResponses.getAnswerContentType()).isEqualTo(UPDATED_ANSWER_CONTENT_TYPE);
        assertThat(testFeedbackResponses.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testFeedbackResponses.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testFeedbackResponses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackResponses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingFeedbackResponses() throws Exception {
        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();
        feedbackResponses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackResponsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedbackResponses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedbackResponses() throws Exception {
        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();
        feedbackResponses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackResponsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedbackResponses() throws Exception {
        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();
        feedbackResponses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackResponsesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackResponsesWithPatch() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();

        // Update the feedbackResponses using partial update
        FeedbackResponses partialUpdatedFeedbackResponses = new FeedbackResponses();
        partialUpdatedFeedbackResponses.setId(feedbackResponses.getId());

        partialUpdatedFeedbackResponses
            .answer(UPDATED_ANSWER)
            .answerContentType(UPDATED_ANSWER_CONTENT_TYPE)
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackResponses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackResponses))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
        FeedbackResponses testFeedbackResponses = feedbackResponsesList.get(feedbackResponsesList.size() - 1);
        assertThat(testFeedbackResponses.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testFeedbackResponses.getAnswerContentType()).isEqualTo(UPDATED_ANSWER_CONTENT_TYPE);
        assertThat(testFeedbackResponses.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testFeedbackResponses.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testFeedbackResponses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackResponses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateFeedbackResponsesWithPatch() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();

        // Update the feedbackResponses using partial update
        FeedbackResponses partialUpdatedFeedbackResponses = new FeedbackResponses();
        partialUpdatedFeedbackResponses.setId(feedbackResponses.getId());

        partialUpdatedFeedbackResponses
            .answer(UPDATED_ANSWER)
            .answerContentType(UPDATED_ANSWER_CONTENT_TYPE)
            .rating(UPDATED_RATING)
            .ratingContentType(UPDATED_RATING_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restFeedbackResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedbackResponses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedbackResponses))
            )
            .andExpect(status().isOk());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
        FeedbackResponses testFeedbackResponses = feedbackResponsesList.get(feedbackResponsesList.size() - 1);
        assertThat(testFeedbackResponses.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testFeedbackResponses.getAnswerContentType()).isEqualTo(UPDATED_ANSWER_CONTENT_TYPE);
        assertThat(testFeedbackResponses.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testFeedbackResponses.getRatingContentType()).isEqualTo(UPDATED_RATING_CONTENT_TYPE);
        assertThat(testFeedbackResponses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFeedbackResponses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingFeedbackResponses() throws Exception {
        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();
        feedbackResponses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedbackResponses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedbackResponses() throws Exception {
        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();
        feedbackResponses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedbackResponses() throws Exception {
        int databaseSizeBeforeUpdate = feedbackResponsesRepository.findAll().size();
        feedbackResponses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedbackResponses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeedbackResponses in the database
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedbackResponses() throws Exception {
        // Initialize the database
        feedbackResponsesRepository.saveAndFlush(feedbackResponses);

        int databaseSizeBeforeDelete = feedbackResponsesRepository.findAll().size();

        // Delete the feedbackResponses
        restFeedbackResponsesMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedbackResponses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedbackResponses> feedbackResponsesList = feedbackResponsesRepository.findAll();
        assertThat(feedbackResponsesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
