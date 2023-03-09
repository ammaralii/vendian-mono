package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Departments;
import com.venturedive.vendian_mono.domain.Divisions;
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.DivisionsRepository;
import com.venturedive.vendian_mono.service.criteria.DivisionsCriteria;
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
 * Integration tests for the {@link DivisionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DivisionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/divisions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DivisionsRepository divisionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisionsMockMvc;

    private Divisions divisions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisions createEntity(EntityManager em) {
        Divisions divisions = new Divisions().name(DEFAULT_NAME).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return divisions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisions createUpdatedEntity(EntityManager em) {
        Divisions divisions = new Divisions().name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return divisions;
    }

    @BeforeEach
    public void initTest() {
        divisions = createEntity(em);
    }

    @Test
    @Transactional
    void createDivisions() throws Exception {
        int databaseSizeBeforeCreate = divisionsRepository.findAll().size();
        // Create the Divisions
        restDivisionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isCreated());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeCreate + 1);
        Divisions testDivisions = divisionsList.get(divisionsList.size() - 1);
        assertThat(testDivisions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDivisions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDivisions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createDivisionsWithExistingId() throws Exception {
        // Create the Divisions with an existing ID
        divisions.setId(1L);

        int databaseSizeBeforeCreate = divisionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionsRepository.findAll().size();
        // set the field null
        divisions.setName(null);

        // Create the Divisions, which fails.

        restDivisionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isBadRequest());

        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionsRepository.findAll().size();
        // set the field null
        divisions.setCreatedat(null);

        // Create the Divisions, which fails.

        restDivisionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isBadRequest());

        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionsRepository.findAll().size();
        // set the field null
        divisions.setUpdatedat(null);

        // Create the Divisions, which fails.

        restDivisionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isBadRequest());

        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDivisions() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList
        restDivisionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getDivisions() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get the divisions
        restDivisionsMockMvc
            .perform(get(ENTITY_API_URL_ID, divisions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(divisions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getDivisionsByIdFiltering() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        Long id = divisions.getId();

        defaultDivisionsShouldBeFound("id.equals=" + id);
        defaultDivisionsShouldNotBeFound("id.notEquals=" + id);

        defaultDivisionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDivisionsShouldNotBeFound("id.greaterThan=" + id);

        defaultDivisionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDivisionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDivisionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name equals to DEFAULT_NAME
        defaultDivisionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the divisionsList where name equals to UPDATED_NAME
        defaultDivisionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDivisionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDivisionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the divisionsList where name equals to UPDATED_NAME
        defaultDivisionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDivisionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name is not null
        defaultDivisionsShouldBeFound("name.specified=true");

        // Get all the divisionsList where name is null
        defaultDivisionsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDivisionsByNameContainsSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name contains DEFAULT_NAME
        defaultDivisionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the divisionsList where name contains UPDATED_NAME
        defaultDivisionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDivisionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name does not contain DEFAULT_NAME
        defaultDivisionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the divisionsList where name does not contain UPDATED_NAME
        defaultDivisionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDivisionsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where createdat equals to DEFAULT_CREATEDAT
        defaultDivisionsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the divisionsList where createdat equals to UPDATED_CREATEDAT
        defaultDivisionsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDivisionsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDivisionsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the divisionsList where createdat equals to UPDATED_CREATEDAT
        defaultDivisionsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDivisionsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where createdat is not null
        defaultDivisionsShouldBeFound("createdat.specified=true");

        // Get all the divisionsList where createdat is null
        defaultDivisionsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDivisionsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDivisionsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the divisionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDivisionsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDivisionsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDivisionsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the divisionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDivisionsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDivisionsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where updatedat is not null
        defaultDivisionsShouldBeFound("updatedat.specified=true");

        // Get all the divisionsList where updatedat is null
        defaultDivisionsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDivisionsByDepartmentsDivisionIsEqualToSomething() throws Exception {
        Departments departmentsDivision;
        if (TestUtil.findAll(em, Departments.class).isEmpty()) {
            divisionsRepository.saveAndFlush(divisions);
            departmentsDivision = DepartmentsResourceIT.createEntity(em);
        } else {
            departmentsDivision = TestUtil.findAll(em, Departments.class).get(0);
        }
        em.persist(departmentsDivision);
        em.flush();
        divisions.addDepartmentsDivision(departmentsDivision);
        divisionsRepository.saveAndFlush(divisions);
        Long departmentsDivisionId = departmentsDivision.getId();

        // Get all the divisionsList where departmentsDivision equals to departmentsDivisionId
        defaultDivisionsShouldBeFound("departmentsDivisionId.equals=" + departmentsDivisionId);

        // Get all the divisionsList where departmentsDivision equals to (departmentsDivisionId + 1)
        defaultDivisionsShouldNotBeFound("departmentsDivisionId.equals=" + (departmentsDivisionId + 1));
    }

    @Test
    @Transactional
    void getAllDivisionsByEmployeejobinfoDivisionIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoDivision;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            divisionsRepository.saveAndFlush(divisions);
            employeejobinfoDivision = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoDivision = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoDivision);
        em.flush();
        divisions.addEmployeejobinfoDivision(employeejobinfoDivision);
        divisionsRepository.saveAndFlush(divisions);
        Long employeejobinfoDivisionId = employeejobinfoDivision.getId();

        // Get all the divisionsList where employeejobinfoDivision equals to employeejobinfoDivisionId
        defaultDivisionsShouldBeFound("employeejobinfoDivisionId.equals=" + employeejobinfoDivisionId);

        // Get all the divisionsList where employeejobinfoDivision equals to (employeejobinfoDivisionId + 1)
        defaultDivisionsShouldNotBeFound("employeejobinfoDivisionId.equals=" + (employeejobinfoDivisionId + 1));
    }

    @Test
    @Transactional
    void getAllDivisionsByEmployeesDivisionIsEqualToSomething() throws Exception {
        Employees employeesDivision;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            divisionsRepository.saveAndFlush(divisions);
            employeesDivision = EmployeesResourceIT.createEntity(em);
        } else {
            employeesDivision = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesDivision);
        em.flush();
        divisions.addEmployeesDivision(employeesDivision);
        divisionsRepository.saveAndFlush(divisions);
        Long employeesDivisionId = employeesDivision.getId();

        // Get all the divisionsList where employeesDivision equals to employeesDivisionId
        defaultDivisionsShouldBeFound("employeesDivisionId.equals=" + employeesDivisionId);

        // Get all the divisionsList where employeesDivision equals to (employeesDivisionId + 1)
        defaultDivisionsShouldNotBeFound("employeesDivisionId.equals=" + (employeesDivisionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDivisionsShouldBeFound(String filter) throws Exception {
        restDivisionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restDivisionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDivisionsShouldNotBeFound(String filter) throws Exception {
        restDivisionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDivisionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDivisions() throws Exception {
        // Get the divisions
        restDivisionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDivisions() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();

        // Update the divisions
        Divisions updatedDivisions = divisionsRepository.findById(divisions.getId()).get();
        // Disconnect from session so that the updates on updatedDivisions are not directly saved in db
        em.detach(updatedDivisions);
        updatedDivisions.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restDivisionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDivisions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDivisions))
            )
            .andExpect(status().isOk());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
        Divisions testDivisions = divisionsList.get(divisionsList.size() - 1);
        assertThat(testDivisions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDivisions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDivisions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDivisions() throws Exception {
        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();
        divisions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDivisions() throws Exception {
        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();
        divisions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDivisions() throws Exception {
        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();
        divisions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDivisionsWithPatch() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();

        // Update the divisions using partial update
        Divisions partialUpdatedDivisions = new Divisions();
        partialUpdatedDivisions.setId(divisions.getId());

        partialUpdatedDivisions.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restDivisionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivisions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDivisions))
            )
            .andExpect(status().isOk());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
        Divisions testDivisions = divisionsList.get(divisionsList.size() - 1);
        assertThat(testDivisions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDivisions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDivisions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDivisionsWithPatch() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();

        // Update the divisions using partial update
        Divisions partialUpdatedDivisions = new Divisions();
        partialUpdatedDivisions.setId(divisions.getId());

        partialUpdatedDivisions.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restDivisionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivisions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDivisions))
            )
            .andExpect(status().isOk());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
        Divisions testDivisions = divisionsList.get(divisionsList.size() - 1);
        assertThat(testDivisions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDivisions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDivisions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDivisions() throws Exception {
        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();
        divisions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, divisions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDivisions() throws Exception {
        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();
        divisions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDivisions() throws Exception {
        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();
        divisions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(divisions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDivisions() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        int databaseSizeBeforeDelete = divisionsRepository.findAll().size();

        // Delete the divisions
        restDivisionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, divisions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
