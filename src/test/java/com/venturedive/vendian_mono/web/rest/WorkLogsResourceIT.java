package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.WorkLogDetails;
import com.venturedive.vendian_mono.domain.WorkLogs;
import com.venturedive.vendian_mono.repository.WorkLogsRepository;
import com.venturedive.vendian_mono.service.criteria.WorkLogsCriteria;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link WorkLogsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkLogsResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_MOOD = 1;
    private static final Integer UPDATED_MOOD = 2;
    private static final Integer SMALLER_MOOD = 1 - 1;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/work-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkLogsRepository workLogsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkLogsMockMvc;

    private WorkLogs workLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkLogs createEntity(EntityManager em) {
        WorkLogs workLogs = new WorkLogs().date(DEFAULT_DATE).mood(DEFAULT_MOOD).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return workLogs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkLogs createUpdatedEntity(EntityManager em) {
        WorkLogs workLogs = new WorkLogs().date(UPDATED_DATE).mood(UPDATED_MOOD).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return workLogs;
    }

    @BeforeEach
    public void initTest() {
        workLogs = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkLogs() throws Exception {
        int databaseSizeBeforeCreate = workLogsRepository.findAll().size();
        // Create the WorkLogs
        restWorkLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isCreated());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeCreate + 1);
        WorkLogs testWorkLogs = workLogsList.get(workLogsList.size() - 1);
        assertThat(testWorkLogs.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testWorkLogs.getMood()).isEqualTo(DEFAULT_MOOD);
        assertThat(testWorkLogs.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testWorkLogs.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createWorkLogsWithExistingId() throws Exception {
        // Create the WorkLogs with an existing ID
        workLogs.setId(1L);

        int databaseSizeBeforeCreate = workLogsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = workLogsRepository.findAll().size();
        // set the field null
        workLogs.setCreatedat(null);

        // Create the WorkLogs, which fails.

        restWorkLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isBadRequest());

        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = workLogsRepository.findAll().size();
        // set the field null
        workLogs.setUpdatedat(null);

        // Create the WorkLogs, which fails.

        restWorkLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isBadRequest());

        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkLogs() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList
        restWorkLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].mood").value(hasItem(DEFAULT_MOOD)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getWorkLogs() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get the workLogs
        restWorkLogsMockMvc
            .perform(get(ENTITY_API_URL_ID, workLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workLogs.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.mood").value(DEFAULT_MOOD))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getWorkLogsByIdFiltering() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        Long id = workLogs.getId();

        defaultWorkLogsShouldBeFound("id.equals=" + id);
        defaultWorkLogsShouldNotBeFound("id.notEquals=" + id);

        defaultWorkLogsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkLogsShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkLogsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkLogsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkLogsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where date equals to DEFAULT_DATE
        defaultWorkLogsShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the workLogsList where date equals to UPDATED_DATE
        defaultWorkLogsShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkLogsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where date in DEFAULT_DATE or UPDATED_DATE
        defaultWorkLogsShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the workLogsList where date equals to UPDATED_DATE
        defaultWorkLogsShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkLogsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where date is not null
        defaultWorkLogsShouldBeFound("date.specified=true");

        // Get all the workLogsList where date is null
        defaultWorkLogsShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkLogsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where date is greater than or equal to DEFAULT_DATE
        defaultWorkLogsShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the workLogsList where date is greater than or equal to UPDATED_DATE
        defaultWorkLogsShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkLogsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where date is less than or equal to DEFAULT_DATE
        defaultWorkLogsShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the workLogsList where date is less than or equal to SMALLER_DATE
        defaultWorkLogsShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllWorkLogsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where date is less than DEFAULT_DATE
        defaultWorkLogsShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the workLogsList where date is less than UPDATED_DATE
        defaultWorkLogsShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkLogsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where date is greater than DEFAULT_DATE
        defaultWorkLogsShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the workLogsList where date is greater than SMALLER_DATE
        defaultWorkLogsShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllWorkLogsByMoodIsEqualToSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where mood equals to DEFAULT_MOOD
        defaultWorkLogsShouldBeFound("mood.equals=" + DEFAULT_MOOD);

        // Get all the workLogsList where mood equals to UPDATED_MOOD
        defaultWorkLogsShouldNotBeFound("mood.equals=" + UPDATED_MOOD);
    }

    @Test
    @Transactional
    void getAllWorkLogsByMoodIsInShouldWork() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where mood in DEFAULT_MOOD or UPDATED_MOOD
        defaultWorkLogsShouldBeFound("mood.in=" + DEFAULT_MOOD + "," + UPDATED_MOOD);

        // Get all the workLogsList where mood equals to UPDATED_MOOD
        defaultWorkLogsShouldNotBeFound("mood.in=" + UPDATED_MOOD);
    }

    @Test
    @Transactional
    void getAllWorkLogsByMoodIsNullOrNotNull() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where mood is not null
        defaultWorkLogsShouldBeFound("mood.specified=true");

        // Get all the workLogsList where mood is null
        defaultWorkLogsShouldNotBeFound("mood.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkLogsByMoodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where mood is greater than or equal to DEFAULT_MOOD
        defaultWorkLogsShouldBeFound("mood.greaterThanOrEqual=" + DEFAULT_MOOD);

        // Get all the workLogsList where mood is greater than or equal to UPDATED_MOOD
        defaultWorkLogsShouldNotBeFound("mood.greaterThanOrEqual=" + UPDATED_MOOD);
    }

    @Test
    @Transactional
    void getAllWorkLogsByMoodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where mood is less than or equal to DEFAULT_MOOD
        defaultWorkLogsShouldBeFound("mood.lessThanOrEqual=" + DEFAULT_MOOD);

        // Get all the workLogsList where mood is less than or equal to SMALLER_MOOD
        defaultWorkLogsShouldNotBeFound("mood.lessThanOrEqual=" + SMALLER_MOOD);
    }

    @Test
    @Transactional
    void getAllWorkLogsByMoodIsLessThanSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where mood is less than DEFAULT_MOOD
        defaultWorkLogsShouldNotBeFound("mood.lessThan=" + DEFAULT_MOOD);

        // Get all the workLogsList where mood is less than UPDATED_MOOD
        defaultWorkLogsShouldBeFound("mood.lessThan=" + UPDATED_MOOD);
    }

    @Test
    @Transactional
    void getAllWorkLogsByMoodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where mood is greater than DEFAULT_MOOD
        defaultWorkLogsShouldNotBeFound("mood.greaterThan=" + DEFAULT_MOOD);

        // Get all the workLogsList where mood is greater than SMALLER_MOOD
        defaultWorkLogsShouldBeFound("mood.greaterThan=" + SMALLER_MOOD);
    }

    @Test
    @Transactional
    void getAllWorkLogsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where createdat equals to DEFAULT_CREATEDAT
        defaultWorkLogsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the workLogsList where createdat equals to UPDATED_CREATEDAT
        defaultWorkLogsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllWorkLogsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultWorkLogsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the workLogsList where createdat equals to UPDATED_CREATEDAT
        defaultWorkLogsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllWorkLogsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where createdat is not null
        defaultWorkLogsShouldBeFound("createdat.specified=true");

        // Get all the workLogsList where createdat is null
        defaultWorkLogsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkLogsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultWorkLogsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the workLogsList where updatedat equals to UPDATED_UPDATEDAT
        defaultWorkLogsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllWorkLogsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultWorkLogsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the workLogsList where updatedat equals to UPDATED_UPDATEDAT
        defaultWorkLogsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllWorkLogsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        // Get all the workLogsList where updatedat is not null
        defaultWorkLogsShouldBeFound("updatedat.specified=true");

        // Get all the workLogsList where updatedat is null
        defaultWorkLogsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkLogsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            workLogsRepository.saveAndFlush(workLogs);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        workLogs.setEmployee(employee);
        workLogsRepository.saveAndFlush(workLogs);
        Long employeeId = employee.getId();

        // Get all the workLogsList where employee equals to employeeId
        defaultWorkLogsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the workLogsList where employee equals to (employeeId + 1)
        defaultWorkLogsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllWorkLogsByWorklogdetailsWorklogIsEqualToSomething() throws Exception {
        WorkLogDetails worklogdetailsWorklog;
        if (TestUtil.findAll(em, WorkLogDetails.class).isEmpty()) {
            workLogsRepository.saveAndFlush(workLogs);
            worklogdetailsWorklog = WorkLogDetailsResourceIT.createEntity(em);
        } else {
            worklogdetailsWorklog = TestUtil.findAll(em, WorkLogDetails.class).get(0);
        }
        em.persist(worklogdetailsWorklog);
        em.flush();
        workLogs.addWorklogdetailsWorklog(worklogdetailsWorklog);
        workLogsRepository.saveAndFlush(workLogs);
        Long worklogdetailsWorklogId = worklogdetailsWorklog.getId();

        // Get all the workLogsList where worklogdetailsWorklog equals to worklogdetailsWorklogId
        defaultWorkLogsShouldBeFound("worklogdetailsWorklogId.equals=" + worklogdetailsWorklogId);

        // Get all the workLogsList where worklogdetailsWorklog equals to (worklogdetailsWorklogId + 1)
        defaultWorkLogsShouldNotBeFound("worklogdetailsWorklogId.equals=" + (worklogdetailsWorklogId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkLogsShouldBeFound(String filter) throws Exception {
        restWorkLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].mood").value(hasItem(DEFAULT_MOOD)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restWorkLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkLogsShouldNotBeFound(String filter) throws Exception {
        restWorkLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkLogs() throws Exception {
        // Get the workLogs
        restWorkLogsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkLogs() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();

        // Update the workLogs
        WorkLogs updatedWorkLogs = workLogsRepository.findById(workLogs.getId()).get();
        // Disconnect from session so that the updates on updatedWorkLogs are not directly saved in db
        em.detach(updatedWorkLogs);
        updatedWorkLogs.date(UPDATED_DATE).mood(UPDATED_MOOD).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restWorkLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkLogs))
            )
            .andExpect(status().isOk());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
        WorkLogs testWorkLogs = workLogsList.get(workLogsList.size() - 1);
        assertThat(testWorkLogs.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorkLogs.getMood()).isEqualTo(UPDATED_MOOD);
        assertThat(testWorkLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testWorkLogs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingWorkLogs() throws Exception {
        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();
        workLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkLogs() throws Exception {
        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();
        workLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkLogs() throws Exception {
        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();
        workLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLogsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkLogsWithPatch() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();

        // Update the workLogs using partial update
        WorkLogs partialUpdatedWorkLogs = new WorkLogs();
        partialUpdatedWorkLogs.setId(workLogs.getId());

        partialUpdatedWorkLogs.date(UPDATED_DATE).mood(UPDATED_MOOD).createdat(UPDATED_CREATEDAT);

        restWorkLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkLogs))
            )
            .andExpect(status().isOk());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
        WorkLogs testWorkLogs = workLogsList.get(workLogsList.size() - 1);
        assertThat(testWorkLogs.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorkLogs.getMood()).isEqualTo(UPDATED_MOOD);
        assertThat(testWorkLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testWorkLogs.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateWorkLogsWithPatch() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();

        // Update the workLogs using partial update
        WorkLogs partialUpdatedWorkLogs = new WorkLogs();
        partialUpdatedWorkLogs.setId(workLogs.getId());

        partialUpdatedWorkLogs.date(UPDATED_DATE).mood(UPDATED_MOOD).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restWorkLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkLogs))
            )
            .andExpect(status().isOk());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
        WorkLogs testWorkLogs = workLogsList.get(workLogsList.size() - 1);
        assertThat(testWorkLogs.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorkLogs.getMood()).isEqualTo(UPDATED_MOOD);
        assertThat(testWorkLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testWorkLogs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingWorkLogs() throws Exception {
        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();
        workLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkLogs() throws Exception {
        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();
        workLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkLogs() throws Exception {
        int databaseSizeBeforeUpdate = workLogsRepository.findAll().size();
        workLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkLogsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkLogs in the database
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkLogs() throws Exception {
        // Initialize the database
        workLogsRepository.saveAndFlush(workLogs);

        int databaseSizeBeforeDelete = workLogsRepository.findAll().size();

        // Delete the workLogs
        restWorkLogsMockMvc
            .perform(delete(ENTITY_API_URL_ID, workLogs.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkLogs> workLogsList = workLogsRepository.findAll();
        assertThat(workLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
