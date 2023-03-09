package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeProjectRatings;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.PerformanceCycles;
import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.Ratings;
import com.venturedive.vendian_mono.repository.ProjectCyclesRepository;
import com.venturedive.vendian_mono.service.ProjectCyclesService;
import com.venturedive.vendian_mono.service.criteria.ProjectCyclesCriteria;
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
 * Integration tests for the {@link ProjectCyclesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProjectCyclesResourceIT {

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ALLOWEDAFTERDUEDATEAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ALLOWEDAFTERDUEDATEAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DUEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AUDITLOGS = "AAAAAAAAAA";
    private static final String UPDATED_AUDITLOGS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/project-cycles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectCyclesRepository projectCyclesRepository;

    @Mock
    private ProjectCyclesRepository projectCyclesRepositoryMock;

    @Mock
    private ProjectCyclesService projectCyclesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectCyclesMockMvc;

    private ProjectCycles projectCycles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectCycles createEntity(EntityManager em) {
        ProjectCycles projectCycles = new ProjectCycles()
            .isactive(DEFAULT_ISACTIVE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .allowedafterduedateat(DEFAULT_ALLOWEDAFTERDUEDATEAT)
            .duedate(DEFAULT_DUEDATE)
            .auditlogs(DEFAULT_AUDITLOGS)
            .deletedat(DEFAULT_DELETEDAT);
        return projectCycles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectCycles createUpdatedEntity(EntityManager em) {
        ProjectCycles projectCycles = new ProjectCycles()
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .allowedafterduedateat(UPDATED_ALLOWEDAFTERDUEDATEAT)
            .duedate(UPDATED_DUEDATE)
            .auditlogs(UPDATED_AUDITLOGS)
            .deletedat(UPDATED_DELETEDAT);
        return projectCycles;
    }

    @BeforeEach
    public void initTest() {
        projectCycles = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectCycles() throws Exception {
        int databaseSizeBeforeCreate = projectCyclesRepository.findAll().size();
        // Create the ProjectCycles
        restProjectCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectCycles testProjectCycles = projectCyclesList.get(projectCyclesList.size() - 1);
        assertThat(testProjectCycles.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testProjectCycles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjectCycles.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testProjectCycles.getAllowedafterduedateat()).isEqualTo(DEFAULT_ALLOWEDAFTERDUEDATEAT);
        assertThat(testProjectCycles.getDuedate()).isEqualTo(DEFAULT_DUEDATE);
        assertThat(testProjectCycles.getAuditlogs()).isEqualTo(DEFAULT_AUDITLOGS);
        assertThat(testProjectCycles.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createProjectCyclesWithExistingId() throws Exception {
        // Create the ProjectCycles with an existing ID
        projectCycles.setId(1L);

        int databaseSizeBeforeCreate = projectCyclesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectCyclesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectCycles() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList
        restProjectCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectCycles.getId().intValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].allowedafterduedateat").value(hasItem(DEFAULT_ALLOWEDAFTERDUEDATEAT.toString())))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())))
            .andExpect(jsonPath("$.[*].auditlogs").value(hasItem(DEFAULT_AUDITLOGS)))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjectCyclesWithEagerRelationshipsIsEnabled() throws Exception {
        when(projectCyclesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjectCyclesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(projectCyclesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjectCyclesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(projectCyclesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjectCyclesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(projectCyclesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProjectCycles() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get the projectCycles
        restProjectCyclesMockMvc
            .perform(get(ENTITY_API_URL_ID, projectCycles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectCycles.getId().intValue()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.allowedafterduedateat").value(DEFAULT_ALLOWEDAFTERDUEDATEAT.toString()))
            .andExpect(jsonPath("$.duedate").value(DEFAULT_DUEDATE.toString()))
            .andExpect(jsonPath("$.auditlogs").value(DEFAULT_AUDITLOGS))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getProjectCyclesByIdFiltering() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        Long id = projectCycles.getId();

        defaultProjectCyclesShouldBeFound("id.equals=" + id);
        defaultProjectCyclesShouldNotBeFound("id.notEquals=" + id);

        defaultProjectCyclesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectCyclesShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectCyclesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectCyclesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where isactive equals to DEFAULT_ISACTIVE
        defaultProjectCyclesShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the projectCyclesList where isactive equals to UPDATED_ISACTIVE
        defaultProjectCyclesShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultProjectCyclesShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the projectCyclesList where isactive equals to UPDATED_ISACTIVE
        defaultProjectCyclesShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where isactive is not null
        defaultProjectCyclesShouldBeFound("isactive.specified=true");

        // Get all the projectCyclesList where isactive is null
        defaultProjectCyclesShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCyclesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where createdat equals to DEFAULT_CREATEDAT
        defaultProjectCyclesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the projectCyclesList where createdat equals to UPDATED_CREATEDAT
        defaultProjectCyclesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultProjectCyclesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the projectCyclesList where createdat equals to UPDATED_CREATEDAT
        defaultProjectCyclesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where createdat is not null
        defaultProjectCyclesShouldBeFound("createdat.specified=true");

        // Get all the projectCyclesList where createdat is null
        defaultProjectCyclesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCyclesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultProjectCyclesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the projectCyclesList where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectCyclesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultProjectCyclesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the projectCyclesList where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectCyclesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where updatedat is not null
        defaultProjectCyclesShouldBeFound("updatedat.specified=true");

        // Get all the projectCyclesList where updatedat is null
        defaultProjectCyclesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAllowedafterduedateatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where allowedafterduedateat equals to DEFAULT_ALLOWEDAFTERDUEDATEAT
        defaultProjectCyclesShouldBeFound("allowedafterduedateat.equals=" + DEFAULT_ALLOWEDAFTERDUEDATEAT);

        // Get all the projectCyclesList where allowedafterduedateat equals to UPDATED_ALLOWEDAFTERDUEDATEAT
        defaultProjectCyclesShouldNotBeFound("allowedafterduedateat.equals=" + UPDATED_ALLOWEDAFTERDUEDATEAT);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAllowedafterduedateatIsInShouldWork() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where allowedafterduedateat in DEFAULT_ALLOWEDAFTERDUEDATEAT or UPDATED_ALLOWEDAFTERDUEDATEAT
        defaultProjectCyclesShouldBeFound(
            "allowedafterduedateat.in=" + DEFAULT_ALLOWEDAFTERDUEDATEAT + "," + UPDATED_ALLOWEDAFTERDUEDATEAT
        );

        // Get all the projectCyclesList where allowedafterduedateat equals to UPDATED_ALLOWEDAFTERDUEDATEAT
        defaultProjectCyclesShouldNotBeFound("allowedafterduedateat.in=" + UPDATED_ALLOWEDAFTERDUEDATEAT);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAllowedafterduedateatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where allowedafterduedateat is not null
        defaultProjectCyclesShouldBeFound("allowedafterduedateat.specified=true");

        // Get all the projectCyclesList where allowedafterduedateat is null
        defaultProjectCyclesShouldNotBeFound("allowedafterduedateat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCyclesByDuedateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where duedate equals to DEFAULT_DUEDATE
        defaultProjectCyclesShouldBeFound("duedate.equals=" + DEFAULT_DUEDATE);

        // Get all the projectCyclesList where duedate equals to UPDATED_DUEDATE
        defaultProjectCyclesShouldNotBeFound("duedate.equals=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByDuedateIsInShouldWork() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where duedate in DEFAULT_DUEDATE or UPDATED_DUEDATE
        defaultProjectCyclesShouldBeFound("duedate.in=" + DEFAULT_DUEDATE + "," + UPDATED_DUEDATE);

        // Get all the projectCyclesList where duedate equals to UPDATED_DUEDATE
        defaultProjectCyclesShouldNotBeFound("duedate.in=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByDuedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where duedate is not null
        defaultProjectCyclesShouldBeFound("duedate.specified=true");

        // Get all the projectCyclesList where duedate is null
        defaultProjectCyclesShouldNotBeFound("duedate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAuditlogsIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where auditlogs equals to DEFAULT_AUDITLOGS
        defaultProjectCyclesShouldBeFound("auditlogs.equals=" + DEFAULT_AUDITLOGS);

        // Get all the projectCyclesList where auditlogs equals to UPDATED_AUDITLOGS
        defaultProjectCyclesShouldNotBeFound("auditlogs.equals=" + UPDATED_AUDITLOGS);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAuditlogsIsInShouldWork() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where auditlogs in DEFAULT_AUDITLOGS or UPDATED_AUDITLOGS
        defaultProjectCyclesShouldBeFound("auditlogs.in=" + DEFAULT_AUDITLOGS + "," + UPDATED_AUDITLOGS);

        // Get all the projectCyclesList where auditlogs equals to UPDATED_AUDITLOGS
        defaultProjectCyclesShouldNotBeFound("auditlogs.in=" + UPDATED_AUDITLOGS);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAuditlogsIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where auditlogs is not null
        defaultProjectCyclesShouldBeFound("auditlogs.specified=true");

        // Get all the projectCyclesList where auditlogs is null
        defaultProjectCyclesShouldNotBeFound("auditlogs.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAuditlogsContainsSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where auditlogs contains DEFAULT_AUDITLOGS
        defaultProjectCyclesShouldBeFound("auditlogs.contains=" + DEFAULT_AUDITLOGS);

        // Get all the projectCyclesList where auditlogs contains UPDATED_AUDITLOGS
        defaultProjectCyclesShouldNotBeFound("auditlogs.contains=" + UPDATED_AUDITLOGS);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAuditlogsNotContainsSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where auditlogs does not contain DEFAULT_AUDITLOGS
        defaultProjectCyclesShouldNotBeFound("auditlogs.doesNotContain=" + DEFAULT_AUDITLOGS);

        // Get all the projectCyclesList where auditlogs does not contain UPDATED_AUDITLOGS
        defaultProjectCyclesShouldBeFound("auditlogs.doesNotContain=" + UPDATED_AUDITLOGS);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where deletedat equals to DEFAULT_DELETEDAT
        defaultProjectCyclesShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the projectCyclesList where deletedat equals to UPDATED_DELETEDAT
        defaultProjectCyclesShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultProjectCyclesShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the projectCyclesList where deletedat equals to UPDATED_DELETEDAT
        defaultProjectCyclesShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCyclesByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        // Get all the projectCyclesList where deletedat is not null
        defaultProjectCyclesShouldBeFound("deletedat.specified=true");

        // Get all the projectCyclesList where deletedat is null
        defaultProjectCyclesShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCyclesByProjectIsEqualToSomething() throws Exception {
        Projects project;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            projectCyclesRepository.saveAndFlush(projectCycles);
            project = ProjectsResourceIT.createEntity(em);
        } else {
            project = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(project);
        em.flush();
        projectCycles.setProject(project);
        projectCyclesRepository.saveAndFlush(projectCycles);
        Long projectId = project.getId();

        // Get all the projectCyclesList where project equals to projectId
        defaultProjectCyclesShouldBeFound("projectId.equals=" + projectId);

        // Get all the projectCyclesList where project equals to (projectId + 1)
        defaultProjectCyclesShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectCyclesByAllowedafterduedatebyIsEqualToSomething() throws Exception {
        Employees allowedafterduedateby;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            projectCyclesRepository.saveAndFlush(projectCycles);
            allowedafterduedateby = EmployeesResourceIT.createEntity(em);
        } else {
            allowedafterduedateby = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(allowedafterduedateby);
        em.flush();
        projectCycles.setAllowedafterduedateby(allowedafterduedateby);
        projectCyclesRepository.saveAndFlush(projectCycles);
        Long allowedafterduedatebyId = allowedafterduedateby.getId();

        // Get all the projectCyclesList where allowedafterduedateby equals to allowedafterduedatebyId
        defaultProjectCyclesShouldBeFound("allowedafterduedatebyId.equals=" + allowedafterduedatebyId);

        // Get all the projectCyclesList where allowedafterduedateby equals to (allowedafterduedatebyId + 1)
        defaultProjectCyclesShouldNotBeFound("allowedafterduedatebyId.equals=" + (allowedafterduedatebyId + 1));
    }

    @Test
    @Transactional
    void getAllProjectCyclesByArchitectIsEqualToSomething() throws Exception {
        Employees architect;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            projectCyclesRepository.saveAndFlush(projectCycles);
            architect = EmployeesResourceIT.createEntity(em);
        } else {
            architect = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(architect);
        em.flush();
        projectCycles.setArchitect(architect);
        projectCyclesRepository.saveAndFlush(projectCycles);
        Long architectId = architect.getId();

        // Get all the projectCyclesList where architect equals to architectId
        defaultProjectCyclesShouldBeFound("architectId.equals=" + architectId);

        // Get all the projectCyclesList where architect equals to (architectId + 1)
        defaultProjectCyclesShouldNotBeFound("architectId.equals=" + (architectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectCyclesByProjectmanagerIsEqualToSomething() throws Exception {
        Employees projectmanager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            projectCyclesRepository.saveAndFlush(projectCycles);
            projectmanager = EmployeesResourceIT.createEntity(em);
        } else {
            projectmanager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(projectmanager);
        em.flush();
        projectCycles.setProjectmanager(projectmanager);
        projectCyclesRepository.saveAndFlush(projectCycles);
        Long projectmanagerId = projectmanager.getId();

        // Get all the projectCyclesList where projectmanager equals to projectmanagerId
        defaultProjectCyclesShouldBeFound("projectmanagerId.equals=" + projectmanagerId);

        // Get all the projectCyclesList where projectmanager equals to (projectmanagerId + 1)
        defaultProjectCyclesShouldNotBeFound("projectmanagerId.equals=" + (projectmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllProjectCyclesByRatingIsEqualToSomething() throws Exception {
        Ratings rating;
        if (TestUtil.findAll(em, Ratings.class).isEmpty()) {
            projectCyclesRepository.saveAndFlush(projectCycles);
            rating = RatingsResourceIT.createEntity(em);
        } else {
            rating = TestUtil.findAll(em, Ratings.class).get(0);
        }
        em.persist(rating);
        em.flush();
        projectCycles.addRating(rating);
        projectCyclesRepository.saveAndFlush(projectCycles);
        Long ratingId = rating.getId();

        // Get all the projectCyclesList where rating equals to ratingId
        defaultProjectCyclesShouldBeFound("ratingId.equals=" + ratingId);

        // Get all the projectCyclesList where rating equals to (ratingId + 1)
        defaultProjectCyclesShouldNotBeFound("ratingId.equals=" + (ratingId + 1));
    }

    @Test
    @Transactional
    void getAllProjectCyclesByEmployeeprojectratingsProjectcycleIsEqualToSomething() throws Exception {
        EmployeeProjectRatings employeeprojectratingsProjectcycle;
        if (TestUtil.findAll(em, EmployeeProjectRatings.class).isEmpty()) {
            projectCyclesRepository.saveAndFlush(projectCycles);
            employeeprojectratingsProjectcycle = EmployeeProjectRatingsResourceIT.createEntity(em);
        } else {
            employeeprojectratingsProjectcycle = TestUtil.findAll(em, EmployeeProjectRatings.class).get(0);
        }
        em.persist(employeeprojectratingsProjectcycle);
        em.flush();
        projectCycles.addEmployeeprojectratingsProjectcycle(employeeprojectratingsProjectcycle);
        projectCyclesRepository.saveAndFlush(projectCycles);
        Long employeeprojectratingsProjectcycleId = employeeprojectratingsProjectcycle.getId();

        // Get all the projectCyclesList where employeeprojectratingsProjectcycle equals to employeeprojectratingsProjectcycleId
        defaultProjectCyclesShouldBeFound("employeeprojectratingsProjectcycleId.equals=" + employeeprojectratingsProjectcycleId);

        // Get all the projectCyclesList where employeeprojectratingsProjectcycle equals to (employeeprojectratingsProjectcycleId + 1)
        defaultProjectCyclesShouldNotBeFound("employeeprojectratingsProjectcycleId.equals=" + (employeeprojectratingsProjectcycleId + 1));
    }

    @Test
    @Transactional
    void getAllProjectCyclesByPerformancecycleIsEqualToSomething() throws Exception {
        PerformanceCycles performancecycle;
        if (TestUtil.findAll(em, PerformanceCycles.class).isEmpty()) {
            projectCyclesRepository.saveAndFlush(projectCycles);
            performancecycle = PerformanceCyclesResourceIT.createEntity(em);
        } else {
            performancecycle = TestUtil.findAll(em, PerformanceCycles.class).get(0);
        }
        em.persist(performancecycle);
        em.flush();
        projectCycles.addPerformancecycle(performancecycle);
        projectCyclesRepository.saveAndFlush(projectCycles);
        Long performancecycleId = performancecycle.getId();

        // Get all the projectCyclesList where performancecycle equals to performancecycleId
        defaultProjectCyclesShouldBeFound("performancecycleId.equals=" + performancecycleId);

        // Get all the projectCyclesList where performancecycle equals to (performancecycleId + 1)
        defaultProjectCyclesShouldNotBeFound("performancecycleId.equals=" + (performancecycleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectCyclesShouldBeFound(String filter) throws Exception {
        restProjectCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectCycles.getId().intValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].allowedafterduedateat").value(hasItem(DEFAULT_ALLOWEDAFTERDUEDATEAT.toString())))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())))
            .andExpect(jsonPath("$.[*].auditlogs").value(hasItem(DEFAULT_AUDITLOGS)))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restProjectCyclesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectCyclesShouldNotBeFound(String filter) throws Exception {
        restProjectCyclesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectCyclesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProjectCycles() throws Exception {
        // Get the projectCycles
        restProjectCyclesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectCycles() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();

        // Update the projectCycles
        ProjectCycles updatedProjectCycles = projectCyclesRepository.findById(projectCycles.getId()).get();
        // Disconnect from session so that the updates on updatedProjectCycles are not directly saved in db
        em.detach(updatedProjectCycles);
        updatedProjectCycles
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .allowedafterduedateat(UPDATED_ALLOWEDAFTERDUEDATEAT)
            .duedate(UPDATED_DUEDATE)
            .auditlogs(UPDATED_AUDITLOGS)
            .deletedat(UPDATED_DELETEDAT);

        restProjectCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectCycles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjectCycles))
            )
            .andExpect(status().isOk());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
        ProjectCycles testProjectCycles = projectCyclesList.get(projectCyclesList.size() - 1);
        assertThat(testProjectCycles.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testProjectCycles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectCycles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectCycles.getAllowedafterduedateat()).isEqualTo(UPDATED_ALLOWEDAFTERDUEDATEAT);
        assertThat(testProjectCycles.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testProjectCycles.getAuditlogs()).isEqualTo(UPDATED_AUDITLOGS);
        assertThat(testProjectCycles.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingProjectCycles() throws Exception {
        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();
        projectCycles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectCycles.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectCycles() throws Exception {
        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();
        projectCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCyclesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectCycles() throws Exception {
        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();
        projectCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCyclesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectCyclesWithPatch() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();

        // Update the projectCycles using partial update
        ProjectCycles partialUpdatedProjectCycles = new ProjectCycles();
        partialUpdatedProjectCycles.setId(projectCycles.getId());

        partialUpdatedProjectCycles
            .updatedat(UPDATED_UPDATEDAT)
            .allowedafterduedateat(UPDATED_ALLOWEDAFTERDUEDATEAT)
            .duedate(UPDATED_DUEDATE)
            .auditlogs(UPDATED_AUDITLOGS)
            .deletedat(UPDATED_DELETEDAT);

        restProjectCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectCycles))
            )
            .andExpect(status().isOk());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
        ProjectCycles testProjectCycles = projectCyclesList.get(projectCyclesList.size() - 1);
        assertThat(testProjectCycles.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testProjectCycles.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjectCycles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectCycles.getAllowedafterduedateat()).isEqualTo(UPDATED_ALLOWEDAFTERDUEDATEAT);
        assertThat(testProjectCycles.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testProjectCycles.getAuditlogs()).isEqualTo(UPDATED_AUDITLOGS);
        assertThat(testProjectCycles.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateProjectCyclesWithPatch() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();

        // Update the projectCycles using partial update
        ProjectCycles partialUpdatedProjectCycles = new ProjectCycles();
        partialUpdatedProjectCycles.setId(projectCycles.getId());

        partialUpdatedProjectCycles
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .allowedafterduedateat(UPDATED_ALLOWEDAFTERDUEDATEAT)
            .duedate(UPDATED_DUEDATE)
            .auditlogs(UPDATED_AUDITLOGS)
            .deletedat(UPDATED_DELETEDAT);

        restProjectCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectCycles))
            )
            .andExpect(status().isOk());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
        ProjectCycles testProjectCycles = projectCyclesList.get(projectCyclesList.size() - 1);
        assertThat(testProjectCycles.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testProjectCycles.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectCycles.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectCycles.getAllowedafterduedateat()).isEqualTo(UPDATED_ALLOWEDAFTERDUEDATEAT);
        assertThat(testProjectCycles.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testProjectCycles.getAuditlogs()).isEqualTo(UPDATED_AUDITLOGS);
        assertThat(testProjectCycles.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingProjectCycles() throws Exception {
        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();
        projectCycles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectCycles.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectCycles() throws Exception {
        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();
        projectCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectCycles() throws Exception {
        int databaseSizeBeforeUpdate = projectCyclesRepository.findAll().size();
        projectCycles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCyclesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectCycles in the database
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectCycles() throws Exception {
        // Initialize the database
        projectCyclesRepository.saveAndFlush(projectCycles);

        int databaseSizeBeforeDelete = projectCyclesRepository.findAll().size();

        // Delete the projectCycles
        restProjectCyclesMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectCycles.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectCycles> projectCyclesList = projectCyclesRepository.findAll();
        assertThat(projectCyclesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
