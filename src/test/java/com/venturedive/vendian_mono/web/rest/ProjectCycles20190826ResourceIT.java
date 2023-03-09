package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ProjectCycles20190826;
import com.venturedive.vendian_mono.repository.ProjectCycles20190826Repository;
import com.venturedive.vendian_mono.service.criteria.ProjectCycles20190826Criteria;
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
 * Integration tests for the {@link ProjectCycles20190826Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectCycles20190826ResourceIT {

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PERFORMANCECYCLEID = 1;
    private static final Integer UPDATED_PERFORMANCECYCLEID = 2;
    private static final Integer SMALLER_PERFORMANCECYCLEID = 1 - 1;

    private static final Integer DEFAULT_PROJECTID = 1;
    private static final Integer UPDATED_PROJECTID = 2;
    private static final Integer SMALLER_PROJECTID = 1 - 1;

    private static final Integer DEFAULT_ALLOWEDAFTERDUEDATEBY = 1;
    private static final Integer UPDATED_ALLOWEDAFTERDUEDATEBY = 2;
    private static final Integer SMALLER_ALLOWEDAFTERDUEDATEBY = 1 - 1;

    private static final Instant DEFAULT_ALLOWEDAFTERDUEDATEAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ALLOWEDAFTERDUEDATEAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DUEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/project-cycles-20190826-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectCycles20190826Repository projectCycles20190826Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectCycles20190826MockMvc;

    private ProjectCycles20190826 projectCycles20190826;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectCycles20190826 createEntity(EntityManager em) {
        ProjectCycles20190826 projectCycles20190826 = new ProjectCycles20190826()
            .isactive(DEFAULT_ISACTIVE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .performancecycleid(DEFAULT_PERFORMANCECYCLEID)
            .projectid(DEFAULT_PROJECTID)
            .allowedafterduedateby(DEFAULT_ALLOWEDAFTERDUEDATEBY)
            .allowedafterduedateat(DEFAULT_ALLOWEDAFTERDUEDATEAT)
            .duedate(DEFAULT_DUEDATE);
        return projectCycles20190826;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectCycles20190826 createUpdatedEntity(EntityManager em) {
        ProjectCycles20190826 projectCycles20190826 = new ProjectCycles20190826()
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .performancecycleid(UPDATED_PERFORMANCECYCLEID)
            .projectid(UPDATED_PROJECTID)
            .allowedafterduedateby(UPDATED_ALLOWEDAFTERDUEDATEBY)
            .allowedafterduedateat(UPDATED_ALLOWEDAFTERDUEDATEAT)
            .duedate(UPDATED_DUEDATE);
        return projectCycles20190826;
    }

    @BeforeEach
    public void initTest() {
        projectCycles20190826 = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectCycles20190826() throws Exception {
        int databaseSizeBeforeCreate = projectCycles20190826Repository.findAll().size();
        // Create the ProjectCycles20190826
        restProjectCycles20190826MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles20190826))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeCreate + 1);
        ProjectCycles20190826 testProjectCycles20190826 = projectCycles20190826List.get(projectCycles20190826List.size() - 1);
        assertThat(testProjectCycles20190826.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testProjectCycles20190826.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testProjectCycles20190826.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testProjectCycles20190826.getPerformancecycleid()).isEqualTo(DEFAULT_PERFORMANCECYCLEID);
        assertThat(testProjectCycles20190826.getProjectid()).isEqualTo(DEFAULT_PROJECTID);
        assertThat(testProjectCycles20190826.getAllowedafterduedateby()).isEqualTo(DEFAULT_ALLOWEDAFTERDUEDATEBY);
        assertThat(testProjectCycles20190826.getAllowedafterduedateat()).isEqualTo(DEFAULT_ALLOWEDAFTERDUEDATEAT);
        assertThat(testProjectCycles20190826.getDuedate()).isEqualTo(DEFAULT_DUEDATE);
    }

    @Test
    @Transactional
    void createProjectCycles20190826WithExistingId() throws Exception {
        // Create the ProjectCycles20190826 with an existing ID
        projectCycles20190826.setId(1L);

        int databaseSizeBeforeCreate = projectCycles20190826Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectCycles20190826MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826s() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List
        restProjectCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectCycles20190826.getId().intValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].performancecycleid").value(hasItem(DEFAULT_PERFORMANCECYCLEID)))
            .andExpect(jsonPath("$.[*].projectid").value(hasItem(DEFAULT_PROJECTID)))
            .andExpect(jsonPath("$.[*].allowedafterduedateby").value(hasItem(DEFAULT_ALLOWEDAFTERDUEDATEBY)))
            .andExpect(jsonPath("$.[*].allowedafterduedateat").value(hasItem(DEFAULT_ALLOWEDAFTERDUEDATEAT.toString())))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())));
    }

    @Test
    @Transactional
    void getProjectCycles20190826() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get the projectCycles20190826
        restProjectCycles20190826MockMvc
            .perform(get(ENTITY_API_URL_ID, projectCycles20190826.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectCycles20190826.getId().intValue()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.performancecycleid").value(DEFAULT_PERFORMANCECYCLEID))
            .andExpect(jsonPath("$.projectid").value(DEFAULT_PROJECTID))
            .andExpect(jsonPath("$.allowedafterduedateby").value(DEFAULT_ALLOWEDAFTERDUEDATEBY))
            .andExpect(jsonPath("$.allowedafterduedateat").value(DEFAULT_ALLOWEDAFTERDUEDATEAT.toString()))
            .andExpect(jsonPath("$.duedate").value(DEFAULT_DUEDATE.toString()));
    }

    @Test
    @Transactional
    void getProjectCycles20190826sByIdFiltering() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        Long id = projectCycles20190826.getId();

        defaultProjectCycles20190826ShouldBeFound("id.equals=" + id);
        defaultProjectCycles20190826ShouldNotBeFound("id.notEquals=" + id);

        defaultProjectCycles20190826ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectCycles20190826ShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectCycles20190826ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectCycles20190826ShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where isactive equals to DEFAULT_ISACTIVE
        defaultProjectCycles20190826ShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the projectCycles20190826List where isactive equals to UPDATED_ISACTIVE
        defaultProjectCycles20190826ShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultProjectCycles20190826ShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the projectCycles20190826List where isactive equals to UPDATED_ISACTIVE
        defaultProjectCycles20190826ShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where isactive is not null
        defaultProjectCycles20190826ShouldBeFound("isactive.specified=true");

        // Get all the projectCycles20190826List where isactive is null
        defaultProjectCycles20190826ShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where createdat equals to DEFAULT_CREATEDAT
        defaultProjectCycles20190826ShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the projectCycles20190826List where createdat equals to UPDATED_CREATEDAT
        defaultProjectCycles20190826ShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultProjectCycles20190826ShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the projectCycles20190826List where createdat equals to UPDATED_CREATEDAT
        defaultProjectCycles20190826ShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where createdat is not null
        defaultProjectCycles20190826ShouldBeFound("createdat.specified=true");

        // Get all the projectCycles20190826List where createdat is null
        defaultProjectCycles20190826ShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where updatedat equals to DEFAULT_UPDATEDAT
        defaultProjectCycles20190826ShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the projectCycles20190826List where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectCycles20190826ShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultProjectCycles20190826ShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the projectCycles20190826List where updatedat equals to UPDATED_UPDATEDAT
        defaultProjectCycles20190826ShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where updatedat is not null
        defaultProjectCycles20190826ShouldBeFound("updatedat.specified=true");

        // Get all the projectCycles20190826List where updatedat is null
        defaultProjectCycles20190826ShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByPerformancecycleidIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where performancecycleid equals to DEFAULT_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldBeFound("performancecycleid.equals=" + DEFAULT_PERFORMANCECYCLEID);

        // Get all the projectCycles20190826List where performancecycleid equals to UPDATED_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldNotBeFound("performancecycleid.equals=" + UPDATED_PERFORMANCECYCLEID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByPerformancecycleidIsInShouldWork() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where performancecycleid in DEFAULT_PERFORMANCECYCLEID or UPDATED_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldBeFound("performancecycleid.in=" + DEFAULT_PERFORMANCECYCLEID + "," + UPDATED_PERFORMANCECYCLEID);

        // Get all the projectCycles20190826List where performancecycleid equals to UPDATED_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldNotBeFound("performancecycleid.in=" + UPDATED_PERFORMANCECYCLEID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByPerformancecycleidIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where performancecycleid is not null
        defaultProjectCycles20190826ShouldBeFound("performancecycleid.specified=true");

        // Get all the projectCycles20190826List where performancecycleid is null
        defaultProjectCycles20190826ShouldNotBeFound("performancecycleid.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByPerformancecycleidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where performancecycleid is greater than or equal to DEFAULT_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldBeFound("performancecycleid.greaterThanOrEqual=" + DEFAULT_PERFORMANCECYCLEID);

        // Get all the projectCycles20190826List where performancecycleid is greater than or equal to UPDATED_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldNotBeFound("performancecycleid.greaterThanOrEqual=" + UPDATED_PERFORMANCECYCLEID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByPerformancecycleidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where performancecycleid is less than or equal to DEFAULT_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldBeFound("performancecycleid.lessThanOrEqual=" + DEFAULT_PERFORMANCECYCLEID);

        // Get all the projectCycles20190826List where performancecycleid is less than or equal to SMALLER_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldNotBeFound("performancecycleid.lessThanOrEqual=" + SMALLER_PERFORMANCECYCLEID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByPerformancecycleidIsLessThanSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where performancecycleid is less than DEFAULT_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldNotBeFound("performancecycleid.lessThan=" + DEFAULT_PERFORMANCECYCLEID);

        // Get all the projectCycles20190826List where performancecycleid is less than UPDATED_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldBeFound("performancecycleid.lessThan=" + UPDATED_PERFORMANCECYCLEID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByPerformancecycleidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where performancecycleid is greater than DEFAULT_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldNotBeFound("performancecycleid.greaterThan=" + DEFAULT_PERFORMANCECYCLEID);

        // Get all the projectCycles20190826List where performancecycleid is greater than SMALLER_PERFORMANCECYCLEID
        defaultProjectCycles20190826ShouldBeFound("performancecycleid.greaterThan=" + SMALLER_PERFORMANCECYCLEID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByProjectidIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where projectid equals to DEFAULT_PROJECTID
        defaultProjectCycles20190826ShouldBeFound("projectid.equals=" + DEFAULT_PROJECTID);

        // Get all the projectCycles20190826List where projectid equals to UPDATED_PROJECTID
        defaultProjectCycles20190826ShouldNotBeFound("projectid.equals=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByProjectidIsInShouldWork() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where projectid in DEFAULT_PROJECTID or UPDATED_PROJECTID
        defaultProjectCycles20190826ShouldBeFound("projectid.in=" + DEFAULT_PROJECTID + "," + UPDATED_PROJECTID);

        // Get all the projectCycles20190826List where projectid equals to UPDATED_PROJECTID
        defaultProjectCycles20190826ShouldNotBeFound("projectid.in=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByProjectidIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where projectid is not null
        defaultProjectCycles20190826ShouldBeFound("projectid.specified=true");

        // Get all the projectCycles20190826List where projectid is null
        defaultProjectCycles20190826ShouldNotBeFound("projectid.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByProjectidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where projectid is greater than or equal to DEFAULT_PROJECTID
        defaultProjectCycles20190826ShouldBeFound("projectid.greaterThanOrEqual=" + DEFAULT_PROJECTID);

        // Get all the projectCycles20190826List where projectid is greater than or equal to UPDATED_PROJECTID
        defaultProjectCycles20190826ShouldNotBeFound("projectid.greaterThanOrEqual=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByProjectidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where projectid is less than or equal to DEFAULT_PROJECTID
        defaultProjectCycles20190826ShouldBeFound("projectid.lessThanOrEqual=" + DEFAULT_PROJECTID);

        // Get all the projectCycles20190826List where projectid is less than or equal to SMALLER_PROJECTID
        defaultProjectCycles20190826ShouldNotBeFound("projectid.lessThanOrEqual=" + SMALLER_PROJECTID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByProjectidIsLessThanSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where projectid is less than DEFAULT_PROJECTID
        defaultProjectCycles20190826ShouldNotBeFound("projectid.lessThan=" + DEFAULT_PROJECTID);

        // Get all the projectCycles20190826List where projectid is less than UPDATED_PROJECTID
        defaultProjectCycles20190826ShouldBeFound("projectid.lessThan=" + UPDATED_PROJECTID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByProjectidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where projectid is greater than DEFAULT_PROJECTID
        defaultProjectCycles20190826ShouldNotBeFound("projectid.greaterThan=" + DEFAULT_PROJECTID);

        // Get all the projectCycles20190826List where projectid is greater than SMALLER_PROJECTID
        defaultProjectCycles20190826ShouldBeFound("projectid.greaterThan=" + SMALLER_PROJECTID);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedatebyIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateby equals to DEFAULT_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldBeFound("allowedafterduedateby.equals=" + DEFAULT_ALLOWEDAFTERDUEDATEBY);

        // Get all the projectCycles20190826List where allowedafterduedateby equals to UPDATED_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateby.equals=" + UPDATED_ALLOWEDAFTERDUEDATEBY);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedatebyIsInShouldWork() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateby in DEFAULT_ALLOWEDAFTERDUEDATEBY or UPDATED_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldBeFound(
            "allowedafterduedateby.in=" + DEFAULT_ALLOWEDAFTERDUEDATEBY + "," + UPDATED_ALLOWEDAFTERDUEDATEBY
        );

        // Get all the projectCycles20190826List where allowedafterduedateby equals to UPDATED_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateby.in=" + UPDATED_ALLOWEDAFTERDUEDATEBY);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedatebyIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateby is not null
        defaultProjectCycles20190826ShouldBeFound("allowedafterduedateby.specified=true");

        // Get all the projectCycles20190826List where allowedafterduedateby is null
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateby.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedatebyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateby is greater than or equal to DEFAULT_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldBeFound("allowedafterduedateby.greaterThanOrEqual=" + DEFAULT_ALLOWEDAFTERDUEDATEBY);

        // Get all the projectCycles20190826List where allowedafterduedateby is greater than or equal to UPDATED_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateby.greaterThanOrEqual=" + UPDATED_ALLOWEDAFTERDUEDATEBY);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedatebyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateby is less than or equal to DEFAULT_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldBeFound("allowedafterduedateby.lessThanOrEqual=" + DEFAULT_ALLOWEDAFTERDUEDATEBY);

        // Get all the projectCycles20190826List where allowedafterduedateby is less than or equal to SMALLER_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateby.lessThanOrEqual=" + SMALLER_ALLOWEDAFTERDUEDATEBY);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedatebyIsLessThanSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateby is less than DEFAULT_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateby.lessThan=" + DEFAULT_ALLOWEDAFTERDUEDATEBY);

        // Get all the projectCycles20190826List where allowedafterduedateby is less than UPDATED_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldBeFound("allowedafterduedateby.lessThan=" + UPDATED_ALLOWEDAFTERDUEDATEBY);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedatebyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateby is greater than DEFAULT_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateby.greaterThan=" + DEFAULT_ALLOWEDAFTERDUEDATEBY);

        // Get all the projectCycles20190826List where allowedafterduedateby is greater than SMALLER_ALLOWEDAFTERDUEDATEBY
        defaultProjectCycles20190826ShouldBeFound("allowedafterduedateby.greaterThan=" + SMALLER_ALLOWEDAFTERDUEDATEBY);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedateatIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateat equals to DEFAULT_ALLOWEDAFTERDUEDATEAT
        defaultProjectCycles20190826ShouldBeFound("allowedafterduedateat.equals=" + DEFAULT_ALLOWEDAFTERDUEDATEAT);

        // Get all the projectCycles20190826List where allowedafterduedateat equals to UPDATED_ALLOWEDAFTERDUEDATEAT
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateat.equals=" + UPDATED_ALLOWEDAFTERDUEDATEAT);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedateatIsInShouldWork() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateat in DEFAULT_ALLOWEDAFTERDUEDATEAT or UPDATED_ALLOWEDAFTERDUEDATEAT
        defaultProjectCycles20190826ShouldBeFound(
            "allowedafterduedateat.in=" + DEFAULT_ALLOWEDAFTERDUEDATEAT + "," + UPDATED_ALLOWEDAFTERDUEDATEAT
        );

        // Get all the projectCycles20190826List where allowedafterduedateat equals to UPDATED_ALLOWEDAFTERDUEDATEAT
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateat.in=" + UPDATED_ALLOWEDAFTERDUEDATEAT);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByAllowedafterduedateatIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where allowedafterduedateat is not null
        defaultProjectCycles20190826ShouldBeFound("allowedafterduedateat.specified=true");

        // Get all the projectCycles20190826List where allowedafterduedateat is null
        defaultProjectCycles20190826ShouldNotBeFound("allowedafterduedateat.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByDuedateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where duedate equals to DEFAULT_DUEDATE
        defaultProjectCycles20190826ShouldBeFound("duedate.equals=" + DEFAULT_DUEDATE);

        // Get all the projectCycles20190826List where duedate equals to UPDATED_DUEDATE
        defaultProjectCycles20190826ShouldNotBeFound("duedate.equals=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByDuedateIsInShouldWork() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where duedate in DEFAULT_DUEDATE or UPDATED_DUEDATE
        defaultProjectCycles20190826ShouldBeFound("duedate.in=" + DEFAULT_DUEDATE + "," + UPDATED_DUEDATE);

        // Get all the projectCycles20190826List where duedate equals to UPDATED_DUEDATE
        defaultProjectCycles20190826ShouldNotBeFound("duedate.in=" + UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void getAllProjectCycles20190826sByDuedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        // Get all the projectCycles20190826List where duedate is not null
        defaultProjectCycles20190826ShouldBeFound("duedate.specified=true");

        // Get all the projectCycles20190826List where duedate is null
        defaultProjectCycles20190826ShouldNotBeFound("duedate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectCycles20190826ShouldBeFound(String filter) throws Exception {
        restProjectCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectCycles20190826.getId().intValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].performancecycleid").value(hasItem(DEFAULT_PERFORMANCECYCLEID)))
            .andExpect(jsonPath("$.[*].projectid").value(hasItem(DEFAULT_PROJECTID)))
            .andExpect(jsonPath("$.[*].allowedafterduedateby").value(hasItem(DEFAULT_ALLOWEDAFTERDUEDATEBY)))
            .andExpect(jsonPath("$.[*].allowedafterduedateat").value(hasItem(DEFAULT_ALLOWEDAFTERDUEDATEAT.toString())))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())));

        // Check, that the count call also returns 1
        restProjectCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectCycles20190826ShouldNotBeFound(String filter) throws Exception {
        restProjectCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectCycles20190826MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProjectCycles20190826() throws Exception {
        // Get the projectCycles20190826
        restProjectCycles20190826MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectCycles20190826() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();

        // Update the projectCycles20190826
        ProjectCycles20190826 updatedProjectCycles20190826 = projectCycles20190826Repository.findById(projectCycles20190826.getId()).get();
        // Disconnect from session so that the updates on updatedProjectCycles20190826 are not directly saved in db
        em.detach(updatedProjectCycles20190826);
        updatedProjectCycles20190826
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .performancecycleid(UPDATED_PERFORMANCECYCLEID)
            .projectid(UPDATED_PROJECTID)
            .allowedafterduedateby(UPDATED_ALLOWEDAFTERDUEDATEBY)
            .allowedafterduedateat(UPDATED_ALLOWEDAFTERDUEDATEAT)
            .duedate(UPDATED_DUEDATE);

        restProjectCycles20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectCycles20190826.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjectCycles20190826))
            )
            .andExpect(status().isOk());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
        ProjectCycles20190826 testProjectCycles20190826 = projectCycles20190826List.get(projectCycles20190826List.size() - 1);
        assertThat(testProjectCycles20190826.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testProjectCycles20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectCycles20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectCycles20190826.getPerformancecycleid()).isEqualTo(UPDATED_PERFORMANCECYCLEID);
        assertThat(testProjectCycles20190826.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testProjectCycles20190826.getAllowedafterduedateby()).isEqualTo(UPDATED_ALLOWEDAFTERDUEDATEBY);
        assertThat(testProjectCycles20190826.getAllowedafterduedateat()).isEqualTo(UPDATED_ALLOWEDAFTERDUEDATEAT);
        assertThat(testProjectCycles20190826.getDuedate()).isEqualTo(UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void putNonExistingProjectCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();
        projectCycles20190826.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectCycles20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectCycles20190826.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();
        projectCycles20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCycles20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();
        projectCycles20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCycles20190826MockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles20190826))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectCycles20190826WithPatch() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();

        // Update the projectCycles20190826 using partial update
        ProjectCycles20190826 partialUpdatedProjectCycles20190826 = new ProjectCycles20190826();
        partialUpdatedProjectCycles20190826.setId(projectCycles20190826.getId());

        partialUpdatedProjectCycles20190826
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .performancecycleid(UPDATED_PERFORMANCECYCLEID)
            .projectid(UPDATED_PROJECTID)
            .allowedafterduedateby(UPDATED_ALLOWEDAFTERDUEDATEBY);

        restProjectCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectCycles20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectCycles20190826))
            )
            .andExpect(status().isOk());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
        ProjectCycles20190826 testProjectCycles20190826 = projectCycles20190826List.get(projectCycles20190826List.size() - 1);
        assertThat(testProjectCycles20190826.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testProjectCycles20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectCycles20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectCycles20190826.getPerformancecycleid()).isEqualTo(UPDATED_PERFORMANCECYCLEID);
        assertThat(testProjectCycles20190826.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testProjectCycles20190826.getAllowedafterduedateby()).isEqualTo(UPDATED_ALLOWEDAFTERDUEDATEBY);
        assertThat(testProjectCycles20190826.getAllowedafterduedateat()).isEqualTo(DEFAULT_ALLOWEDAFTERDUEDATEAT);
        assertThat(testProjectCycles20190826.getDuedate()).isEqualTo(DEFAULT_DUEDATE);
    }

    @Test
    @Transactional
    void fullUpdateProjectCycles20190826WithPatch() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();

        // Update the projectCycles20190826 using partial update
        ProjectCycles20190826 partialUpdatedProjectCycles20190826 = new ProjectCycles20190826();
        partialUpdatedProjectCycles20190826.setId(projectCycles20190826.getId());

        partialUpdatedProjectCycles20190826
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .performancecycleid(UPDATED_PERFORMANCECYCLEID)
            .projectid(UPDATED_PROJECTID)
            .allowedafterduedateby(UPDATED_ALLOWEDAFTERDUEDATEBY)
            .allowedafterduedateat(UPDATED_ALLOWEDAFTERDUEDATEAT)
            .duedate(UPDATED_DUEDATE);

        restProjectCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectCycles20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectCycles20190826))
            )
            .andExpect(status().isOk());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
        ProjectCycles20190826 testProjectCycles20190826 = projectCycles20190826List.get(projectCycles20190826List.size() - 1);
        assertThat(testProjectCycles20190826.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testProjectCycles20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testProjectCycles20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testProjectCycles20190826.getPerformancecycleid()).isEqualTo(UPDATED_PERFORMANCECYCLEID);
        assertThat(testProjectCycles20190826.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testProjectCycles20190826.getAllowedafterduedateby()).isEqualTo(UPDATED_ALLOWEDAFTERDUEDATEBY);
        assertThat(testProjectCycles20190826.getAllowedafterduedateat()).isEqualTo(UPDATED_ALLOWEDAFTERDUEDATEAT);
        assertThat(testProjectCycles20190826.getDuedate()).isEqualTo(UPDATED_DUEDATE);
    }

    @Test
    @Transactional
    void patchNonExistingProjectCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();
        projectCycles20190826.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectCycles20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();
        projectCycles20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectCycles20190826() throws Exception {
        int databaseSizeBeforeUpdate = projectCycles20190826Repository.findAll().size();
        projectCycles20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectCycles20190826MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectCycles20190826))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectCycles20190826 in the database
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectCycles20190826() throws Exception {
        // Initialize the database
        projectCycles20190826Repository.saveAndFlush(projectCycles20190826);

        int databaseSizeBeforeDelete = projectCycles20190826Repository.findAll().size();

        // Delete the projectCycles20190826
        restProjectCycles20190826MockMvc
            .perform(delete(ENTITY_API_URL_ID, projectCycles20190826.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectCycles20190826> projectCycles20190826List = projectCycles20190826Repository.findAll();
        assertThat(projectCycles20190826List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
