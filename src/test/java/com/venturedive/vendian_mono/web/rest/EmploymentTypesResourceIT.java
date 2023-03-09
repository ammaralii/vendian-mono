package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.EmploymentTypes;
import com.venturedive.vendian_mono.repository.EmploymentTypesRepository;
import com.venturedive.vendian_mono.service.criteria.EmploymentTypesCriteria;
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
 * Integration tests for the {@link EmploymentTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmploymentTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employment-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmploymentTypesRepository employmentTypesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploymentTypesMockMvc;

    private EmploymentTypes employmentTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentTypes createEntity(EntityManager em) {
        EmploymentTypes employmentTypes = new EmploymentTypes()
            .name(DEFAULT_NAME)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return employmentTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentTypes createUpdatedEntity(EntityManager em) {
        EmploymentTypes employmentTypes = new EmploymentTypes()
            .name(UPDATED_NAME)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return employmentTypes;
    }

    @BeforeEach
    public void initTest() {
        employmentTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createEmploymentTypes() throws Exception {
        int databaseSizeBeforeCreate = employmentTypesRepository.findAll().size();
        // Create the EmploymentTypes
        restEmploymentTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isCreated());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeCreate + 1);
        EmploymentTypes testEmploymentTypes = employmentTypesList.get(employmentTypesList.size() - 1);
        assertThat(testEmploymentTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmploymentTypes.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmploymentTypes.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmploymentTypesWithExistingId() throws Exception {
        // Create the EmploymentTypes with an existing ID
        employmentTypes.setId(1L);

        int databaseSizeBeforeCreate = employmentTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentTypesRepository.findAll().size();
        // set the field null
        employmentTypes.setName(null);

        // Create the EmploymentTypes, which fails.

        restEmploymentTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isBadRequest());

        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmploymentTypes() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList
        restEmploymentTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmploymentTypes() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get the employmentTypes
        restEmploymentTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, employmentTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employmentTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmploymentTypesByIdFiltering() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        Long id = employmentTypes.getId();

        defaultEmploymentTypesShouldBeFound("id.equals=" + id);
        defaultEmploymentTypesShouldNotBeFound("id.notEquals=" + id);

        defaultEmploymentTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmploymentTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmploymentTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmploymentTypesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where name equals to DEFAULT_NAME
        defaultEmploymentTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the employmentTypesList where name equals to UPDATED_NAME
        defaultEmploymentTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmploymentTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the employmentTypesList where name equals to UPDATED_NAME
        defaultEmploymentTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where name is not null
        defaultEmploymentTypesShouldBeFound("name.specified=true");

        // Get all the employmentTypesList where name is null
        defaultEmploymentTypesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where name contains DEFAULT_NAME
        defaultEmploymentTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the employmentTypesList where name contains UPDATED_NAME
        defaultEmploymentTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where name does not contain DEFAULT_NAME
        defaultEmploymentTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the employmentTypesList where name does not contain UPDATED_NAME
        defaultEmploymentTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where createdat equals to DEFAULT_CREATEDAT
        defaultEmploymentTypesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employmentTypesList where createdat equals to UPDATED_CREATEDAT
        defaultEmploymentTypesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmploymentTypesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employmentTypesList where createdat equals to UPDATED_CREATEDAT
        defaultEmploymentTypesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where createdat is not null
        defaultEmploymentTypesShouldBeFound("createdat.specified=true");

        // Get all the employmentTypesList where createdat is null
        defaultEmploymentTypesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmploymentTypesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employmentTypesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmploymentTypesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmploymentTypesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employmentTypesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmploymentTypesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        // Get all the employmentTypesList where updatedat is not null
        defaultEmploymentTypesShouldBeFound("updatedat.specified=true");

        // Get all the employmentTypesList where updatedat is null
        defaultEmploymentTypesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByEmployeejobinfoEmploymenttypeIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoEmploymenttype;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            employmentTypesRepository.saveAndFlush(employmentTypes);
            employeejobinfoEmploymenttype = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoEmploymenttype = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoEmploymenttype);
        em.flush();
        employmentTypes.addEmployeejobinfoEmploymenttype(employeejobinfoEmploymenttype);
        employmentTypesRepository.saveAndFlush(employmentTypes);
        Long employeejobinfoEmploymenttypeId = employeejobinfoEmploymenttype.getId();

        // Get all the employmentTypesList where employeejobinfoEmploymenttype equals to employeejobinfoEmploymenttypeId
        defaultEmploymentTypesShouldBeFound("employeejobinfoEmploymenttypeId.equals=" + employeejobinfoEmploymenttypeId);

        // Get all the employmentTypesList where employeejobinfoEmploymenttype equals to (employeejobinfoEmploymenttypeId + 1)
        defaultEmploymentTypesShouldNotBeFound("employeejobinfoEmploymenttypeId.equals=" + (employeejobinfoEmploymenttypeId + 1));
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByEmployeesEmploymenttypeIsEqualToSomething() throws Exception {
        Employees employeesEmploymenttype;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employmentTypesRepository.saveAndFlush(employmentTypes);
            employeesEmploymenttype = EmployeesResourceIT.createEntity(em);
        } else {
            employeesEmploymenttype = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesEmploymenttype);
        em.flush();
        employmentTypes.addEmployeesEmploymenttype(employeesEmploymenttype);
        employmentTypesRepository.saveAndFlush(employmentTypes);
        Long employeesEmploymenttypeId = employeesEmploymenttype.getId();

        // Get all the employmentTypesList where employeesEmploymenttype equals to employeesEmploymenttypeId
        defaultEmploymentTypesShouldBeFound("employeesEmploymenttypeId.equals=" + employeesEmploymenttypeId);

        // Get all the employmentTypesList where employeesEmploymenttype equals to (employeesEmploymenttypeId + 1)
        defaultEmploymentTypesShouldNotBeFound("employeesEmploymenttypeId.equals=" + (employeesEmploymenttypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmploymentTypesShouldBeFound(String filter) throws Exception {
        restEmploymentTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmploymentTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmploymentTypesShouldNotBeFound(String filter) throws Exception {
        restEmploymentTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmploymentTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmploymentTypes() throws Exception {
        // Get the employmentTypes
        restEmploymentTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmploymentTypes() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();

        // Update the employmentTypes
        EmploymentTypes updatedEmploymentTypes = employmentTypesRepository.findById(employmentTypes.getId()).get();
        // Disconnect from session so that the updates on updatedEmploymentTypes are not directly saved in db
        em.detach(updatedEmploymentTypes);
        updatedEmploymentTypes.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restEmploymentTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmploymentTypes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmploymentTypes))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
        EmploymentTypes testEmploymentTypes = employmentTypesList.get(employmentTypesList.size() - 1);
        assertThat(testEmploymentTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmploymentTypes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmploymentTypes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmploymentTypes() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();
        employmentTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employmentTypes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploymentTypes() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();
        employmentTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploymentTypes() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();
        employmentTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentTypesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmploymentTypesWithPatch() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();

        // Update the employmentTypes using partial update
        EmploymentTypes partialUpdatedEmploymentTypes = new EmploymentTypes();
        partialUpdatedEmploymentTypes.setId(employmentTypes.getId());

        partialUpdatedEmploymentTypes.name(UPDATED_NAME);

        restEmploymentTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploymentTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploymentTypes))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
        EmploymentTypes testEmploymentTypes = employmentTypesList.get(employmentTypesList.size() - 1);
        assertThat(testEmploymentTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmploymentTypes.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmploymentTypes.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmploymentTypesWithPatch() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();

        // Update the employmentTypes using partial update
        EmploymentTypes partialUpdatedEmploymentTypes = new EmploymentTypes();
        partialUpdatedEmploymentTypes.setId(employmentTypes.getId());

        partialUpdatedEmploymentTypes.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restEmploymentTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploymentTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploymentTypes))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
        EmploymentTypes testEmploymentTypes = employmentTypesList.get(employmentTypesList.size() - 1);
        assertThat(testEmploymentTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmploymentTypes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmploymentTypes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmploymentTypes() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();
        employmentTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employmentTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploymentTypes() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();
        employmentTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploymentTypes() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypesRepository.findAll().size();
        employmentTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentTypesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmploymentTypes in the database
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploymentTypes() throws Exception {
        // Initialize the database
        employmentTypesRepository.saveAndFlush(employmentTypes);

        int databaseSizeBeforeDelete = employmentTypesRepository.findAll().size();

        // Delete the employmentTypes
        restEmploymentTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, employmentTypes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmploymentTypes> employmentTypesList = employmentTypesRepository.findAll();
        assertThat(employmentTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
