package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.BusinessUnits;
import com.venturedive.vendian_mono.domain.EmployeeProjects;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Interviews;
import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.domain.ProjectLeadership;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.domain.Questions;
import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerClientTrack;
import com.venturedive.vendian_mono.domain.WorkLogDetails;
import com.venturedive.vendian_mono.domain.ZEmployeeProjects;
import com.venturedive.vendian_mono.repository.ProjectsRepository;
import com.venturedive.vendian_mono.service.criteria.ProjectsCriteria;
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
 * Integration tests for the {@link ProjectsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Boolean DEFAULT_ISDELETE = false;
    private static final Boolean UPDATED_ISDELETE = true;

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COLORCODE = "AAAAAAAAAA";
    private static final String UPDATED_COLORCODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DELIVERYMANAGERID = 1;
    private static final Integer UPDATED_DELIVERYMANAGERID = 2;
    private static final Integer SMALLER_DELIVERYMANAGERID = 1 - 1;

    private static final Integer DEFAULT_ARCHITECTID = 1;
    private static final Integer UPDATED_ARCHITECTID = 2;
    private static final Integer SMALLER_ARCHITECTID = 1 - 1;

    private static final Integer DEFAULT_ISDELETED = 1;
    private static final Integer UPDATED_ISDELETED = 2;
    private static final Integer SMALLER_ISDELETED = 1 - 1;

    private static final Integer DEFAULT_APPROVEDRESOURCES = 1;
    private static final Integer UPDATED_APPROVEDRESOURCES = 2;
    private static final Integer SMALLER_APPROVEDRESOURCES = 1 - 1;

    private static final Instant DEFAULT_RELEASEAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RELEASEAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectsMockMvc;

    private Projects projects;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createEntity(EntityManager em) {
        Projects projects = new Projects()
            .name(DEFAULT_NAME)
            .isactive(DEFAULT_ISACTIVE)
            .isdelete(DEFAULT_ISDELETE)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .colorcode(DEFAULT_COLORCODE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deliverymanagerid(DEFAULT_DELIVERYMANAGERID)
            .architectid(DEFAULT_ARCHITECTID)
            .isdeleted(DEFAULT_ISDELETED)
            .approvedresources(DEFAULT_APPROVEDRESOURCES)
            .releaseat(DEFAULT_RELEASEAT);
        return projects;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createUpdatedEntity(EntityManager em) {
        Projects projects = new Projects()
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE)
            .isdelete(UPDATED_ISDELETE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .colorcode(UPDATED_COLORCODE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deliverymanagerid(UPDATED_DELIVERYMANAGERID)
            .architectid(UPDATED_ARCHITECTID)
            .isdeleted(UPDATED_ISDELETED)
            .approvedresources(UPDATED_APPROVEDRESOURCES)
            .releaseat(UPDATED_RELEASEAT);
        return projects;
    }

    @BeforeEach
    public void initTest() {
        projects = createEntity(em);
    }

    @Test
    @Transactional
    void createProjects() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();
        // Create the Projects
        restProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isCreated());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate + 1);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjects.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testProjects.getIsdelete()).isEqualTo(DEFAULT_ISDELETE);
        assertThat(testProjects.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testProjects.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testProjects.getColorcode()).isEqualTo(DEFAULT_COLORCODE);
        assertThat(testProjects.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjects.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testProjects.getDeliverymanagerid()).isEqualTo(DEFAULT_DELIVERYMANAGERID);
        assertThat(testProjects.getArchitectid()).isEqualTo(DEFAULT_ARCHITECTID);
        assertThat(testProjects.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testProjects.getApprovedresources()).isEqualTo(DEFAULT_APPROVEDRESOURCES);
        assertThat(testProjects.getReleaseat()).isEqualTo(DEFAULT_RELEASEAT);
    }

    @Test
    @Transactional
    void createProjectsWithExistingId() throws Exception {
        // Create the Projects with an existing ID
        projects.setId(1L);

        int databaseSizeBeforeCreate = projectsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectsRepository.findAll().size();
        // set the field null
        projects.setCreatedat(null);

        // Create the Projects, which fails.

        restProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isBadRequest());

        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectsRepository.findAll().size();
        // set the field null
        projects.setUpdatedat(null);

        // Create the Projects, which fails.

        restProjectsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isBadRequest());

        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isdelete").value(hasItem(DEFAULT_ISDELETE.booleanValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].colorcode").value(hasItem(DEFAULT_COLORCODE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deliverymanagerid").value(hasItem(DEFAULT_DELIVERYMANAGERID)))
            .andExpect(jsonPath("$.[*].architectid").value(hasItem(DEFAULT_ARCHITECTID)))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED)))
            .andExpect(jsonPath("$.[*].approvedresources").value(hasItem(DEFAULT_APPROVEDRESOURCES)))
            .andExpect(jsonPath("$.[*].releaseat").value(hasItem(DEFAULT_RELEASEAT.toString())));
    }

    @Test
    @Transactional
    void getProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get the projects
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL_ID, projects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projects.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.isdelete").value(DEFAULT_ISDELETE.booleanValue()))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.colorcode").value(DEFAULT_COLORCODE))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deliverymanagerid").value(DEFAULT_DELIVERYMANAGERID))
            .andExpect(jsonPath("$.architectid").value(DEFAULT_ARCHITECTID))
            .andExpect(jsonPath("$.isdeleted").value(DEFAULT_ISDELETED))
            .andExpect(jsonPath("$.approvedresources").value(DEFAULT_APPROVEDRESOURCES))
            .andExpect(jsonPath("$.releaseat").value(DEFAULT_RELEASEAT.toString()));
    }

    @Test
    @Transactional
    void getProjectsByIdFiltering() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        Long id = projects.getId();

        defaultProjectsShouldBeFound("id.equals=" + id);
        defaultProjectsShouldNotBeFound("id.notEquals=" + id);

        defaultProjectsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectsShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where name equals to DEFAULT_NAME
        defaultProjectsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectsList where name equals to UPDATED_NAME
        defaultProjectsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectsList where name equals to UPDATED_NAME
        defaultProjectsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where name is not null
        defaultProjectsShouldBeFound("name.specified=true");

        // Get all the projectsList where name is null
        defaultProjectsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByNameContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where name contains DEFAULT_NAME
        defaultProjectsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the projectsList where name contains UPDATED_NAME
        defaultProjectsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where name does not contain DEFAULT_NAME
        defaultProjectsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the projectsList where name does not contain UPDATED_NAME
        defaultProjectsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isactive equals to DEFAULT_ISACTIVE
        defaultProjectsShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the projectsList where isactive equals to UPDATED_ISACTIVE
        defaultProjectsShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllProjectsByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultProjectsShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the projectsList where isactive equals to UPDATED_ISACTIVE
        defaultProjectsShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllProjectsByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isactive is not null
        defaultProjectsShouldBeFound("isactive.specified=true");

        // Get all the projectsList where isactive is null
        defaultProjectsShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeleteIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdelete equals to DEFAULT_ISDELETE
        defaultProjectsShouldBeFound("isdelete.equals=" + DEFAULT_ISDELETE);

        // Get all the projectsList where isdelete equals to UPDATED_ISDELETE
        defaultProjectsShouldNotBeFound("isdelete.equals=" + UPDATED_ISDELETE);
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeleteIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdelete in DEFAULT_ISDELETE or UPDATED_ISDELETE
        defaultProjectsShouldBeFound("isdelete.in=" + DEFAULT_ISDELETE + "," + UPDATED_ISDELETE);

        // Get all the projectsList where isdelete equals to UPDATED_ISDELETE
        defaultProjectsShouldNotBeFound("isdelete.in=" + UPDATED_ISDELETE);
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeleteIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdelete is not null
        defaultProjectsShouldBeFound("isdelete.specified=true");

        // Get all the projectsList where isdelete is null
        defaultProjectsShouldNotBeFound("isdelete.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startdate equals to DEFAULT_STARTDATE
        defaultProjectsShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the projectsList where startdate equals to UPDATED_STARTDATE
        defaultProjectsShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultProjectsShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the projectsList where startdate equals to UPDATED_STARTDATE
        defaultProjectsShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startdate is not null
        defaultProjectsShouldBeFound("startdate.specified=true");

        // Get all the projectsList where startdate is null
        defaultProjectsShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where enddate equals to DEFAULT_ENDDATE
        defaultProjectsShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the projectsList where enddate equals to UPDATED_ENDDATE
        defaultProjectsShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultProjectsShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the projectsList where enddate equals to UPDATED_ENDDATE
        defaultProjectsShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where enddate is not null
        defaultProjectsShouldBeFound("enddate.specified=true");

        // Get all the projectsList where enddate is null
        defaultProjectsShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByColorcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where colorcode equals to DEFAULT_COLORCODE
        defaultProjectsShouldBeFound("colorcode.equals=" + DEFAULT_COLORCODE);

        // Get all the projectsList where colorcode equals to UPDATED_COLORCODE
        defaultProjectsShouldNotBeFound("colorcode.equals=" + UPDATED_COLORCODE);
    }

    @Test
    @Transactional
    void getAllProjectsByColorcodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where colorcode in DEFAULT_COLORCODE or UPDATED_COLORCODE
        defaultProjectsShouldBeFound("colorcode.in=" + DEFAULT_COLORCODE + "," + UPDATED_COLORCODE);

        // Get all the projectsList where colorcode equals to UPDATED_COLORCODE
        defaultProjectsShouldNotBeFound("colorcode.in=" + UPDATED_COLORCODE);
    }

    @Test
    @Transactional
    void getAllProjectsByColorcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where colorcode is not null
        defaultProjectsShouldBeFound("colorcode.specified=true");

        // Get all the projectsList where colorcode is null
        defaultProjectsShouldNotBeFound("colorcode.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByColorcodeContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where colorcode contains DEFAULT_COLORCODE
        defaultProjectsShouldBeFound("colorcode.contains=" + DEFAULT_COLORCODE);

        // Get all the projectsList where colorcode contains UPDATED_COLORCODE
        defaultProjectsShouldNotBeFound("colorcode.contains=" + UPDATED_COLORCODE);
    }

    @Test
    @Transactional
    void getAllProjectsByColorcodeNotContainsSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where colorcode does not contain DEFAULT_COLORCODE
        defaultProjectsShouldNotBeFound("colorcode.doesNotContain=" + DEFAULT_COLORCODE);

        // Get all the projectsList where colorcode does not contain UPDATED_COLORCODE
        defaultProjectsShouldBeFound("colorcode.doesNotContain=" + UPDATED_COLORCODE);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdat equals to DEFAULT_CREATEDAT
        defaultProjectsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the projectsList where createdat equals to UPDATED_CREATEDAT
        defaultProjectsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultProjectsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the projectsList where createdat equals to UPDATED_CREATEDAT
        defaultProjectsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where createdat is not null
        defaultProjectsShouldBeFound("createdat.specified=true");

        // Get all the projectsList where createdat is null
        defaultProjectsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultProjectsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the projectsList where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultProjectsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the projectsList where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where updatedat is not null
        defaultProjectsShouldBeFound("updatedat.specified=true");

        // Get all the projectsList where updatedat is null
        defaultProjectsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByDeliverymanageridIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deliverymanagerid equals to DEFAULT_DELIVERYMANAGERID
        defaultProjectsShouldBeFound("deliverymanagerid.equals=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the projectsList where deliverymanagerid equals to UPDATED_DELIVERYMANAGERID
        defaultProjectsShouldNotBeFound("deliverymanagerid.equals=" + UPDATED_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllProjectsByDeliverymanageridIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deliverymanagerid in DEFAULT_DELIVERYMANAGERID or UPDATED_DELIVERYMANAGERID
        defaultProjectsShouldBeFound("deliverymanagerid.in=" + DEFAULT_DELIVERYMANAGERID + "," + UPDATED_DELIVERYMANAGERID);

        // Get all the projectsList where deliverymanagerid equals to UPDATED_DELIVERYMANAGERID
        defaultProjectsShouldNotBeFound("deliverymanagerid.in=" + UPDATED_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllProjectsByDeliverymanageridIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deliverymanagerid is not null
        defaultProjectsShouldBeFound("deliverymanagerid.specified=true");

        // Get all the projectsList where deliverymanagerid is null
        defaultProjectsShouldNotBeFound("deliverymanagerid.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByDeliverymanageridIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deliverymanagerid is greater than or equal to DEFAULT_DELIVERYMANAGERID
        defaultProjectsShouldBeFound("deliverymanagerid.greaterThanOrEqual=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the projectsList where deliverymanagerid is greater than or equal to UPDATED_DELIVERYMANAGERID
        defaultProjectsShouldNotBeFound("deliverymanagerid.greaterThanOrEqual=" + UPDATED_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllProjectsByDeliverymanageridIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deliverymanagerid is less than or equal to DEFAULT_DELIVERYMANAGERID
        defaultProjectsShouldBeFound("deliverymanagerid.lessThanOrEqual=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the projectsList where deliverymanagerid is less than or equal to SMALLER_DELIVERYMANAGERID
        defaultProjectsShouldNotBeFound("deliverymanagerid.lessThanOrEqual=" + SMALLER_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllProjectsByDeliverymanageridIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deliverymanagerid is less than DEFAULT_DELIVERYMANAGERID
        defaultProjectsShouldNotBeFound("deliverymanagerid.lessThan=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the projectsList where deliverymanagerid is less than UPDATED_DELIVERYMANAGERID
        defaultProjectsShouldBeFound("deliverymanagerid.lessThan=" + UPDATED_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllProjectsByDeliverymanageridIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where deliverymanagerid is greater than DEFAULT_DELIVERYMANAGERID
        defaultProjectsShouldNotBeFound("deliverymanagerid.greaterThan=" + DEFAULT_DELIVERYMANAGERID);

        // Get all the projectsList where deliverymanagerid is greater than SMALLER_DELIVERYMANAGERID
        defaultProjectsShouldBeFound("deliverymanagerid.greaterThan=" + SMALLER_DELIVERYMANAGERID);
    }

    @Test
    @Transactional
    void getAllProjectsByArchitectidIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where architectid equals to DEFAULT_ARCHITECTID
        defaultProjectsShouldBeFound("architectid.equals=" + DEFAULT_ARCHITECTID);

        // Get all the projectsList where architectid equals to UPDATED_ARCHITECTID
        defaultProjectsShouldNotBeFound("architectid.equals=" + UPDATED_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllProjectsByArchitectidIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where architectid in DEFAULT_ARCHITECTID or UPDATED_ARCHITECTID
        defaultProjectsShouldBeFound("architectid.in=" + DEFAULT_ARCHITECTID + "," + UPDATED_ARCHITECTID);

        // Get all the projectsList where architectid equals to UPDATED_ARCHITECTID
        defaultProjectsShouldNotBeFound("architectid.in=" + UPDATED_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllProjectsByArchitectidIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where architectid is not null
        defaultProjectsShouldBeFound("architectid.specified=true");

        // Get all the projectsList where architectid is null
        defaultProjectsShouldNotBeFound("architectid.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByArchitectidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where architectid is greater than or equal to DEFAULT_ARCHITECTID
        defaultProjectsShouldBeFound("architectid.greaterThanOrEqual=" + DEFAULT_ARCHITECTID);

        // Get all the projectsList where architectid is greater than or equal to UPDATED_ARCHITECTID
        defaultProjectsShouldNotBeFound("architectid.greaterThanOrEqual=" + UPDATED_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllProjectsByArchitectidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where architectid is less than or equal to DEFAULT_ARCHITECTID
        defaultProjectsShouldBeFound("architectid.lessThanOrEqual=" + DEFAULT_ARCHITECTID);

        // Get all the projectsList where architectid is less than or equal to SMALLER_ARCHITECTID
        defaultProjectsShouldNotBeFound("architectid.lessThanOrEqual=" + SMALLER_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllProjectsByArchitectidIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where architectid is less than DEFAULT_ARCHITECTID
        defaultProjectsShouldNotBeFound("architectid.lessThan=" + DEFAULT_ARCHITECTID);

        // Get all the projectsList where architectid is less than UPDATED_ARCHITECTID
        defaultProjectsShouldBeFound("architectid.lessThan=" + UPDATED_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllProjectsByArchitectidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where architectid is greater than DEFAULT_ARCHITECTID
        defaultProjectsShouldNotBeFound("architectid.greaterThan=" + DEFAULT_ARCHITECTID);

        // Get all the projectsList where architectid is greater than SMALLER_ARCHITECTID
        defaultProjectsShouldBeFound("architectid.greaterThan=" + SMALLER_ARCHITECTID);
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdeleted equals to DEFAULT_ISDELETED
        defaultProjectsShouldBeFound("isdeleted.equals=" + DEFAULT_ISDELETED);

        // Get all the projectsList where isdeleted equals to UPDATED_ISDELETED
        defaultProjectsShouldNotBeFound("isdeleted.equals=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeletedIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdeleted in DEFAULT_ISDELETED or UPDATED_ISDELETED
        defaultProjectsShouldBeFound("isdeleted.in=" + DEFAULT_ISDELETED + "," + UPDATED_ISDELETED);

        // Get all the projectsList where isdeleted equals to UPDATED_ISDELETED
        defaultProjectsShouldNotBeFound("isdeleted.in=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdeleted is not null
        defaultProjectsShouldBeFound("isdeleted.specified=true");

        // Get all the projectsList where isdeleted is null
        defaultProjectsShouldNotBeFound("isdeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeletedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdeleted is greater than or equal to DEFAULT_ISDELETED
        defaultProjectsShouldBeFound("isdeleted.greaterThanOrEqual=" + DEFAULT_ISDELETED);

        // Get all the projectsList where isdeleted is greater than or equal to UPDATED_ISDELETED
        defaultProjectsShouldNotBeFound("isdeleted.greaterThanOrEqual=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeletedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdeleted is less than or equal to DEFAULT_ISDELETED
        defaultProjectsShouldBeFound("isdeleted.lessThanOrEqual=" + DEFAULT_ISDELETED);

        // Get all the projectsList where isdeleted is less than or equal to SMALLER_ISDELETED
        defaultProjectsShouldNotBeFound("isdeleted.lessThanOrEqual=" + SMALLER_ISDELETED);
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeletedIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdeleted is less than DEFAULT_ISDELETED
        defaultProjectsShouldNotBeFound("isdeleted.lessThan=" + DEFAULT_ISDELETED);

        // Get all the projectsList where isdeleted is less than UPDATED_ISDELETED
        defaultProjectsShouldBeFound("isdeleted.lessThan=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllProjectsByIsdeletedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where isdeleted is greater than DEFAULT_ISDELETED
        defaultProjectsShouldNotBeFound("isdeleted.greaterThan=" + DEFAULT_ISDELETED);

        // Get all the projectsList where isdeleted is greater than SMALLER_ISDELETED
        defaultProjectsShouldBeFound("isdeleted.greaterThan=" + SMALLER_ISDELETED);
    }

    @Test
    @Transactional
    void getAllProjectsByApprovedresourcesIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where approvedresources equals to DEFAULT_APPROVEDRESOURCES
        defaultProjectsShouldBeFound("approvedresources.equals=" + DEFAULT_APPROVEDRESOURCES);

        // Get all the projectsList where approvedresources equals to UPDATED_APPROVEDRESOURCES
        defaultProjectsShouldNotBeFound("approvedresources.equals=" + UPDATED_APPROVEDRESOURCES);
    }

    @Test
    @Transactional
    void getAllProjectsByApprovedresourcesIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where approvedresources in DEFAULT_APPROVEDRESOURCES or UPDATED_APPROVEDRESOURCES
        defaultProjectsShouldBeFound("approvedresources.in=" + DEFAULT_APPROVEDRESOURCES + "," + UPDATED_APPROVEDRESOURCES);

        // Get all the projectsList where approvedresources equals to UPDATED_APPROVEDRESOURCES
        defaultProjectsShouldNotBeFound("approvedresources.in=" + UPDATED_APPROVEDRESOURCES);
    }

    @Test
    @Transactional
    void getAllProjectsByApprovedresourcesIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where approvedresources is not null
        defaultProjectsShouldBeFound("approvedresources.specified=true");

        // Get all the projectsList where approvedresources is null
        defaultProjectsShouldNotBeFound("approvedresources.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByApprovedresourcesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where approvedresources is greater than or equal to DEFAULT_APPROVEDRESOURCES
        defaultProjectsShouldBeFound("approvedresources.greaterThanOrEqual=" + DEFAULT_APPROVEDRESOURCES);

        // Get all the projectsList where approvedresources is greater than or equal to UPDATED_APPROVEDRESOURCES
        defaultProjectsShouldNotBeFound("approvedresources.greaterThanOrEqual=" + UPDATED_APPROVEDRESOURCES);
    }

    @Test
    @Transactional
    void getAllProjectsByApprovedresourcesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where approvedresources is less than or equal to DEFAULT_APPROVEDRESOURCES
        defaultProjectsShouldBeFound("approvedresources.lessThanOrEqual=" + DEFAULT_APPROVEDRESOURCES);

        // Get all the projectsList where approvedresources is less than or equal to SMALLER_APPROVEDRESOURCES
        defaultProjectsShouldNotBeFound("approvedresources.lessThanOrEqual=" + SMALLER_APPROVEDRESOURCES);
    }

    @Test
    @Transactional
    void getAllProjectsByApprovedresourcesIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where approvedresources is less than DEFAULT_APPROVEDRESOURCES
        defaultProjectsShouldNotBeFound("approvedresources.lessThan=" + DEFAULT_APPROVEDRESOURCES);

        // Get all the projectsList where approvedresources is less than UPDATED_APPROVEDRESOURCES
        defaultProjectsShouldBeFound("approvedresources.lessThan=" + UPDATED_APPROVEDRESOURCES);
    }

    @Test
    @Transactional
    void getAllProjectsByApprovedresourcesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where approvedresources is greater than DEFAULT_APPROVEDRESOURCES
        defaultProjectsShouldNotBeFound("approvedresources.greaterThan=" + DEFAULT_APPROVEDRESOURCES);

        // Get all the projectsList where approvedresources is greater than SMALLER_APPROVEDRESOURCES
        defaultProjectsShouldBeFound("approvedresources.greaterThan=" + SMALLER_APPROVEDRESOURCES);
    }

    @Test
    @Transactional
    void getAllProjectsByReleaseatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where releaseat equals to DEFAULT_RELEASEAT
        defaultProjectsShouldBeFound("releaseat.equals=" + DEFAULT_RELEASEAT);

        // Get all the projectsList where releaseat equals to UPDATED_RELEASEAT
        defaultProjectsShouldNotBeFound("releaseat.equals=" + UPDATED_RELEASEAT);
    }

    @Test
    @Transactional
    void getAllProjectsByReleaseatIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where releaseat in DEFAULT_RELEASEAT or UPDATED_RELEASEAT
        defaultProjectsShouldBeFound("releaseat.in=" + DEFAULT_RELEASEAT + "," + UPDATED_RELEASEAT);

        // Get all the projectsList where releaseat equals to UPDATED_RELEASEAT
        defaultProjectsShouldNotBeFound("releaseat.in=" + UPDATED_RELEASEAT);
    }

    @Test
    @Transactional
    void getAllProjectsByReleaseatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where releaseat is not null
        defaultProjectsShouldBeFound("releaseat.specified=true");

        // Get all the projectsList where releaseat is null
        defaultProjectsShouldNotBeFound("releaseat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByProjectmanagerIsEqualToSomething() throws Exception {
        Employees projectmanager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            projectmanager = EmployeesResourceIT.createEntity(em);
        } else {
            projectmanager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(projectmanager);
        em.flush();
        projects.setProjectmanager(projectmanager);
        projectsRepository.saveAndFlush(projects);
        Long projectmanagerId = projectmanager.getId();

        // Get all the projectsList where projectmanager equals to projectmanagerId
        defaultProjectsShouldBeFound("projectmanagerId.equals=" + projectmanagerId);

        // Get all the projectsList where projectmanager equals to (projectmanagerId + 1)
        defaultProjectsShouldNotBeFound("projectmanagerId.equals=" + (projectmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByBusinessunitIsEqualToSomething() throws Exception {
        BusinessUnits businessunit;
        if (TestUtil.findAll(em, BusinessUnits.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            businessunit = BusinessUnitsResourceIT.createEntity(em);
        } else {
            businessunit = TestUtil.findAll(em, BusinessUnits.class).get(0);
        }
        em.persist(businessunit);
        em.flush();
        projects.setBusinessunit(businessunit);
        projectsRepository.saveAndFlush(projects);
        Long businessunitId = businessunit.getId();

        // Get all the projectsList where businessunit equals to businessunitId
        defaultProjectsShouldBeFound("businessunitId.equals=" + businessunitId);

        // Get all the projectsList where businessunit equals to (businessunitId + 1)
        defaultProjectsShouldNotBeFound("businessunitId.equals=" + (businessunitId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByEmployeeprojectsProjectIsEqualToSomething() throws Exception {
        EmployeeProjects employeeprojectsProject;
        if (TestUtil.findAll(em, EmployeeProjects.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            employeeprojectsProject = EmployeeProjectsResourceIT.createEntity(em);
        } else {
            employeeprojectsProject = TestUtil.findAll(em, EmployeeProjects.class).get(0);
        }
        em.persist(employeeprojectsProject);
        em.flush();
        projects.addEmployeeprojectsProject(employeeprojectsProject);
        projectsRepository.saveAndFlush(projects);
        Long employeeprojectsProjectId = employeeprojectsProject.getId();

        // Get all the projectsList where employeeprojectsProject equals to employeeprojectsProjectId
        defaultProjectsShouldBeFound("employeeprojectsProjectId.equals=" + employeeprojectsProjectId);

        // Get all the projectsList where employeeprojectsProject equals to (employeeprojectsProjectId + 1)
        defaultProjectsShouldNotBeFound("employeeprojectsProjectId.equals=" + (employeeprojectsProjectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByInterviewsProjectIsEqualToSomething() throws Exception {
        Interviews interviewsProject;
        if (TestUtil.findAll(em, Interviews.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            interviewsProject = InterviewsResourceIT.createEntity(em);
        } else {
            interviewsProject = TestUtil.findAll(em, Interviews.class).get(0);
        }
        em.persist(interviewsProject);
        em.flush();
        projects.addInterviewsProject(interviewsProject);
        projectsRepository.saveAndFlush(projects);
        Long interviewsProjectId = interviewsProject.getId();

        // Get all the projectsList where interviewsProject equals to interviewsProjectId
        defaultProjectsShouldBeFound("interviewsProjectId.equals=" + interviewsProjectId);

        // Get all the projectsList where interviewsProject equals to (interviewsProjectId + 1)
        defaultProjectsShouldNotBeFound("interviewsProjectId.equals=" + (interviewsProjectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByProjectcyclesProjectIsEqualToSomething() throws Exception {
        ProjectCycles projectcyclesProject;
        if (TestUtil.findAll(em, ProjectCycles.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            projectcyclesProject = ProjectCyclesResourceIT.createEntity(em);
        } else {
            projectcyclesProject = TestUtil.findAll(em, ProjectCycles.class).get(0);
        }
        em.persist(projectcyclesProject);
        em.flush();
        projects.addProjectcyclesProject(projectcyclesProject);
        projectsRepository.saveAndFlush(projects);
        Long projectcyclesProjectId = projectcyclesProject.getId();

        // Get all the projectsList where projectcyclesProject equals to projectcyclesProjectId
        defaultProjectsShouldBeFound("projectcyclesProjectId.equals=" + projectcyclesProjectId);

        // Get all the projectsList where projectcyclesProject equals to (projectcyclesProjectId + 1)
        defaultProjectsShouldNotBeFound("projectcyclesProjectId.equals=" + (projectcyclesProjectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByProjectleadershipProjectIsEqualToSomething() throws Exception {
        ProjectLeadership projectleadershipProject;
        if (TestUtil.findAll(em, ProjectLeadership.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            projectleadershipProject = ProjectLeadershipResourceIT.createEntity(em);
        } else {
            projectleadershipProject = TestUtil.findAll(em, ProjectLeadership.class).get(0);
        }
        em.persist(projectleadershipProject);
        em.flush();
        projects.addProjectleadershipProject(projectleadershipProject);
        projectsRepository.saveAndFlush(projects);
        Long projectleadershipProjectId = projectleadershipProject.getId();

        // Get all the projectsList where projectleadershipProject equals to projectleadershipProjectId
        defaultProjectsShouldBeFound("projectleadershipProjectId.equals=" + projectleadershipProjectId);

        // Get all the projectsList where projectleadershipProject equals to (projectleadershipProjectId + 1)
        defaultProjectsShouldNotBeFound("projectleadershipProjectId.equals=" + (projectleadershipProjectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByQuestionsProjectIsEqualToSomething() throws Exception {
        Questions questionsProject;
        if (TestUtil.findAll(em, Questions.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            questionsProject = QuestionsResourceIT.createEntity(em);
        } else {
            questionsProject = TestUtil.findAll(em, Questions.class).get(0);
        }
        em.persist(questionsProject);
        em.flush();
        projects.addQuestionsProject(questionsProject);
        projectsRepository.saveAndFlush(projects);
        Long questionsProjectId = questionsProject.getId();

        // Get all the projectsList where questionsProject equals to questionsProjectId
        defaultProjectsShouldBeFound("questionsProjectId.equals=" + questionsProjectId);

        // Get all the projectsList where questionsProject equals to (questionsProjectId + 1)
        defaultProjectsShouldNotBeFound("questionsProjectId.equals=" + (questionsProjectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByQuestionsfrequencyperclienttrackProjectIsEqualToSomething() throws Exception {
        QuestionsFrequencyPerClientTrack questionsfrequencyperclienttrackProject;
        if (TestUtil.findAll(em, QuestionsFrequencyPerClientTrack.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            questionsfrequencyperclienttrackProject = QuestionsFrequencyPerClientTrackResourceIT.createEntity(em);
        } else {
            questionsfrequencyperclienttrackProject = TestUtil.findAll(em, QuestionsFrequencyPerClientTrack.class).get(0);
        }
        em.persist(questionsfrequencyperclienttrackProject);
        em.flush();
        projects.addQuestionsfrequencyperclienttrackProject(questionsfrequencyperclienttrackProject);
        projectsRepository.saveAndFlush(projects);
        Long questionsfrequencyperclienttrackProjectId = questionsfrequencyperclienttrackProject.getId();

        // Get all the projectsList where questionsfrequencyperclienttrackProject equals to questionsfrequencyperclienttrackProjectId
        defaultProjectsShouldBeFound("questionsfrequencyperclienttrackProjectId.equals=" + questionsfrequencyperclienttrackProjectId);

        // Get all the projectsList where questionsfrequencyperclienttrackProject equals to (questionsfrequencyperclienttrackProjectId + 1)
        defaultProjectsShouldNotBeFound(
            "questionsfrequencyperclienttrackProjectId.equals=" + (questionsfrequencyperclienttrackProjectId + 1)
        );
    }

    @Test
    @Transactional
    void getAllProjectsByWorklogdetailsProjectIsEqualToSomething() throws Exception {
        WorkLogDetails worklogdetailsProject;
        if (TestUtil.findAll(em, WorkLogDetails.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            worklogdetailsProject = WorkLogDetailsResourceIT.createEntity(em);
        } else {
            worklogdetailsProject = TestUtil.findAll(em, WorkLogDetails.class).get(0);
        }
        em.persist(worklogdetailsProject);
        em.flush();
        projects.addWorklogdetailsProject(worklogdetailsProject);
        projectsRepository.saveAndFlush(projects);
        Long worklogdetailsProjectId = worklogdetailsProject.getId();

        // Get all the projectsList where worklogdetailsProject equals to worklogdetailsProjectId
        defaultProjectsShouldBeFound("worklogdetailsProjectId.equals=" + worklogdetailsProjectId);

        // Get all the projectsList where worklogdetailsProject equals to (worklogdetailsProjectId + 1)
        defaultProjectsShouldNotBeFound("worklogdetailsProjectId.equals=" + (worklogdetailsProjectId + 1));
    }

    @Test
    @Transactional
    void getAllProjectsByZemployeeprojectsProjectIsEqualToSomething() throws Exception {
        ZEmployeeProjects zemployeeprojectsProject;
        if (TestUtil.findAll(em, ZEmployeeProjects.class).isEmpty()) {
            projectsRepository.saveAndFlush(projects);
            zemployeeprojectsProject = ZEmployeeProjectsResourceIT.createEntity(em);
        } else {
            zemployeeprojectsProject = TestUtil.findAll(em, ZEmployeeProjects.class).get(0);
        }
        em.persist(zemployeeprojectsProject);
        em.flush();
        projects.addZemployeeprojectsProject(zemployeeprojectsProject);
        projectsRepository.saveAndFlush(projects);
        Long zemployeeprojectsProjectId = zemployeeprojectsProject.getId();

        // Get all the projectsList where zemployeeprojectsProject equals to zemployeeprojectsProjectId
        defaultProjectsShouldBeFound("zemployeeprojectsProjectId.equals=" + zemployeeprojectsProjectId);

        // Get all the projectsList where zemployeeprojectsProject equals to (zemployeeprojectsProjectId + 1)
        defaultProjectsShouldNotBeFound("zemployeeprojectsProjectId.equals=" + (zemployeeprojectsProjectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectsShouldBeFound(String filter) throws Exception {
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isdelete").value(hasItem(DEFAULT_ISDELETE.booleanValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].colorcode").value(hasItem(DEFAULT_COLORCODE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deliverymanagerid").value(hasItem(DEFAULT_DELIVERYMANAGERID)))
            .andExpect(jsonPath("$.[*].architectid").value(hasItem(DEFAULT_ARCHITECTID)))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED)))
            .andExpect(jsonPath("$.[*].approvedresources").value(hasItem(DEFAULT_APPROVEDRESOURCES)))
            .andExpect(jsonPath("$.[*].releaseat").value(hasItem(DEFAULT_RELEASEAT.toString())));

        // Check, that the count call also returns 1
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectsShouldNotBeFound(String filter) throws Exception {
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProjects() throws Exception {
        // Get the projects
        restProjectsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects
        Projects updatedProjects = projectsRepository.findById(projects.getId()).get();
        // Disconnect from session so that the updates on updatedProjects are not directly saved in db
        em.detach(updatedProjects);
        updatedProjects
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE)
            .isdelete(UPDATED_ISDELETE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .colorcode(UPDATED_COLORCODE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deliverymanagerid(UPDATED_DELIVERYMANAGERID)
            .architectid(UPDATED_ARCHITECTID)
            .isdeleted(UPDATED_ISDELETED)
            .approvedresources(UPDATED_APPROVEDRESOURCES)
            .releaseat(UPDATED_RELEASEAT);

        restProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjects.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjects))
            )
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjects.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testProjects.getIsdelete()).isEqualTo(UPDATED_ISDELETE);
        assertThat(testProjects.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testProjects.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testProjects.getColorcode()).isEqualTo(UPDATED_COLORCODE);
        assertThat(testProjects.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjects.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjects.getDeliverymanagerid()).isEqualTo(UPDATED_DELIVERYMANAGERID);
        assertThat(testProjects.getArchitectid()).isEqualTo(UPDATED_ARCHITECTID);
        assertThat(testProjects.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testProjects.getApprovedresources()).isEqualTo(UPDATED_APPROVEDRESOURCES);
        assertThat(testProjects.getReleaseat()).isEqualTo(UPDATED_RELEASEAT);
    }

    @Test
    @Transactional
    void putNonExistingProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projects.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectsWithPatch() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects using partial update
        Projects partialUpdatedProjects = new Projects();
        partialUpdatedProjects.setId(projects.getId());

        partialUpdatedProjects
            .name(UPDATED_NAME)
            .startdate(UPDATED_STARTDATE)
            .deliverymanagerid(UPDATED_DELIVERYMANAGERID)
            .isdeleted(UPDATED_ISDELETED);

        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjects))
            )
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjects.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testProjects.getIsdelete()).isEqualTo(DEFAULT_ISDELETE);
        assertThat(testProjects.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testProjects.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testProjects.getColorcode()).isEqualTo(DEFAULT_COLORCODE);
        assertThat(testProjects.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjects.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testProjects.getDeliverymanagerid()).isEqualTo(UPDATED_DELIVERYMANAGERID);
        assertThat(testProjects.getArchitectid()).isEqualTo(DEFAULT_ARCHITECTID);
        assertThat(testProjects.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testProjects.getApprovedresources()).isEqualTo(DEFAULT_APPROVEDRESOURCES);
        assertThat(testProjects.getReleaseat()).isEqualTo(DEFAULT_RELEASEAT);
    }

    @Test
    @Transactional
    void fullUpdateProjectsWithPatch() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects using partial update
        Projects partialUpdatedProjects = new Projects();
        partialUpdatedProjects.setId(projects.getId());

        partialUpdatedProjects
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE)
            .isdelete(UPDATED_ISDELETE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .colorcode(UPDATED_COLORCODE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deliverymanagerid(UPDATED_DELIVERYMANAGERID)
            .architectid(UPDATED_ARCHITECTID)
            .isdeleted(UPDATED_ISDELETED)
            .approvedresources(UPDATED_APPROVEDRESOURCES)
            .releaseat(UPDATED_RELEASEAT);

        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjects))
            )
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjects.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testProjects.getIsdelete()).isEqualTo(UPDATED_ISDELETE);
        assertThat(testProjects.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testProjects.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testProjects.getColorcode()).isEqualTo(UPDATED_COLORCODE);
        assertThat(testProjects.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjects.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjects.getDeliverymanagerid()).isEqualTo(UPDATED_DELIVERYMANAGERID);
        assertThat(testProjects.getArchitectid()).isEqualTo(UPDATED_ARCHITECTID);
        assertThat(testProjects.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testProjects.getApprovedresources()).isEqualTo(UPDATED_APPROVEDRESOURCES);
        assertThat(testProjects.getReleaseat()).isEqualTo(UPDATED_RELEASEAT);
    }

    @Test
    @Transactional
    void patchNonExistingProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projects.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();
        projects.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projects))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeDelete = projectsRepository.findAll().size();

        // Delete the projects
        restProjectsMockMvc
            .perform(delete(ENTITY_API_URL_ID, projects.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
