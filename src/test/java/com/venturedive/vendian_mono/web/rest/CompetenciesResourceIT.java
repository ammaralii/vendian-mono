package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Competencies;
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.CompetenciesRepository;
import com.venturedive.vendian_mono.service.criteria.CompetenciesCriteria;
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
 * Integration tests for the {@link CompetenciesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetenciesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/competencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompetenciesRepository competenciesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetenciesMockMvc;

    private Competencies competencies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competencies createEntity(EntityManager em) {
        Competencies competencies = new Competencies().name(DEFAULT_NAME).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return competencies;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competencies createUpdatedEntity(EntityManager em) {
        Competencies competencies = new Competencies().name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return competencies;
    }

    @BeforeEach
    public void initTest() {
        competencies = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetencies() throws Exception {
        int databaseSizeBeforeCreate = competenciesRepository.findAll().size();
        // Create the Competencies
        restCompetenciesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isCreated());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeCreate + 1);
        Competencies testCompetencies = competenciesList.get(competenciesList.size() - 1);
        assertThat(testCompetencies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompetencies.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testCompetencies.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createCompetenciesWithExistingId() throws Exception {
        // Create the Competencies with an existing ID
        competencies.setId(1L);

        int databaseSizeBeforeCreate = competenciesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetenciesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = competenciesRepository.findAll().size();
        // set the field null
        competencies.setName(null);

        // Create the Competencies, which fails.

        restCompetenciesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isBadRequest());

        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = competenciesRepository.findAll().size();
        // set the field null
        competencies.setCreatedat(null);

        // Create the Competencies, which fails.

        restCompetenciesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isBadRequest());

        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = competenciesRepository.findAll().size();
        // set the field null
        competencies.setUpdatedat(null);

        // Create the Competencies, which fails.

        restCompetenciesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isBadRequest());

        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompetencies() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList
        restCompetenciesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competencies.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getCompetencies() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get the competencies
        restCompetenciesMockMvc
            .perform(get(ENTITY_API_URL_ID, competencies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competencies.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getCompetenciesByIdFiltering() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        Long id = competencies.getId();

        defaultCompetenciesShouldBeFound("id.equals=" + id);
        defaultCompetenciesShouldNotBeFound("id.notEquals=" + id);

        defaultCompetenciesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompetenciesShouldNotBeFound("id.greaterThan=" + id);

        defaultCompetenciesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompetenciesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where name equals to DEFAULT_NAME
        defaultCompetenciesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the competenciesList where name equals to UPDATED_NAME
        defaultCompetenciesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompetenciesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the competenciesList where name equals to UPDATED_NAME
        defaultCompetenciesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where name is not null
        defaultCompetenciesShouldBeFound("name.specified=true");

        // Get all the competenciesList where name is null
        defaultCompetenciesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameContainsSomething() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where name contains DEFAULT_NAME
        defaultCompetenciesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the competenciesList where name contains UPDATED_NAME
        defaultCompetenciesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where name does not contain DEFAULT_NAME
        defaultCompetenciesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the competenciesList where name does not contain UPDATED_NAME
        defaultCompetenciesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where createdat equals to DEFAULT_CREATEDAT
        defaultCompetenciesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the competenciesList where createdat equals to UPDATED_CREATEDAT
        defaultCompetenciesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultCompetenciesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the competenciesList where createdat equals to UPDATED_CREATEDAT
        defaultCompetenciesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where createdat is not null
        defaultCompetenciesShouldBeFound("createdat.specified=true");

        // Get all the competenciesList where createdat is null
        defaultCompetenciesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultCompetenciesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the competenciesList where updatedat equals to UPDATED_UPDATEDAT
        defaultCompetenciesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllCompetenciesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultCompetenciesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the competenciesList where updatedat equals to UPDATED_UPDATEDAT
        defaultCompetenciesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllCompetenciesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        // Get all the competenciesList where updatedat is not null
        defaultCompetenciesShouldBeFound("updatedat.specified=true");

        // Get all the competenciesList where updatedat is null
        defaultCompetenciesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByEmployeejobinfoCompetencyIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoCompetency;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            competenciesRepository.saveAndFlush(competencies);
            employeejobinfoCompetency = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoCompetency = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoCompetency);
        em.flush();
        competencies.addEmployeejobinfoCompetency(employeejobinfoCompetency);
        competenciesRepository.saveAndFlush(competencies);
        Long employeejobinfoCompetencyId = employeejobinfoCompetency.getId();

        // Get all the competenciesList where employeejobinfoCompetency equals to employeejobinfoCompetencyId
        defaultCompetenciesShouldBeFound("employeejobinfoCompetencyId.equals=" + employeejobinfoCompetencyId);

        // Get all the competenciesList where employeejobinfoCompetency equals to (employeejobinfoCompetencyId + 1)
        defaultCompetenciesShouldNotBeFound("employeejobinfoCompetencyId.equals=" + (employeejobinfoCompetencyId + 1));
    }

    @Test
    @Transactional
    void getAllCompetenciesByEmployeesCompetencyIsEqualToSomething() throws Exception {
        Employees employeesCompetency;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            competenciesRepository.saveAndFlush(competencies);
            employeesCompetency = EmployeesResourceIT.createEntity(em);
        } else {
            employeesCompetency = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesCompetency);
        em.flush();
        competencies.addEmployeesCompetency(employeesCompetency);
        competenciesRepository.saveAndFlush(competencies);
        Long employeesCompetencyId = employeesCompetency.getId();

        // Get all the competenciesList where employeesCompetency equals to employeesCompetencyId
        defaultCompetenciesShouldBeFound("employeesCompetencyId.equals=" + employeesCompetencyId);

        // Get all the competenciesList where employeesCompetency equals to (employeesCompetencyId + 1)
        defaultCompetenciesShouldNotBeFound("employeesCompetencyId.equals=" + (employeesCompetencyId + 1));
    }

    @Test
    @Transactional
    void getAllCompetenciesByTracksCompetencyIsEqualToSomething() throws Exception {
        Tracks tracksCompetency;
        if (TestUtil.findAll(em, Tracks.class).isEmpty()) {
            competenciesRepository.saveAndFlush(competencies);
            tracksCompetency = TracksResourceIT.createEntity(em);
        } else {
            tracksCompetency = TestUtil.findAll(em, Tracks.class).get(0);
        }
        em.persist(tracksCompetency);
        em.flush();
        competencies.addTracksCompetency(tracksCompetency);
        competenciesRepository.saveAndFlush(competencies);
        Long tracksCompetencyId = tracksCompetency.getId();

        // Get all the competenciesList where tracksCompetency equals to tracksCompetencyId
        defaultCompetenciesShouldBeFound("tracksCompetencyId.equals=" + tracksCompetencyId);

        // Get all the competenciesList where tracksCompetency equals to (tracksCompetencyId + 1)
        defaultCompetenciesShouldNotBeFound("tracksCompetencyId.equals=" + (tracksCompetencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompetenciesShouldBeFound(String filter) throws Exception {
        restCompetenciesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competencies.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restCompetenciesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompetenciesShouldNotBeFound(String filter) throws Exception {
        restCompetenciesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompetenciesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompetencies() throws Exception {
        // Get the competencies
        restCompetenciesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetencies() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();

        // Update the competencies
        Competencies updatedCompetencies = competenciesRepository.findById(competencies.getId()).get();
        // Disconnect from session so that the updates on updatedCompetencies are not directly saved in db
        em.detach(updatedCompetencies);
        updatedCompetencies.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restCompetenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetencies.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompetencies))
            )
            .andExpect(status().isOk());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
        Competencies testCompetencies = competenciesList.get(competenciesList.size() - 1);
        assertThat(testCompetencies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetencies.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testCompetencies.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingCompetencies() throws Exception {
        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();
        competencies.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competencies.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetencies() throws Exception {
        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();
        competencies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetencies() throws Exception {
        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();
        competencies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetenciesWithPatch() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();

        // Update the competencies using partial update
        Competencies partialUpdatedCompetencies = new Competencies();
        partialUpdatedCompetencies.setId(competencies.getId());

        partialUpdatedCompetencies.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restCompetenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetencies.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetencies))
            )
            .andExpect(status().isOk());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
        Competencies testCompetencies = competenciesList.get(competenciesList.size() - 1);
        assertThat(testCompetencies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetencies.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testCompetencies.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateCompetenciesWithPatch() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();

        // Update the competencies using partial update
        Competencies partialUpdatedCompetencies = new Competencies();
        partialUpdatedCompetencies.setId(competencies.getId());

        partialUpdatedCompetencies.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restCompetenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetencies.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetencies))
            )
            .andExpect(status().isOk());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
        Competencies testCompetencies = competenciesList.get(competenciesList.size() - 1);
        assertThat(testCompetencies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetencies.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testCompetencies.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingCompetencies() throws Exception {
        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();
        competencies.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competencies.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetencies() throws Exception {
        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();
        competencies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetencies() throws Exception {
        int databaseSizeBeforeUpdate = competenciesRepository.findAll().size();
        competencies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competencies))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competencies in the database
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetencies() throws Exception {
        // Initialize the database
        competenciesRepository.saveAndFlush(competencies);

        int databaseSizeBeforeDelete = competenciesRepository.findAll().size();

        // Delete the competencies
        restCompetenciesMockMvc
            .perform(delete(ENTITY_API_URL_ID, competencies.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competencies> competenciesList = competenciesRepository.findAll();
        assertThat(competenciesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
