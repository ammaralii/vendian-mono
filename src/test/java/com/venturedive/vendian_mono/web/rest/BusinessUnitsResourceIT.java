package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.BusinessUnits;
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.repository.BusinessUnitsRepository;
import com.venturedive.vendian_mono.service.criteria.BusinessUnitsCriteria;
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
 * Integration tests for the {@link BusinessUnitsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BusinessUnitsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/business-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusinessUnitsRepository businessUnitsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessUnitsMockMvc;

    private BusinessUnits businessUnits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnits createEntity(EntityManager em) {
        BusinessUnits businessUnits = new BusinessUnits().name(DEFAULT_NAME).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return businessUnits;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnits createUpdatedEntity(EntityManager em) {
        BusinessUnits businessUnits = new BusinessUnits().name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return businessUnits;
    }

    @BeforeEach
    public void initTest() {
        businessUnits = createEntity(em);
    }

    @Test
    @Transactional
    void createBusinessUnits() throws Exception {
        int databaseSizeBeforeCreate = businessUnitsRepository.findAll().size();
        // Create the BusinessUnits
        restBusinessUnitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isCreated());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessUnits testBusinessUnits = businessUnitsList.get(businessUnitsList.size() - 1);
        assertThat(testBusinessUnits.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBusinessUnits.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testBusinessUnits.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createBusinessUnitsWithExistingId() throws Exception {
        // Create the BusinessUnits with an existing ID
        businessUnits.setId(1L);

        int databaseSizeBeforeCreate = businessUnitsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessUnitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessUnitsRepository.findAll().size();
        // set the field null
        businessUnits.setName(null);

        // Create the BusinessUnits, which fails.

        restBusinessUnitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isBadRequest());

        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessUnitsRepository.findAll().size();
        // set the field null
        businessUnits.setCreatedat(null);

        // Create the BusinessUnits, which fails.

        restBusinessUnitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isBadRequest());

        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessUnitsRepository.findAll().size();
        // set the field null
        businessUnits.setUpdatedat(null);

        // Create the BusinessUnits, which fails.

        restBusinessUnitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isBadRequest());

        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBusinessUnits() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList
        restBusinessUnitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessUnits.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getBusinessUnits() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get the businessUnits
        restBusinessUnitsMockMvc
            .perform(get(ENTITY_API_URL_ID, businessUnits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessUnits.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getBusinessUnitsByIdFiltering() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        Long id = businessUnits.getId();

        defaultBusinessUnitsShouldBeFound("id.equals=" + id);
        defaultBusinessUnitsShouldNotBeFound("id.notEquals=" + id);

        defaultBusinessUnitsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusinessUnitsShouldNotBeFound("id.greaterThan=" + id);

        defaultBusinessUnitsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusinessUnitsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where name equals to DEFAULT_NAME
        defaultBusinessUnitsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the businessUnitsList where name equals to UPDATED_NAME
        defaultBusinessUnitsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBusinessUnitsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the businessUnitsList where name equals to UPDATED_NAME
        defaultBusinessUnitsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where name is not null
        defaultBusinessUnitsShouldBeFound("name.specified=true");

        // Get all the businessUnitsList where name is null
        defaultBusinessUnitsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByNameContainsSomething() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where name contains DEFAULT_NAME
        defaultBusinessUnitsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the businessUnitsList where name contains UPDATED_NAME
        defaultBusinessUnitsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where name does not contain DEFAULT_NAME
        defaultBusinessUnitsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the businessUnitsList where name does not contain UPDATED_NAME
        defaultBusinessUnitsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where createdat equals to DEFAULT_CREATEDAT
        defaultBusinessUnitsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the businessUnitsList where createdat equals to UPDATED_CREATEDAT
        defaultBusinessUnitsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultBusinessUnitsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the businessUnitsList where createdat equals to UPDATED_CREATEDAT
        defaultBusinessUnitsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where createdat is not null
        defaultBusinessUnitsShouldBeFound("createdat.specified=true");

        // Get all the businessUnitsList where createdat is null
        defaultBusinessUnitsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultBusinessUnitsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the businessUnitsList where updatedat equals to UPDATED_UPDATEDAT
        defaultBusinessUnitsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultBusinessUnitsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the businessUnitsList where updatedat equals to UPDATED_UPDATEDAT
        defaultBusinessUnitsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        // Get all the businessUnitsList where updatedat is not null
        defaultBusinessUnitsShouldBeFound("updatedat.specified=true");

        // Get all the businessUnitsList where updatedat is null
        defaultBusinessUnitsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByEmployeejobinfoBusinessunitIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoBusinessunit;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            businessUnitsRepository.saveAndFlush(businessUnits);
            employeejobinfoBusinessunit = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoBusinessunit = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoBusinessunit);
        em.flush();
        businessUnits.addEmployeejobinfoBusinessunit(employeejobinfoBusinessunit);
        businessUnitsRepository.saveAndFlush(businessUnits);
        Long employeejobinfoBusinessunitId = employeejobinfoBusinessunit.getId();

        // Get all the businessUnitsList where employeejobinfoBusinessunit equals to employeejobinfoBusinessunitId
        defaultBusinessUnitsShouldBeFound("employeejobinfoBusinessunitId.equals=" + employeejobinfoBusinessunitId);

        // Get all the businessUnitsList where employeejobinfoBusinessunit equals to (employeejobinfoBusinessunitId + 1)
        defaultBusinessUnitsShouldNotBeFound("employeejobinfoBusinessunitId.equals=" + (employeejobinfoBusinessunitId + 1));
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByEmployeesBusinessunitIsEqualToSomething() throws Exception {
        Employees employeesBusinessunit;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            businessUnitsRepository.saveAndFlush(businessUnits);
            employeesBusinessunit = EmployeesResourceIT.createEntity(em);
        } else {
            employeesBusinessunit = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesBusinessunit);
        em.flush();
        businessUnits.addEmployeesBusinessunit(employeesBusinessunit);
        businessUnitsRepository.saveAndFlush(businessUnits);
        Long employeesBusinessunitId = employeesBusinessunit.getId();

        // Get all the businessUnitsList where employeesBusinessunit equals to employeesBusinessunitId
        defaultBusinessUnitsShouldBeFound("employeesBusinessunitId.equals=" + employeesBusinessunitId);

        // Get all the businessUnitsList where employeesBusinessunit equals to (employeesBusinessunitId + 1)
        defaultBusinessUnitsShouldNotBeFound("employeesBusinessunitId.equals=" + (employeesBusinessunitId + 1));
    }

    @Test
    @Transactional
    void getAllBusinessUnitsByProjectsBusinessunitIsEqualToSomething() throws Exception {
        Projects projectsBusinessunit;
        if (TestUtil.findAll(em, Projects.class).isEmpty()) {
            businessUnitsRepository.saveAndFlush(businessUnits);
            projectsBusinessunit = ProjectsResourceIT.createEntity(em);
        } else {
            projectsBusinessunit = TestUtil.findAll(em, Projects.class).get(0);
        }
        em.persist(projectsBusinessunit);
        em.flush();
        businessUnits.addProjectsBusinessunit(projectsBusinessunit);
        businessUnitsRepository.saveAndFlush(businessUnits);
        Long projectsBusinessunitId = projectsBusinessunit.getId();

        // Get all the businessUnitsList where projectsBusinessunit equals to projectsBusinessunitId
        defaultBusinessUnitsShouldBeFound("projectsBusinessunitId.equals=" + projectsBusinessunitId);

        // Get all the businessUnitsList where projectsBusinessunit equals to (projectsBusinessunitId + 1)
        defaultBusinessUnitsShouldNotBeFound("projectsBusinessunitId.equals=" + (projectsBusinessunitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusinessUnitsShouldBeFound(String filter) throws Exception {
        restBusinessUnitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessUnits.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restBusinessUnitsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusinessUnitsShouldNotBeFound(String filter) throws Exception {
        restBusinessUnitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessUnitsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBusinessUnits() throws Exception {
        // Get the businessUnits
        restBusinessUnitsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBusinessUnits() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();

        // Update the businessUnits
        BusinessUnits updatedBusinessUnits = businessUnitsRepository.findById(businessUnits.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessUnits are not directly saved in db
        em.detach(updatedBusinessUnits);
        updatedBusinessUnits.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restBusinessUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBusinessUnits.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBusinessUnits))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnits testBusinessUnits = businessUnitsList.get(businessUnitsList.size() - 1);
        assertThat(testBusinessUnits.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessUnits.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testBusinessUnits.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingBusinessUnits() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();
        businessUnits.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessUnits.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBusinessUnits() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();
        businessUnits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBusinessUnits() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();
        businessUnits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusinessUnitsWithPatch() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();

        // Update the businessUnits using partial update
        BusinessUnits partialUpdatedBusinessUnits = new BusinessUnits();
        partialUpdatedBusinessUnits.setId(businessUnits.getId());

        partialUpdatedBusinessUnits.name(UPDATED_NAME);

        restBusinessUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessUnits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessUnits))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnits testBusinessUnits = businessUnitsList.get(businessUnitsList.size() - 1);
        assertThat(testBusinessUnits.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessUnits.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testBusinessUnits.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateBusinessUnitsWithPatch() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();

        // Update the businessUnits using partial update
        BusinessUnits partialUpdatedBusinessUnits = new BusinessUnits();
        partialUpdatedBusinessUnits.setId(businessUnits.getId());

        partialUpdatedBusinessUnits.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restBusinessUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessUnits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessUnits))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnits testBusinessUnits = businessUnitsList.get(businessUnitsList.size() - 1);
        assertThat(testBusinessUnits.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessUnits.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testBusinessUnits.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingBusinessUnits() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();
        businessUnits.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, businessUnits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBusinessUnits() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();
        businessUnits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBusinessUnits() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitsRepository.findAll().size();
        businessUnits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessUnits))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessUnits in the database
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBusinessUnits() throws Exception {
        // Initialize the database
        businessUnitsRepository.saveAndFlush(businessUnits);

        int databaseSizeBeforeDelete = businessUnitsRepository.findAll().size();

        // Delete the businessUnits
        restBusinessUnitsMockMvc
            .perform(delete(ENTITY_API_URL_ID, businessUnits.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessUnits> businessUnitsList = businessUnitsRepository.findAll();
        assertThat(businessUnitsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
