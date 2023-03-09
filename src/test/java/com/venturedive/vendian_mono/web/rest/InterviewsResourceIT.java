package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Interviews;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.Questions;
import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.InterviewsRepository;
import com.venturedive.vendian_mono.service.criteria.InterviewsCriteria;
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
 * Integration tests for the {@link InterviewsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InterviewsResourceIT {

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTCOMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTCOMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_LMCOMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_LMCOMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_SCHEDULEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SCHEDULEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/interviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InterviewsRepository interviewsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterviewsMockMvc;

    private Interviews interviews;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interviews createEntity(EntityManager em) {
        Interviews interviews = new Interviews()
            .result(DEFAULT_RESULT)
            .clientcomments(DEFAULT_CLIENTCOMMENTS)
            .lmcomments(DEFAULT_LMCOMMENTS)
            .scheduledat(DEFAULT_SCHEDULEDAT)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        interviews.setEmployee(employees);
        // Add required entity
        Projects projects;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            projects = ProjectsResourceIT.createEntity(em);
            em.persist(projects);
            em.flush();
        } else {
            projects = TestUtil.findAll(em, Projects.class).get(0);
        }
        interviews.setProject(projects);
        // Add required entity
        Tracks tracks;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            tracks = TracksResourceIT.createEntity(em);
            em.persist(tracks);
            em.flush();
        } else {
            tracks = TestUtil.findAll(em, Tracks.class).get(0);
        }
        interviews.setTrack(tracks);
        return interviews;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interviews createUpdatedEntity(EntityManager em) {
        Interviews interviews = new Interviews()
            .result(UPDATED_RESULT)
            .clientcomments(UPDATED_CLIENTCOMMENTS)
            .lmcomments(UPDATED_LMCOMMENTS)
            .scheduledat(UPDATED_SCHEDULEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        interviews.setEmployee(employees);
        // Add required entity
        Projects projects;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            projects = ProjectsResourceIT.createUpdatedEntity(em);
            em.persist(projects);
            em.flush();
        } else {
            projects = TestUtil.findAll(em, Projects.class).get(0);
        }
        interviews.setProject(projects);
        // Add required entity
        Tracks tracks;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            tracks = TracksResourceIT.createUpdatedEntity(em);
            em.persist(tracks);
            em.flush();
        } else {
            tracks = TestUtil.findAll(em, Tracks.class).get(0);
        }
        interviews.setTrack(tracks);
        return interviews;
    }

    @BeforeEach
    public void initTest() {
        interviews = createEntity(em);
    }

    @Test
    @Transactional
    void createInterviews() throws Exception {
        int databaseSizeBeforeCreate = interviewsRepository.findAll().size();
        // Create the Interviews
        restInterviewsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isCreated());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeCreate + 1);
        Interviews testInterviews = interviewsList.get(interviewsList.size() - 1);
        assertThat(testInterviews.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testInterviews.getClientcomments()).isEqualTo(DEFAULT_CLIENTCOMMENTS);
        assertThat(testInterviews.getLmcomments()).isEqualTo(DEFAULT_LMCOMMENTS);
        assertThat(testInterviews.getScheduledat()).isEqualTo(DEFAULT_SCHEDULEDAT);
        assertThat(testInterviews.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testInterviews.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testInterviews.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createInterviewsWithExistingId() throws Exception {
        // Create the Interviews with an existing ID
        interviews.setId(1L);

        int databaseSizeBeforeCreate = interviewsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterviewsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = interviewsRepository.findAll().size();
        // set the field null
        interviews.setCreatedat(null);

        // Create the Interviews, which fails.

        restInterviewsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isBadRequest());

        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = interviewsRepository.findAll().size();
        // set the field null
        interviews.setUpdatedat(null);

        // Create the Interviews, which fails.

        restInterviewsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isBadRequest());

        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInterviews() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList
        restInterviewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviews.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].clientcomments").value(hasItem(DEFAULT_CLIENTCOMMENTS)))
            .andExpect(jsonPath("$.[*].lmcomments").value(hasItem(DEFAULT_LMCOMMENTS)))
            .andExpect(jsonPath("$.[*].scheduledat").value(hasItem(DEFAULT_SCHEDULEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    @Transactional
    void getInterviews() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get the interviews
        restInterviewsMockMvc
            .perform(get(ENTITY_API_URL_ID, interviews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interviews.getId().intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.clientcomments").value(DEFAULT_CLIENTCOMMENTS))
            .andExpect(jsonPath("$.lmcomments").value(DEFAULT_LMCOMMENTS))
            .andExpect(jsonPath("$.scheduledat").value(DEFAULT_SCHEDULEDAT.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getInterviewsByIdFiltering() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        Long id = interviews.getId();

        defaultInterviewsShouldBeFound("id.equals=" + id);
        defaultInterviewsShouldNotBeFound("id.notEquals=" + id);

        defaultInterviewsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInterviewsShouldNotBeFound("id.greaterThan=" + id);

        defaultInterviewsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInterviewsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInterviewsByResultIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where result equals to DEFAULT_RESULT
        defaultInterviewsShouldBeFound("result.equals=" + DEFAULT_RESULT);

        // Get all the interviewsList where result equals to UPDATED_RESULT
        defaultInterviewsShouldNotBeFound("result.equals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllInterviewsByResultIsInShouldWork() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where result in DEFAULT_RESULT or UPDATED_RESULT
        defaultInterviewsShouldBeFound("result.in=" + DEFAULT_RESULT + "," + UPDATED_RESULT);

        // Get all the interviewsList where result equals to UPDATED_RESULT
        defaultInterviewsShouldNotBeFound("result.in=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllInterviewsByResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where result is not null
        defaultInterviewsShouldBeFound("result.specified=true");

        // Get all the interviewsList where result is null
        defaultInterviewsShouldNotBeFound("result.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewsByResultContainsSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where result contains DEFAULT_RESULT
        defaultInterviewsShouldBeFound("result.contains=" + DEFAULT_RESULT);

        // Get all the interviewsList where result contains UPDATED_RESULT
        defaultInterviewsShouldNotBeFound("result.contains=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllInterviewsByResultNotContainsSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where result does not contain DEFAULT_RESULT
        defaultInterviewsShouldNotBeFound("result.doesNotContain=" + DEFAULT_RESULT);

        // Get all the interviewsList where result does not contain UPDATED_RESULT
        defaultInterviewsShouldBeFound("result.doesNotContain=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllInterviewsByClientcommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where clientcomments equals to DEFAULT_CLIENTCOMMENTS
        defaultInterviewsShouldBeFound("clientcomments.equals=" + DEFAULT_CLIENTCOMMENTS);

        // Get all the interviewsList where clientcomments equals to UPDATED_CLIENTCOMMENTS
        defaultInterviewsShouldNotBeFound("clientcomments.equals=" + UPDATED_CLIENTCOMMENTS);
    }

    @Test
    @Transactional
    void getAllInterviewsByClientcommentsIsInShouldWork() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where clientcomments in DEFAULT_CLIENTCOMMENTS or UPDATED_CLIENTCOMMENTS
        defaultInterviewsShouldBeFound("clientcomments.in=" + DEFAULT_CLIENTCOMMENTS + "," + UPDATED_CLIENTCOMMENTS);

        // Get all the interviewsList where clientcomments equals to UPDATED_CLIENTCOMMENTS
        defaultInterviewsShouldNotBeFound("clientcomments.in=" + UPDATED_CLIENTCOMMENTS);
    }

    @Test
    @Transactional
    void getAllInterviewsByClientcommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where clientcomments is not null
        defaultInterviewsShouldBeFound("clientcomments.specified=true");

        // Get all the interviewsList where clientcomments is null
        defaultInterviewsShouldNotBeFound("clientcomments.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewsByClientcommentsContainsSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where clientcomments contains DEFAULT_CLIENTCOMMENTS
        defaultInterviewsShouldBeFound("clientcomments.contains=" + DEFAULT_CLIENTCOMMENTS);

        // Get all the interviewsList where clientcomments contains UPDATED_CLIENTCOMMENTS
        defaultInterviewsShouldNotBeFound("clientcomments.contains=" + UPDATED_CLIENTCOMMENTS);
    }

    @Test
    @Transactional
    void getAllInterviewsByClientcommentsNotContainsSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where clientcomments does not contain DEFAULT_CLIENTCOMMENTS
        defaultInterviewsShouldNotBeFound("clientcomments.doesNotContain=" + DEFAULT_CLIENTCOMMENTS);

        // Get all the interviewsList where clientcomments does not contain UPDATED_CLIENTCOMMENTS
        defaultInterviewsShouldBeFound("clientcomments.doesNotContain=" + UPDATED_CLIENTCOMMENTS);
    }

    @Test
    @Transactional
    void getAllInterviewsByLmcommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where lmcomments equals to DEFAULT_LMCOMMENTS
        defaultInterviewsShouldBeFound("lmcomments.equals=" + DEFAULT_LMCOMMENTS);

        // Get all the interviewsList where lmcomments equals to UPDATED_LMCOMMENTS
        defaultInterviewsShouldNotBeFound("lmcomments.equals=" + UPDATED_LMCOMMENTS);
    }

    @Test
    @Transactional
    void getAllInterviewsByLmcommentsIsInShouldWork() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where lmcomments in DEFAULT_LMCOMMENTS or UPDATED_LMCOMMENTS
        defaultInterviewsShouldBeFound("lmcomments.in=" + DEFAULT_LMCOMMENTS + "," + UPDATED_LMCOMMENTS);

        // Get all the interviewsList where lmcomments equals to UPDATED_LMCOMMENTS
        defaultInterviewsShouldNotBeFound("lmcomments.in=" + UPDATED_LMCOMMENTS);
    }

    @Test
    @Transactional
    void getAllInterviewsByLmcommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where lmcomments is not null
        defaultInterviewsShouldBeFound("lmcomments.specified=true");

        // Get all the interviewsList where lmcomments is null
        defaultInterviewsShouldNotBeFound("lmcomments.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewsByLmcommentsContainsSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where lmcomments contains DEFAULT_LMCOMMENTS
        defaultInterviewsShouldBeFound("lmcomments.contains=" + DEFAULT_LMCOMMENTS);

        // Get all the interviewsList where lmcomments contains UPDATED_LMCOMMENTS
        defaultInterviewsShouldNotBeFound("lmcomments.contains=" + UPDATED_LMCOMMENTS);
    }

    @Test
    @Transactional
    void getAllInterviewsByLmcommentsNotContainsSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where lmcomments does not contain DEFAULT_LMCOMMENTS
        defaultInterviewsShouldNotBeFound("lmcomments.doesNotContain=" + DEFAULT_LMCOMMENTS);

        // Get all the interviewsList where lmcomments does not contain UPDATED_LMCOMMENTS
        defaultInterviewsShouldBeFound("lmcomments.doesNotContain=" + UPDATED_LMCOMMENTS);
    }

    @Test
    @Transactional
    void getAllInterviewsByScheduledatIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where scheduledat equals to DEFAULT_SCHEDULEDAT
        defaultInterviewsShouldBeFound("scheduledat.equals=" + DEFAULT_SCHEDULEDAT);

        // Get all the interviewsList where scheduledat equals to UPDATED_SCHEDULEDAT
        defaultInterviewsShouldNotBeFound("scheduledat.equals=" + UPDATED_SCHEDULEDAT);
    }

    @Test
    @Transactional
    void getAllInterviewsByScheduledatIsInShouldWork() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where scheduledat in DEFAULT_SCHEDULEDAT or UPDATED_SCHEDULEDAT
        defaultInterviewsShouldBeFound("scheduledat.in=" + DEFAULT_SCHEDULEDAT + "," + UPDATED_SCHEDULEDAT);

        // Get all the interviewsList where scheduledat equals to UPDATED_SCHEDULEDAT
        defaultInterviewsShouldNotBeFound("scheduledat.in=" + UPDATED_SCHEDULEDAT);
    }

    @Test
    @Transactional
    void getAllInterviewsByScheduledatIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where scheduledat is not null
        defaultInterviewsShouldBeFound("scheduledat.specified=true");

        // Get all the interviewsList where scheduledat is null
        defaultInterviewsShouldNotBeFound("scheduledat.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where createdat equals to DEFAULT_CREATEDAT
        defaultInterviewsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the interviewsList where createdat equals to UPDATED_CREATEDAT
        defaultInterviewsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllInterviewsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultInterviewsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the interviewsList where createdat equals to UPDATED_CREATEDAT
        defaultInterviewsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllInterviewsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where createdat is not null
        defaultInterviewsShouldBeFound("createdat.specified=true");

        // Get all the interviewsList where createdat is null
        defaultInterviewsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultInterviewsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the interviewsList where updatedat equals to UPDATED_UPDATEDAT
        defaultInterviewsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllInterviewsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultInterviewsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the interviewsList where updatedat equals to UPDATED_UPDATEDAT
        defaultInterviewsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllInterviewsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where updatedat is not null
        defaultInterviewsShouldBeFound("updatedat.specified=true");

        // Get all the interviewsList where updatedat is null
        defaultInterviewsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where deletedat equals to DEFAULT_DELETEDAT
        defaultInterviewsShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the interviewsList where deletedat equals to UPDATED_DELETEDAT
        defaultInterviewsShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllInterviewsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultInterviewsShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the interviewsList where deletedat equals to UPDATED_DELETEDAT
        defaultInterviewsShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllInterviewsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        // Get all the interviewsList where deletedat is not null
        defaultInterviewsShouldBeFound("deletedat.specified=true");

        // Get all the interviewsList where deletedat is null
        defaultInterviewsShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            interviewsRepository.saveAndFlush(interviews);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        interviews.setEmployee(employee);
        interviewsRepository.saveAndFlush(interviews);
        Long employeeId = employee.getId();

        // Get all the interviewsList where employee equals to employeeId
        defaultInterviewsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the interviewsList where employee equals to (employeeId + 1)
        defaultInterviewsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllInterviewsByProjectIsEqualToSomething() throws Exception {
        Projects project;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            interviewsRepository.saveAndFlush(interviews);
            project = ProjectsResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(project);
        em.flush();
        interviews.setProject(project);
        interviewsRepository.saveAndFlush(interviews);
        Long projectId = project.getId();

        // Get all the interviewsList where project equals to projectId
        defaultInterviewsShouldBeFound("projectId.equals=" + projectId);

        // Get all the interviewsList where project equals to (projectId + 1)
        defaultInterviewsShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    @Test
    @Transactional
    void getAllInterviewsByTrackIsEqualToSomething() throws Exception {
        Tracks track;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            interviewsRepository.saveAndFlush(interviews);
            track = TracksResourceIT.createEntity(em);
        } else {
            track = TestUtil.findAll(em, Tracks.class).get(0);
        }
        em.persist(track);
        em.flush();
        interviews.setTrack(track);
        interviewsRepository.saveAndFlush(interviews);
        Long trackId = track.getId();

        // Get all the interviewsList where track equals to trackId
        defaultInterviewsShouldBeFound("trackId.equals=" + trackId);

        // Get all the interviewsList where track equals to (trackId + 1)
        defaultInterviewsShouldNotBeFound("trackId.equals=" + (trackId + 1));
    }

    @Test
    @Transactional
    void getAllInterviewsByQuestionsInterviewIsEqualToSomething() throws Exception {
        Questions questionsInterview;
        if (TestUtil.findAll(em, Questions.class).isEmpty()) {
            interviewsRepository.saveAndFlush(interviews);
            questionsInterview = QuestionsResourceIT.createEntity(em);
        } else {
            questionsInterview = TestUtil.findAll(em, Questions.class).get(0);
        }
        em.persist(questionsInterview);
        em.flush();
        interviews.addQuestionsInterview(questionsInterview);
        interviewsRepository.saveAndFlush(interviews);
        Long questionsInterviewId = questionsInterview.getId();

        // Get all the interviewsList where questionsInterview equals to questionsInterviewId
        defaultInterviewsShouldBeFound("questionsInterviewId.equals=" + questionsInterviewId);

        // Get all the interviewsList where questionsInterview equals to (questionsInterviewId + 1)
        defaultInterviewsShouldNotBeFound("questionsInterviewId.equals=" + (questionsInterviewId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInterviewsShouldBeFound(String filter) throws Exception {
        restInterviewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviews.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].clientcomments").value(hasItem(DEFAULT_CLIENTCOMMENTS)))
            .andExpect(jsonPath("$.[*].lmcomments").value(hasItem(DEFAULT_LMCOMMENTS)))
            .andExpect(jsonPath("$.[*].scheduledat").value(hasItem(DEFAULT_SCHEDULEDAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restInterviewsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInterviewsShouldNotBeFound(String filter) throws Exception {
        restInterviewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInterviewsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInterviews() throws Exception {
        // Get the interviews
        restInterviewsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInterviews() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();

        // Update the interviews
        Interviews updatedInterviews = interviewsRepository.findById(interviews.getId()).get();
        // Disconnect from session so that the updates on updatedInterviews are not directly saved in db
        em.detach(updatedInterviews);
        updatedInterviews
            .result(UPDATED_RESULT)
            .clientcomments(UPDATED_CLIENTCOMMENTS)
            .lmcomments(UPDATED_LMCOMMENTS)
            .scheduledat(UPDATED_SCHEDULEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restInterviewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInterviews.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInterviews))
            )
            .andExpect(status().isOk());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
        Interviews testInterviews = interviewsList.get(interviewsList.size() - 1);
        assertThat(testInterviews.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testInterviews.getClientcomments()).isEqualTo(UPDATED_CLIENTCOMMENTS);
        assertThat(testInterviews.getLmcomments()).isEqualTo(UPDATED_LMCOMMENTS);
        assertThat(testInterviews.getScheduledat()).isEqualTo(UPDATED_SCHEDULEDAT);
        assertThat(testInterviews.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testInterviews.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testInterviews.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingInterviews() throws Exception {
        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();
        interviews.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interviews.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInterviews() throws Exception {
        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();
        interviews.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterviewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInterviews() throws Exception {
        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();
        interviews.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterviewsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInterviewsWithPatch() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();

        // Update the interviews using partial update
        Interviews partialUpdatedInterviews = new Interviews();
        partialUpdatedInterviews.setId(interviews.getId());

        partialUpdatedInterviews.clientcomments(UPDATED_CLIENTCOMMENTS).updatedat(UPDATED_UPDATEDAT).deletedat(UPDATED_DELETEDAT);

        restInterviewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterviews.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterviews))
            )
            .andExpect(status().isOk());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
        Interviews testInterviews = interviewsList.get(interviewsList.size() - 1);
        assertThat(testInterviews.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testInterviews.getClientcomments()).isEqualTo(UPDATED_CLIENTCOMMENTS);
        assertThat(testInterviews.getLmcomments()).isEqualTo(DEFAULT_LMCOMMENTS);
        assertThat(testInterviews.getScheduledat()).isEqualTo(DEFAULT_SCHEDULEDAT);
        assertThat(testInterviews.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testInterviews.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testInterviews.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateInterviewsWithPatch() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();

        // Update the interviews using partial update
        Interviews partialUpdatedInterviews = new Interviews();
        partialUpdatedInterviews.setId(interviews.getId());

        partialUpdatedInterviews
            .result(UPDATED_RESULT)
            .clientcomments(UPDATED_CLIENTCOMMENTS)
            .lmcomments(UPDATED_LMCOMMENTS)
            .scheduledat(UPDATED_SCHEDULEDAT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restInterviewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterviews.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterviews))
            )
            .andExpect(status().isOk());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
        Interviews testInterviews = interviewsList.get(interviewsList.size() - 1);
        assertThat(testInterviews.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testInterviews.getClientcomments()).isEqualTo(UPDATED_CLIENTCOMMENTS);
        assertThat(testInterviews.getLmcomments()).isEqualTo(UPDATED_LMCOMMENTS);
        assertThat(testInterviews.getScheduledat()).isEqualTo(UPDATED_SCHEDULEDAT);
        assertThat(testInterviews.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testInterviews.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testInterviews.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingInterviews() throws Exception {
        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();
        interviews.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, interviews.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInterviews() throws Exception {
        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();
        interviews.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterviewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInterviews() throws Exception {
        int databaseSizeBeforeUpdate = interviewsRepository.findAll().size();
        interviews.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterviewsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interviews))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interviews in the database
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInterviews() throws Exception {
        // Initialize the database
        interviewsRepository.saveAndFlush(interviews);

        int databaseSizeBeforeDelete = interviewsRepository.findAll().size();

        // Delete the interviews
        restInterviewsMockMvc
            .perform(delete(ENTITY_API_URL_ID, interviews.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Interviews> interviewsList = interviewsRepository.findAll();
        assertThat(interviewsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
