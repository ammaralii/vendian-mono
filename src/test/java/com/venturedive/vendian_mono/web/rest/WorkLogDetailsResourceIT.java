package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.WorkLogDetails;
import com.venturedive.vendian_mono.domain.WorkLogs;
import com.venturedive.vendian_mono.repository.WorkLogDetailsRepository;
import com.venturedive.vendian_mono.service.criteria.WorkLogDetailsCriteria;
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
 * Integration tests for the {@link WorkLogDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkLogDetailsResourceIT {

    private static final String DEFAULT_PERCENTAGE = "AAAAAAAAAA";
    private static final String UPDATED_PERCENTAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_HOURS = 1;
    private static final Integer UPDATED_HOURS = 2;
    private static final Integer SMALLER_HOURS = 1 - 1;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/work-log-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkLogDetailsRepository workLogDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkLogDetailsMockMvc;

    private WorkLogDetails workLogDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkLogDetails createEntity(EntityManager em) {
        WorkLogDetails workLogDetails = new WorkLogDetails()
            .percentage(DEFAULT_PERCENTAGE)
            .hours(DEFAULT_HOURS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return workLogDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkLogDetails createUpdatedEntity(EntityManager em) {
        WorkLogDetails workLogDetails = new WorkLogDetails()
            .percentage(UPDATED_PERCENTAGE)
            .hours(UPDATED_HOURS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return workLogDetails;
    }

    @BeforeEach
    public void initTest() {
        workLogDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkLogDetails() throws Exception {
        int databaseSizeBeforeCreate = workLogDetailsRepository.findAll().size();
        // Create the WorkLogDetails
        restWorkLogDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isCreated());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        WorkLogDetails testWorkLogDetails = workLogDetailsList.get(workLogDetailsList.size() - 1);
        assertThat(testWorkLogDetails.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testWorkLogDetails.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testWorkLogDetails.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testWorkLogDetails.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createWorkLogDetailsWithExistingId() throws Exception {
        // Create the WorkLogDetails with an existing ID
        workLogDetails.setId(1L);

        int databaseSizeBeforeCreate = workLogDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkLogDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = workLogDetailsRepository.findAll().size();
        // set the field null
        workLogDetails.setCreatedat(null);

        // Create the WorkLogDetails, which fails.

        restWorkLogDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isBadRequest());

        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = workLogDetailsRepository.findAll().size();
        // set the field null
        workLogDetails.setUpdatedat(null);

        // Create the WorkLogDetails, which fails.

        restWorkLogDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isBadRequest());

        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkLogDetails() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList
        restWorkLogDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workLogDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getWorkLogDetails() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get the workLogDetails
        restWorkLogDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, workLogDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workLogDetails.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getWorkLogDetailsByIdFiltering() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        Long id = workLogDetails.getId();

        defaultWorkLogDetailsShouldBeFound("id.equals=" + id);
        defaultWorkLogDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultWorkLogDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkLogDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkLogDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkLogDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where percentage equals to DEFAULT_PERCENTAGE
        defaultWorkLogDetailsShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the workLogDetailsList where percentage equals to UPDATED_PERCENTAGE
        defaultWorkLogDetailsShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultWorkLogDetailsShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the workLogDetailsList where percentage equals to UPDATED_PERCENTAGE
        defaultWorkLogDetailsShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where percentage is not null
        defaultWorkLogDetailsShouldBeFound("percentage.specified=true");

        // Get all the workLogDetailsList where percentage is null
        defaultWorkLogDetailsShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByPercentageContainsSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where percentage contains DEFAULT_PERCENTAGE
        defaultWorkLogDetailsShouldBeFound("percentage.contains=" + DEFAULT_PERCENTAGE);

        // Get all the workLogDetailsList where percentage contains UPDATED_PERCENTAGE
        defaultWorkLogDetailsShouldNotBeFound("percentage.contains=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByPercentageNotContainsSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where percentage does not contain DEFAULT_PERCENTAGE
        defaultWorkLogDetailsShouldNotBeFound("percentage.doesNotContain=" + DEFAULT_PERCENTAGE);

        // Get all the workLogDetailsList where percentage does not contain UPDATED_PERCENTAGE
        defaultWorkLogDetailsShouldBeFound("percentage.doesNotContain=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where hours equals to DEFAULT_HOURS
        defaultWorkLogDetailsShouldBeFound("hours.equals=" + DEFAULT_HOURS);

        // Get all the workLogDetailsList where hours equals to UPDATED_HOURS
        defaultWorkLogDetailsShouldNotBeFound("hours.equals=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByHoursIsInShouldWork() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where hours in DEFAULT_HOURS or UPDATED_HOURS
        defaultWorkLogDetailsShouldBeFound("hours.in=" + DEFAULT_HOURS + "," + UPDATED_HOURS);

        // Get all the workLogDetailsList where hours equals to UPDATED_HOURS
        defaultWorkLogDetailsShouldNotBeFound("hours.in=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where hours is not null
        defaultWorkLogDetailsShouldBeFound("hours.specified=true");

        // Get all the workLogDetailsList where hours is null
        defaultWorkLogDetailsShouldNotBeFound("hours.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByHoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where hours is greater than or equal to DEFAULT_HOURS
        defaultWorkLogDetailsShouldBeFound("hours.greaterThanOrEqual=" + DEFAULT_HOURS);

        // Get all the workLogDetailsList where hours is greater than or equal to UPDATED_HOURS
        defaultWorkLogDetailsShouldNotBeFound("hours.greaterThanOrEqual=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByHoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where hours is less than or equal to DEFAULT_HOURS
        defaultWorkLogDetailsShouldBeFound("hours.lessThanOrEqual=" + DEFAULT_HOURS);

        // Get all the workLogDetailsList where hours is less than or equal to SMALLER_HOURS
        defaultWorkLogDetailsShouldNotBeFound("hours.lessThanOrEqual=" + SMALLER_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByHoursIsLessThanSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where hours is less than DEFAULT_HOURS
        defaultWorkLogDetailsShouldNotBeFound("hours.lessThan=" + DEFAULT_HOURS);

        // Get all the workLogDetailsList where hours is less than UPDATED_HOURS
        defaultWorkLogDetailsShouldBeFound("hours.lessThan=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByHoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where hours is greater than DEFAULT_HOURS
        defaultWorkLogDetailsShouldNotBeFound("hours.greaterThan=" + DEFAULT_HOURS);

        // Get all the workLogDetailsList where hours is greater than SMALLER_HOURS
        defaultWorkLogDetailsShouldBeFound("hours.greaterThan=" + SMALLER_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where createdat equals to DEFAULT_CREATEDAT
        defaultWorkLogDetailsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the workLogDetailsList where createdat equals to UPDATED_CREATEDAT
        defaultWorkLogDetailsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultWorkLogDetailsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the workLogDetailsList where createdat equals to UPDATED_CREATEDAT
        defaultWorkLogDetailsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where createdat is not null
        defaultWorkLogDetailsShouldBeFound("createdat.specified=true");

        // Get all the workLogDetailsList where createdat is null
        defaultWorkLogDetailsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultWorkLogDetailsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the workLogDetailsList where updatedat equals to UPDATED_UPDATEDAT
        defaultWorkLogDetailsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultWorkLogDetailsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the workLogDetailsList where updatedat equals to UPDATED_UPDATEDAT
        defaultWorkLogDetailsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        // Get all the workLogDetailsList where updatedat is not null
        defaultWorkLogDetailsShouldBeFound("updatedat.specified=true");

        // Get all the workLogDetailsList where updatedat is null
        defaultWorkLogDetailsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByWorklogIsEqualToSomething() throws Exception {
        WorkLogs worklog;
        if (TestUtil.findAll(em, WorkLogs.class).isEmpty()) {
            workLogDetailsRepository.saveAndFlush(workLogDetails);
            worklog = WorkLogsResourceIT.createEntity(em);
        } else {
            worklog = TestUtil.findAll(em, WorkLogs.class).get(0);
        }
        em.persist(worklog);
        em.flush();
        workLogDetails.setWorklog(worklog);
        workLogDetailsRepository.saveAndFlush(workLogDetails);
        Long worklogId = worklog.getId();

        // Get all the workLogDetailsList where worklog equals to worklogId
        defaultWorkLogDetailsShouldBeFound("worklogId.equals=" + worklogId);

        // Get all the workLogDetailsList where worklog equals to (worklogId + 1)
        defaultWorkLogDetailsShouldNotBeFound("worklogId.equals=" + (worklogId + 1));
    }

    @Test
    @Transactional
    void getAllWorkLogDetailsByProjectIsEqualToSomething() throws Exception {
        Projects project;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            workLogDetailsRepository.saveAndFlush(workLogDetails);
            project = ProjectsResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(project);
        em.flush();
        workLogDetails.setProject(project);
        workLogDetailsRepository.saveAndFlush(workLogDetails);
        Long projectId = project.getId();

        // Get all the workLogDetailsList where project equals to projectId
        defaultWorkLogDetailsShouldBeFound("projectId.equals=" + projectId);

        // Get all the workLogDetailsList where project equals to (projectId + 1)
        defaultWorkLogDetailsShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkLogDetailsShouldBeFound(String filter) throws Exception {
        restWorkLogDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workLogDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restWorkLogDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkLogDetailsShouldNotBeFound(String filter) throws Exception {
        restWorkLogDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkLogDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkLogDetails() throws Exception {
        // Get the workLogDetails
        restWorkLogDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkLogDetails() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();

        // Update the workLogDetails
        WorkLogDetails updatedWorkLogDetails = workLogDetailsRepository.findById(workLogDetails.getId()).get();
        // Disconnect from session so that the updates on updatedWorkLogDetails are not directly saved in db
        em.detach(updatedWorkLogDetails);
        updatedWorkLogDetails.percentage(UPDATED_PERCENTAGE).hours(UPDATED_HOURS).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restWorkLogDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkLogDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkLogDetails))
            )
            .andExpect(status().isOk());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
        WorkLogDetails testWorkLogDetails = workLogDetailsList.get(workLogDetailsList.size() - 1);
        assertThat(testWorkLogDetails.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testWorkLogDetails.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkLogDetails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testWorkLogDetails.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingWorkLogDetails() throws Exception {
        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();
        workLogDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkLogDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workLogDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkLogDetails() throws Exception {
        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();
        workLogDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLogDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkLogDetails() throws Exception {
        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();
        workLogDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLogDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkLogDetailsWithPatch() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();

        // Update the workLogDetails using partial update
        WorkLogDetails partialUpdatedWorkLogDetails = new WorkLogDetails();
        partialUpdatedWorkLogDetails.setId(workLogDetails.getId());

        partialUpdatedWorkLogDetails.percentage(UPDATED_PERCENTAGE);

        restWorkLogDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkLogDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkLogDetails))
            )
            .andExpect(status().isOk());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
        WorkLogDetails testWorkLogDetails = workLogDetailsList.get(workLogDetailsList.size() - 1);
        assertThat(testWorkLogDetails.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testWorkLogDetails.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testWorkLogDetails.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testWorkLogDetails.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateWorkLogDetailsWithPatch() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();

        // Update the workLogDetails using partial update
        WorkLogDetails partialUpdatedWorkLogDetails = new WorkLogDetails();
        partialUpdatedWorkLogDetails.setId(workLogDetails.getId());

        partialUpdatedWorkLogDetails
            .percentage(UPDATED_PERCENTAGE)
            .hours(UPDATED_HOURS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restWorkLogDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkLogDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkLogDetails))
            )
            .andExpect(status().isOk());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
        WorkLogDetails testWorkLogDetails = workLogDetailsList.get(workLogDetailsList.size() - 1);
        assertThat(testWorkLogDetails.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testWorkLogDetails.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkLogDetails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testWorkLogDetails.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingWorkLogDetails() throws Exception {
        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();
        workLogDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkLogDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workLogDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkLogDetails() throws Exception {
        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();
        workLogDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLogDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkLogDetails() throws Exception {
        int databaseSizeBeforeUpdate = workLogDetailsRepository.findAll().size();
        workLogDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLogDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workLogDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkLogDetails in the database
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkLogDetails() throws Exception {
        // Initialize the database
        workLogDetailsRepository.saveAndFlush(workLogDetails);

        int databaseSizeBeforeDelete = workLogDetailsRepository.findAll().size();

        // Delete the workLogDetails
        restWorkLogDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, workLogDetails.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkLogDetails> workLogDetailsList = workLogDetailsRepository.findAll();
        assertThat(workLogDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
