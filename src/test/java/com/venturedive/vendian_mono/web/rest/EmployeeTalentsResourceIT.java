package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeTalents;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeTalentsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeTalentsCriteria;
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
 * Integration tests for the {@link EmployeeTalentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeTalentsResourceIT {

    private static final Boolean DEFAULT_CRITICALPOSITION = false;
    private static final Boolean UPDATED_CRITICALPOSITION = true;

    private static final Boolean DEFAULT_HIGHPOTENTIAL = false;
    private static final Boolean UPDATED_HIGHPOTENTIAL = true;

    private static final String DEFAULT_SUCCESSORFOR = "AAAAAAAAAA";
    private static final String UPDATED_SUCCESSORFOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CRITICALEXPERIENCE = false;
    private static final Boolean UPDATED_CRITICALEXPERIENCE = true;

    private static final String DEFAULT_PROMOTIONREADINESS = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTIONREADINESS = "BBBBBBBBBB";

    private static final String DEFAULT_LEADERSHIPQUALITIES = "AAAAAAAAAA";
    private static final String UPDATED_LEADERSHIPQUALITIES = "BBBBBBBBBB";

    private static final String DEFAULT_CAREERAMBITIONS = "AAAAAAAAAA";
    private static final String UPDATED_CAREERAMBITIONS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-talents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeTalentsRepository employeeTalentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeTalentsMockMvc;

    private EmployeeTalents employeeTalents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeTalents createEntity(EntityManager em) {
        EmployeeTalents employeeTalents = new EmployeeTalents()
            .criticalposition(DEFAULT_CRITICALPOSITION)
            .highpotential(DEFAULT_HIGHPOTENTIAL)
            .successorfor(DEFAULT_SUCCESSORFOR)
            .criticalexperience(DEFAULT_CRITICALEXPERIENCE)
            .promotionreadiness(DEFAULT_PROMOTIONREADINESS)
            .leadershipqualities(DEFAULT_LEADERSHIPQUALITIES)
            .careerambitions(DEFAULT_CAREERAMBITIONS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeTalents.setEmployee(employees);
        return employeeTalents;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeTalents createUpdatedEntity(EntityManager em) {
        EmployeeTalents employeeTalents = new EmployeeTalents()
            .criticalposition(UPDATED_CRITICALPOSITION)
            .highpotential(UPDATED_HIGHPOTENTIAL)
            .successorfor(UPDATED_SUCCESSORFOR)
            .criticalexperience(UPDATED_CRITICALEXPERIENCE)
            .promotionreadiness(UPDATED_PROMOTIONREADINESS)
            .leadershipqualities(UPDATED_LEADERSHIPQUALITIES)
            .careerambitions(UPDATED_CAREERAMBITIONS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeTalents.setEmployee(employees);
        return employeeTalents;
    }

    @BeforeEach
    public void initTest() {
        employeeTalents = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeTalents() throws Exception {
        int databaseSizeBeforeCreate = employeeTalentsRepository.findAll().size();
        // Create the EmployeeTalents
        restEmployeeTalentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeTalents))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeTalents testEmployeeTalents = employeeTalentsList.get(employeeTalentsList.size() - 1);
        assertThat(testEmployeeTalents.getCriticalposition()).isEqualTo(DEFAULT_CRITICALPOSITION);
        assertThat(testEmployeeTalents.getHighpotential()).isEqualTo(DEFAULT_HIGHPOTENTIAL);
        assertThat(testEmployeeTalents.getSuccessorfor()).isEqualTo(DEFAULT_SUCCESSORFOR);
        assertThat(testEmployeeTalents.getCriticalexperience()).isEqualTo(DEFAULT_CRITICALEXPERIENCE);
        assertThat(testEmployeeTalents.getPromotionreadiness()).isEqualTo(DEFAULT_PROMOTIONREADINESS);
        assertThat(testEmployeeTalents.getLeadershipqualities()).isEqualTo(DEFAULT_LEADERSHIPQUALITIES);
        assertThat(testEmployeeTalents.getCareerambitions()).isEqualTo(DEFAULT_CAREERAMBITIONS);
        assertThat(testEmployeeTalents.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeTalents.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeTalentsWithExistingId() throws Exception {
        // Create the EmployeeTalents with an existing ID
        employeeTalents.setId(1L);

        int databaseSizeBeforeCreate = employeeTalentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeTalentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeTalents))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeTalents() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList
        restEmployeeTalentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeTalents.getId().intValue())))
            .andExpect(jsonPath("$.[*].criticalposition").value(hasItem(DEFAULT_CRITICALPOSITION.booleanValue())))
            .andExpect(jsonPath("$.[*].highpotential").value(hasItem(DEFAULT_HIGHPOTENTIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].successorfor").value(hasItem(DEFAULT_SUCCESSORFOR)))
            .andExpect(jsonPath("$.[*].criticalexperience").value(hasItem(DEFAULT_CRITICALEXPERIENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].promotionreadiness").value(hasItem(DEFAULT_PROMOTIONREADINESS)))
            .andExpect(jsonPath("$.[*].leadershipqualities").value(hasItem(DEFAULT_LEADERSHIPQUALITIES)))
            .andExpect(jsonPath("$.[*].careerambitions").value(hasItem(DEFAULT_CAREERAMBITIONS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeTalents() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get the employeeTalents
        restEmployeeTalentsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeTalents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeTalents.getId().intValue()))
            .andExpect(jsonPath("$.criticalposition").value(DEFAULT_CRITICALPOSITION.booleanValue()))
            .andExpect(jsonPath("$.highpotential").value(DEFAULT_HIGHPOTENTIAL.booleanValue()))
            .andExpect(jsonPath("$.successorfor").value(DEFAULT_SUCCESSORFOR))
            .andExpect(jsonPath("$.criticalexperience").value(DEFAULT_CRITICALEXPERIENCE.booleanValue()))
            .andExpect(jsonPath("$.promotionreadiness").value(DEFAULT_PROMOTIONREADINESS))
            .andExpect(jsonPath("$.leadershipqualities").value(DEFAULT_LEADERSHIPQUALITIES))
            .andExpect(jsonPath("$.careerambitions").value(DEFAULT_CAREERAMBITIONS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeTalentsByIdFiltering() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        Long id = employeeTalents.getId();

        defaultEmployeeTalentsShouldBeFound("id.equals=" + id);
        defaultEmployeeTalentsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeTalentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeTalentsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeTalentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeTalentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCriticalpositionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where criticalposition equals to DEFAULT_CRITICALPOSITION
        defaultEmployeeTalentsShouldBeFound("criticalposition.equals=" + DEFAULT_CRITICALPOSITION);

        // Get all the employeeTalentsList where criticalposition equals to UPDATED_CRITICALPOSITION
        defaultEmployeeTalentsShouldNotBeFound("criticalposition.equals=" + UPDATED_CRITICALPOSITION);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCriticalpositionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where criticalposition in DEFAULT_CRITICALPOSITION or UPDATED_CRITICALPOSITION
        defaultEmployeeTalentsShouldBeFound("criticalposition.in=" + DEFAULT_CRITICALPOSITION + "," + UPDATED_CRITICALPOSITION);

        // Get all the employeeTalentsList where criticalposition equals to UPDATED_CRITICALPOSITION
        defaultEmployeeTalentsShouldNotBeFound("criticalposition.in=" + UPDATED_CRITICALPOSITION);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCriticalpositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where criticalposition is not null
        defaultEmployeeTalentsShouldBeFound("criticalposition.specified=true");

        // Get all the employeeTalentsList where criticalposition is null
        defaultEmployeeTalentsShouldNotBeFound("criticalposition.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByHighpotentialIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where highpotential equals to DEFAULT_HIGHPOTENTIAL
        defaultEmployeeTalentsShouldBeFound("highpotential.equals=" + DEFAULT_HIGHPOTENTIAL);

        // Get all the employeeTalentsList where highpotential equals to UPDATED_HIGHPOTENTIAL
        defaultEmployeeTalentsShouldNotBeFound("highpotential.equals=" + UPDATED_HIGHPOTENTIAL);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByHighpotentialIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where highpotential in DEFAULT_HIGHPOTENTIAL or UPDATED_HIGHPOTENTIAL
        defaultEmployeeTalentsShouldBeFound("highpotential.in=" + DEFAULT_HIGHPOTENTIAL + "," + UPDATED_HIGHPOTENTIAL);

        // Get all the employeeTalentsList where highpotential equals to UPDATED_HIGHPOTENTIAL
        defaultEmployeeTalentsShouldNotBeFound("highpotential.in=" + UPDATED_HIGHPOTENTIAL);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByHighpotentialIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where highpotential is not null
        defaultEmployeeTalentsShouldBeFound("highpotential.specified=true");

        // Get all the employeeTalentsList where highpotential is null
        defaultEmployeeTalentsShouldNotBeFound("highpotential.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsBySuccessorforIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where successorfor equals to DEFAULT_SUCCESSORFOR
        defaultEmployeeTalentsShouldBeFound("successorfor.equals=" + DEFAULT_SUCCESSORFOR);

        // Get all the employeeTalentsList where successorfor equals to UPDATED_SUCCESSORFOR
        defaultEmployeeTalentsShouldNotBeFound("successorfor.equals=" + UPDATED_SUCCESSORFOR);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsBySuccessorforIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where successorfor in DEFAULT_SUCCESSORFOR or UPDATED_SUCCESSORFOR
        defaultEmployeeTalentsShouldBeFound("successorfor.in=" + DEFAULT_SUCCESSORFOR + "," + UPDATED_SUCCESSORFOR);

        // Get all the employeeTalentsList where successorfor equals to UPDATED_SUCCESSORFOR
        defaultEmployeeTalentsShouldNotBeFound("successorfor.in=" + UPDATED_SUCCESSORFOR);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsBySuccessorforIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where successorfor is not null
        defaultEmployeeTalentsShouldBeFound("successorfor.specified=true");

        // Get all the employeeTalentsList where successorfor is null
        defaultEmployeeTalentsShouldNotBeFound("successorfor.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsBySuccessorforContainsSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where successorfor contains DEFAULT_SUCCESSORFOR
        defaultEmployeeTalentsShouldBeFound("successorfor.contains=" + DEFAULT_SUCCESSORFOR);

        // Get all the employeeTalentsList where successorfor contains UPDATED_SUCCESSORFOR
        defaultEmployeeTalentsShouldNotBeFound("successorfor.contains=" + UPDATED_SUCCESSORFOR);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsBySuccessorforNotContainsSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where successorfor does not contain DEFAULT_SUCCESSORFOR
        defaultEmployeeTalentsShouldNotBeFound("successorfor.doesNotContain=" + DEFAULT_SUCCESSORFOR);

        // Get all the employeeTalentsList where successorfor does not contain UPDATED_SUCCESSORFOR
        defaultEmployeeTalentsShouldBeFound("successorfor.doesNotContain=" + UPDATED_SUCCESSORFOR);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCriticalexperienceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where criticalexperience equals to DEFAULT_CRITICALEXPERIENCE
        defaultEmployeeTalentsShouldBeFound("criticalexperience.equals=" + DEFAULT_CRITICALEXPERIENCE);

        // Get all the employeeTalentsList where criticalexperience equals to UPDATED_CRITICALEXPERIENCE
        defaultEmployeeTalentsShouldNotBeFound("criticalexperience.equals=" + UPDATED_CRITICALEXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCriticalexperienceIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where criticalexperience in DEFAULT_CRITICALEXPERIENCE or UPDATED_CRITICALEXPERIENCE
        defaultEmployeeTalentsShouldBeFound("criticalexperience.in=" + DEFAULT_CRITICALEXPERIENCE + "," + UPDATED_CRITICALEXPERIENCE);

        // Get all the employeeTalentsList where criticalexperience equals to UPDATED_CRITICALEXPERIENCE
        defaultEmployeeTalentsShouldNotBeFound("criticalexperience.in=" + UPDATED_CRITICALEXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCriticalexperienceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where criticalexperience is not null
        defaultEmployeeTalentsShouldBeFound("criticalexperience.specified=true");

        // Get all the employeeTalentsList where criticalexperience is null
        defaultEmployeeTalentsShouldNotBeFound("criticalexperience.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByPromotionreadinessIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where promotionreadiness equals to DEFAULT_PROMOTIONREADINESS
        defaultEmployeeTalentsShouldBeFound("promotionreadiness.equals=" + DEFAULT_PROMOTIONREADINESS);

        // Get all the employeeTalentsList where promotionreadiness equals to UPDATED_PROMOTIONREADINESS
        defaultEmployeeTalentsShouldNotBeFound("promotionreadiness.equals=" + UPDATED_PROMOTIONREADINESS);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByPromotionreadinessIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where promotionreadiness in DEFAULT_PROMOTIONREADINESS or UPDATED_PROMOTIONREADINESS
        defaultEmployeeTalentsShouldBeFound("promotionreadiness.in=" + DEFAULT_PROMOTIONREADINESS + "," + UPDATED_PROMOTIONREADINESS);

        // Get all the employeeTalentsList where promotionreadiness equals to UPDATED_PROMOTIONREADINESS
        defaultEmployeeTalentsShouldNotBeFound("promotionreadiness.in=" + UPDATED_PROMOTIONREADINESS);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByPromotionreadinessIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where promotionreadiness is not null
        defaultEmployeeTalentsShouldBeFound("promotionreadiness.specified=true");

        // Get all the employeeTalentsList where promotionreadiness is null
        defaultEmployeeTalentsShouldNotBeFound("promotionreadiness.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByPromotionreadinessContainsSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where promotionreadiness contains DEFAULT_PROMOTIONREADINESS
        defaultEmployeeTalentsShouldBeFound("promotionreadiness.contains=" + DEFAULT_PROMOTIONREADINESS);

        // Get all the employeeTalentsList where promotionreadiness contains UPDATED_PROMOTIONREADINESS
        defaultEmployeeTalentsShouldNotBeFound("promotionreadiness.contains=" + UPDATED_PROMOTIONREADINESS);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByPromotionreadinessNotContainsSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where promotionreadiness does not contain DEFAULT_PROMOTIONREADINESS
        defaultEmployeeTalentsShouldNotBeFound("promotionreadiness.doesNotContain=" + DEFAULT_PROMOTIONREADINESS);

        // Get all the employeeTalentsList where promotionreadiness does not contain UPDATED_PROMOTIONREADINESS
        defaultEmployeeTalentsShouldBeFound("promotionreadiness.doesNotContain=" + UPDATED_PROMOTIONREADINESS);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByLeadershipqualitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where leadershipqualities equals to DEFAULT_LEADERSHIPQUALITIES
        defaultEmployeeTalentsShouldBeFound("leadershipqualities.equals=" + DEFAULT_LEADERSHIPQUALITIES);

        // Get all the employeeTalentsList where leadershipqualities equals to UPDATED_LEADERSHIPQUALITIES
        defaultEmployeeTalentsShouldNotBeFound("leadershipqualities.equals=" + UPDATED_LEADERSHIPQUALITIES);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByLeadershipqualitiesIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where leadershipqualities in DEFAULT_LEADERSHIPQUALITIES or UPDATED_LEADERSHIPQUALITIES
        defaultEmployeeTalentsShouldBeFound("leadershipqualities.in=" + DEFAULT_LEADERSHIPQUALITIES + "," + UPDATED_LEADERSHIPQUALITIES);

        // Get all the employeeTalentsList where leadershipqualities equals to UPDATED_LEADERSHIPQUALITIES
        defaultEmployeeTalentsShouldNotBeFound("leadershipqualities.in=" + UPDATED_LEADERSHIPQUALITIES);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByLeadershipqualitiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where leadershipqualities is not null
        defaultEmployeeTalentsShouldBeFound("leadershipqualities.specified=true");

        // Get all the employeeTalentsList where leadershipqualities is null
        defaultEmployeeTalentsShouldNotBeFound("leadershipqualities.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByLeadershipqualitiesContainsSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where leadershipqualities contains DEFAULT_LEADERSHIPQUALITIES
        defaultEmployeeTalentsShouldBeFound("leadershipqualities.contains=" + DEFAULT_LEADERSHIPQUALITIES);

        // Get all the employeeTalentsList where leadershipqualities contains UPDATED_LEADERSHIPQUALITIES
        defaultEmployeeTalentsShouldNotBeFound("leadershipqualities.contains=" + UPDATED_LEADERSHIPQUALITIES);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByLeadershipqualitiesNotContainsSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where leadershipqualities does not contain DEFAULT_LEADERSHIPQUALITIES
        defaultEmployeeTalentsShouldNotBeFound("leadershipqualities.doesNotContain=" + DEFAULT_LEADERSHIPQUALITIES);

        // Get all the employeeTalentsList where leadershipqualities does not contain UPDATED_LEADERSHIPQUALITIES
        defaultEmployeeTalentsShouldBeFound("leadershipqualities.doesNotContain=" + UPDATED_LEADERSHIPQUALITIES);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCareerambitionsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where careerambitions equals to DEFAULT_CAREERAMBITIONS
        defaultEmployeeTalentsShouldBeFound("careerambitions.equals=" + DEFAULT_CAREERAMBITIONS);

        // Get all the employeeTalentsList where careerambitions equals to UPDATED_CAREERAMBITIONS
        defaultEmployeeTalentsShouldNotBeFound("careerambitions.equals=" + UPDATED_CAREERAMBITIONS);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCareerambitionsIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where careerambitions in DEFAULT_CAREERAMBITIONS or UPDATED_CAREERAMBITIONS
        defaultEmployeeTalentsShouldBeFound("careerambitions.in=" + DEFAULT_CAREERAMBITIONS + "," + UPDATED_CAREERAMBITIONS);

        // Get all the employeeTalentsList where careerambitions equals to UPDATED_CAREERAMBITIONS
        defaultEmployeeTalentsShouldNotBeFound("careerambitions.in=" + UPDATED_CAREERAMBITIONS);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCareerambitionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where careerambitions is not null
        defaultEmployeeTalentsShouldBeFound("careerambitions.specified=true");

        // Get all the employeeTalentsList where careerambitions is null
        defaultEmployeeTalentsShouldNotBeFound("careerambitions.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCareerambitionsContainsSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where careerambitions contains DEFAULT_CAREERAMBITIONS
        defaultEmployeeTalentsShouldBeFound("careerambitions.contains=" + DEFAULT_CAREERAMBITIONS);

        // Get all the employeeTalentsList where careerambitions contains UPDATED_CAREERAMBITIONS
        defaultEmployeeTalentsShouldNotBeFound("careerambitions.contains=" + UPDATED_CAREERAMBITIONS);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCareerambitionsNotContainsSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where careerambitions does not contain DEFAULT_CAREERAMBITIONS
        defaultEmployeeTalentsShouldNotBeFound("careerambitions.doesNotContain=" + DEFAULT_CAREERAMBITIONS);

        // Get all the employeeTalentsList where careerambitions does not contain UPDATED_CAREERAMBITIONS
        defaultEmployeeTalentsShouldBeFound("careerambitions.doesNotContain=" + UPDATED_CAREERAMBITIONS);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeTalentsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeTalentsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeTalentsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeTalentsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeTalentsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeTalentsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where createdat is not null
        defaultEmployeeTalentsShouldBeFound("createdat.specified=true");

        // Get all the employeeTalentsList where createdat is null
        defaultEmployeeTalentsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeTalentsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeTalentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeTalentsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeTalentsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeTalentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeTalentsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        // Get all the employeeTalentsList where updatedat is not null
        defaultEmployeeTalentsShouldBeFound("updatedat.specified=true");

        // Get all the employeeTalentsList where updatedat is null
        defaultEmployeeTalentsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTalentsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeTalentsRepository.saveAndFlush(employeeTalents);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeTalents.setEmployee(employee);
        employeeTalentsRepository.saveAndFlush(employeeTalents);
        Long employeeId = employee.getId();

        // Get all the employeeTalentsList where employee equals to employeeId
        defaultEmployeeTalentsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeTalentsList where employee equals to (employeeId + 1)
        defaultEmployeeTalentsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeTalentsShouldBeFound(String filter) throws Exception {
        restEmployeeTalentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeTalents.getId().intValue())))
            .andExpect(jsonPath("$.[*].criticalposition").value(hasItem(DEFAULT_CRITICALPOSITION.booleanValue())))
            .andExpect(jsonPath("$.[*].highpotential").value(hasItem(DEFAULT_HIGHPOTENTIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].successorfor").value(hasItem(DEFAULT_SUCCESSORFOR)))
            .andExpect(jsonPath("$.[*].criticalexperience").value(hasItem(DEFAULT_CRITICALEXPERIENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].promotionreadiness").value(hasItem(DEFAULT_PROMOTIONREADINESS)))
            .andExpect(jsonPath("$.[*].leadershipqualities").value(hasItem(DEFAULT_LEADERSHIPQUALITIES)))
            .andExpect(jsonPath("$.[*].careerambitions").value(hasItem(DEFAULT_CAREERAMBITIONS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeTalentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeTalentsShouldNotBeFound(String filter) throws Exception {
        restEmployeeTalentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeTalentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeTalents() throws Exception {
        // Get the employeeTalents
        restEmployeeTalentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeTalents() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();

        // Update the employeeTalents
        EmployeeTalents updatedEmployeeTalents = employeeTalentsRepository.findById(employeeTalents.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeTalents are not directly saved in db
        em.detach(updatedEmployeeTalents);
        updatedEmployeeTalents
            .criticalposition(UPDATED_CRITICALPOSITION)
            .highpotential(UPDATED_HIGHPOTENTIAL)
            .successorfor(UPDATED_SUCCESSORFOR)
            .criticalexperience(UPDATED_CRITICALEXPERIENCE)
            .promotionreadiness(UPDATED_PROMOTIONREADINESS)
            .leadershipqualities(UPDATED_LEADERSHIPQUALITIES)
            .careerambitions(UPDATED_CAREERAMBITIONS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeTalentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeTalents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeTalents))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeTalents testEmployeeTalents = employeeTalentsList.get(employeeTalentsList.size() - 1);
        assertThat(testEmployeeTalents.getCriticalposition()).isEqualTo(UPDATED_CRITICALPOSITION);
        assertThat(testEmployeeTalents.getHighpotential()).isEqualTo(UPDATED_HIGHPOTENTIAL);
        assertThat(testEmployeeTalents.getSuccessorfor()).isEqualTo(UPDATED_SUCCESSORFOR);
        assertThat(testEmployeeTalents.getCriticalexperience()).isEqualTo(UPDATED_CRITICALEXPERIENCE);
        assertThat(testEmployeeTalents.getPromotionreadiness()).isEqualTo(UPDATED_PROMOTIONREADINESS);
        assertThat(testEmployeeTalents.getLeadershipqualities()).isEqualTo(UPDATED_LEADERSHIPQUALITIES);
        assertThat(testEmployeeTalents.getCareerambitions()).isEqualTo(UPDATED_CAREERAMBITIONS);
        assertThat(testEmployeeTalents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeTalents.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeTalents() throws Exception {
        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();
        employeeTalents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeTalentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeTalents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeTalents))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeTalents() throws Exception {
        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();
        employeeTalents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeTalentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeTalents))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeTalents() throws Exception {
        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();
        employeeTalents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeTalentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeTalents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeTalentsWithPatch() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();

        // Update the employeeTalents using partial update
        EmployeeTalents partialUpdatedEmployeeTalents = new EmployeeTalents();
        partialUpdatedEmployeeTalents.setId(employeeTalents.getId());

        partialUpdatedEmployeeTalents
            .highpotential(UPDATED_HIGHPOTENTIAL)
            .criticalexperience(UPDATED_CRITICALEXPERIENCE)
            .promotionreadiness(UPDATED_PROMOTIONREADINESS)
            .careerambitions(UPDATED_CAREERAMBITIONS)
            .createdat(UPDATED_CREATEDAT);

        restEmployeeTalentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeTalents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeTalents))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeTalents testEmployeeTalents = employeeTalentsList.get(employeeTalentsList.size() - 1);
        assertThat(testEmployeeTalents.getCriticalposition()).isEqualTo(DEFAULT_CRITICALPOSITION);
        assertThat(testEmployeeTalents.getHighpotential()).isEqualTo(UPDATED_HIGHPOTENTIAL);
        assertThat(testEmployeeTalents.getSuccessorfor()).isEqualTo(DEFAULT_SUCCESSORFOR);
        assertThat(testEmployeeTalents.getCriticalexperience()).isEqualTo(UPDATED_CRITICALEXPERIENCE);
        assertThat(testEmployeeTalents.getPromotionreadiness()).isEqualTo(UPDATED_PROMOTIONREADINESS);
        assertThat(testEmployeeTalents.getLeadershipqualities()).isEqualTo(DEFAULT_LEADERSHIPQUALITIES);
        assertThat(testEmployeeTalents.getCareerambitions()).isEqualTo(UPDATED_CAREERAMBITIONS);
        assertThat(testEmployeeTalents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeTalents.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeTalentsWithPatch() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();

        // Update the employeeTalents using partial update
        EmployeeTalents partialUpdatedEmployeeTalents = new EmployeeTalents();
        partialUpdatedEmployeeTalents.setId(employeeTalents.getId());

        partialUpdatedEmployeeTalents
            .criticalposition(UPDATED_CRITICALPOSITION)
            .highpotential(UPDATED_HIGHPOTENTIAL)
            .successorfor(UPDATED_SUCCESSORFOR)
            .criticalexperience(UPDATED_CRITICALEXPERIENCE)
            .promotionreadiness(UPDATED_PROMOTIONREADINESS)
            .leadershipqualities(UPDATED_LEADERSHIPQUALITIES)
            .careerambitions(UPDATED_CAREERAMBITIONS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeTalentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeTalents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeTalents))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeTalents testEmployeeTalents = employeeTalentsList.get(employeeTalentsList.size() - 1);
        assertThat(testEmployeeTalents.getCriticalposition()).isEqualTo(UPDATED_CRITICALPOSITION);
        assertThat(testEmployeeTalents.getHighpotential()).isEqualTo(UPDATED_HIGHPOTENTIAL);
        assertThat(testEmployeeTalents.getSuccessorfor()).isEqualTo(UPDATED_SUCCESSORFOR);
        assertThat(testEmployeeTalents.getCriticalexperience()).isEqualTo(UPDATED_CRITICALEXPERIENCE);
        assertThat(testEmployeeTalents.getPromotionreadiness()).isEqualTo(UPDATED_PROMOTIONREADINESS);
        assertThat(testEmployeeTalents.getLeadershipqualities()).isEqualTo(UPDATED_LEADERSHIPQUALITIES);
        assertThat(testEmployeeTalents.getCareerambitions()).isEqualTo(UPDATED_CAREERAMBITIONS);
        assertThat(testEmployeeTalents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeTalents.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeTalents() throws Exception {
        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();
        employeeTalents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeTalentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeTalents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeTalents))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeTalents() throws Exception {
        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();
        employeeTalents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeTalentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeTalents))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeTalents() throws Exception {
        int databaseSizeBeforeUpdate = employeeTalentsRepository.findAll().size();
        employeeTalents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeTalentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeTalents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeTalents in the database
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeTalents() throws Exception {
        // Initialize the database
        employeeTalentsRepository.saveAndFlush(employeeTalents);

        int databaseSizeBeforeDelete = employeeTalentsRepository.findAll().size();

        // Delete the employeeTalents
        restEmployeeTalentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeTalents.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeTalents> employeeTalentsList = employeeTalentsRepository.findAll();
        assertThat(employeeTalentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
