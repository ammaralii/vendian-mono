package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.EmploymentStatuses;
import com.venturedive.vendian_mono.repository.EmploymentStatusesRepository;
import com.venturedive.vendian_mono.service.criteria.EmploymentStatusesCriteria;
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
 * Integration tests for the {@link EmploymentStatusesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmploymentStatusesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employment-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmploymentStatusesRepository employmentStatusesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploymentStatusesMockMvc;

    private EmploymentStatuses employmentStatuses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentStatuses createEntity(EntityManager em) {
        EmploymentStatuses employmentStatuses = new EmploymentStatuses()
            .name(DEFAULT_NAME)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return employmentStatuses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentStatuses createUpdatedEntity(EntityManager em) {
        EmploymentStatuses employmentStatuses = new EmploymentStatuses()
            .name(UPDATED_NAME)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return employmentStatuses;
    }

    @BeforeEach
    public void initTest() {
        employmentStatuses = createEntity(em);
    }

    @Test
    @Transactional
    void createEmploymentStatuses() throws Exception {
        int databaseSizeBeforeCreate = employmentStatusesRepository.findAll().size();
        // Create the EmploymentStatuses
        restEmploymentStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isCreated());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeCreate + 1);
        EmploymentStatuses testEmploymentStatuses = employmentStatusesList.get(employmentStatusesList.size() - 1);
        assertThat(testEmploymentStatuses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmploymentStatuses.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmploymentStatuses.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmploymentStatusesWithExistingId() throws Exception {
        // Create the EmploymentStatuses with an existing ID
        employmentStatuses.setId(1L);

        int databaseSizeBeforeCreate = employmentStatusesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentStatusesRepository.findAll().size();
        // set the field null
        employmentStatuses.setName(null);

        // Create the EmploymentStatuses, which fails.

        restEmploymentStatusesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isBadRequest());

        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmploymentStatuses() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList
        restEmploymentStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmploymentStatuses() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get the employmentStatuses
        restEmploymentStatusesMockMvc
            .perform(get(ENTITY_API_URL_ID, employmentStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employmentStatuses.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmploymentStatusesByIdFiltering() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        Long id = employmentStatuses.getId();

        defaultEmploymentStatusesShouldBeFound("id.equals=" + id);
        defaultEmploymentStatusesShouldNotBeFound("id.notEquals=" + id);

        defaultEmploymentStatusesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmploymentStatusesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmploymentStatusesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmploymentStatusesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where name equals to DEFAULT_NAME
        defaultEmploymentStatusesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the employmentStatusesList where name equals to UPDATED_NAME
        defaultEmploymentStatusesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmploymentStatusesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the employmentStatusesList where name equals to UPDATED_NAME
        defaultEmploymentStatusesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where name is not null
        defaultEmploymentStatusesShouldBeFound("name.specified=true");

        // Get all the employmentStatusesList where name is null
        defaultEmploymentStatusesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByNameContainsSomething() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where name contains DEFAULT_NAME
        defaultEmploymentStatusesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the employmentStatusesList where name contains UPDATED_NAME
        defaultEmploymentStatusesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where name does not contain DEFAULT_NAME
        defaultEmploymentStatusesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the employmentStatusesList where name does not contain UPDATED_NAME
        defaultEmploymentStatusesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where createdat equals to DEFAULT_CREATEDAT
        defaultEmploymentStatusesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employmentStatusesList where createdat equals to UPDATED_CREATEDAT
        defaultEmploymentStatusesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmploymentStatusesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employmentStatusesList where createdat equals to UPDATED_CREATEDAT
        defaultEmploymentStatusesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where createdat is not null
        defaultEmploymentStatusesShouldBeFound("createdat.specified=true");

        // Get all the employmentStatusesList where createdat is null
        defaultEmploymentStatusesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmploymentStatusesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employmentStatusesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmploymentStatusesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmploymentStatusesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employmentStatusesList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmploymentStatusesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        // Get all the employmentStatusesList where updatedat is not null
        defaultEmploymentStatusesShouldBeFound("updatedat.specified=true");

        // Get all the employmentStatusesList where updatedat is null
        defaultEmploymentStatusesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentStatusesByEmployeesEmploymentstatusIsEqualToSomething() throws Exception {
        Employees employeesEmploymentstatus;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employmentStatusesRepository.saveAndFlush(employmentStatuses);
            employeesEmploymentstatus = EmployeesResourceIT.createEntity(em);
        } else {
            employeesEmploymentstatus = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesEmploymentstatus);
        em.flush();
        employmentStatuses.addEmployeesEmploymentstatus(employeesEmploymentstatus);
        employmentStatusesRepository.saveAndFlush(employmentStatuses);
        Long employeesEmploymentstatusId = employeesEmploymentstatus.getId();

        // Get all the employmentStatusesList where employeesEmploymentstatus equals to employeesEmploymentstatusId
        defaultEmploymentStatusesShouldBeFound("employeesEmploymentstatusId.equals=" + employeesEmploymentstatusId);

        // Get all the employmentStatusesList where employeesEmploymentstatus equals to (employeesEmploymentstatusId + 1)
        defaultEmploymentStatusesShouldNotBeFound("employeesEmploymentstatusId.equals=" + (employeesEmploymentstatusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmploymentStatusesShouldBeFound(String filter) throws Exception {
        restEmploymentStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmploymentStatusesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmploymentStatusesShouldNotBeFound(String filter) throws Exception {
        restEmploymentStatusesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmploymentStatusesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmploymentStatuses() throws Exception {
        // Get the employmentStatuses
        restEmploymentStatusesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmploymentStatuses() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();

        // Update the employmentStatuses
        EmploymentStatuses updatedEmploymentStatuses = employmentStatusesRepository.findById(employmentStatuses.getId()).get();
        // Disconnect from session so that the updates on updatedEmploymentStatuses are not directly saved in db
        em.detach(updatedEmploymentStatuses);
        updatedEmploymentStatuses.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restEmploymentStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmploymentStatuses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmploymentStatuses))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
        EmploymentStatuses testEmploymentStatuses = employmentStatusesList.get(employmentStatusesList.size() - 1);
        assertThat(testEmploymentStatuses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmploymentStatuses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmploymentStatuses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmploymentStatuses() throws Exception {
        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();
        employmentStatuses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employmentStatuses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploymentStatuses() throws Exception {
        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();
        employmentStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentStatusesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploymentStatuses() throws Exception {
        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();
        employmentStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentStatusesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmploymentStatusesWithPatch() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();

        // Update the employmentStatuses using partial update
        EmploymentStatuses partialUpdatedEmploymentStatuses = new EmploymentStatuses();
        partialUpdatedEmploymentStatuses.setId(employmentStatuses.getId());

        partialUpdatedEmploymentStatuses.createdat(UPDATED_CREATEDAT);

        restEmploymentStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploymentStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploymentStatuses))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
        EmploymentStatuses testEmploymentStatuses = employmentStatusesList.get(employmentStatusesList.size() - 1);
        assertThat(testEmploymentStatuses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmploymentStatuses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmploymentStatuses.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmploymentStatusesWithPatch() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();

        // Update the employmentStatuses using partial update
        EmploymentStatuses partialUpdatedEmploymentStatuses = new EmploymentStatuses();
        partialUpdatedEmploymentStatuses.setId(employmentStatuses.getId());

        partialUpdatedEmploymentStatuses.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restEmploymentStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploymentStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploymentStatuses))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
        EmploymentStatuses testEmploymentStatuses = employmentStatusesList.get(employmentStatusesList.size() - 1);
        assertThat(testEmploymentStatuses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmploymentStatuses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmploymentStatuses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmploymentStatuses() throws Exception {
        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();
        employmentStatuses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employmentStatuses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploymentStatuses() throws Exception {
        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();
        employmentStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploymentStatuses() throws Exception {
        int databaseSizeBeforeUpdate = employmentStatusesRepository.findAll().size();
        employmentStatuses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentStatusesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentStatuses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmploymentStatuses in the database
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploymentStatuses() throws Exception {
        // Initialize the database
        employmentStatusesRepository.saveAndFlush(employmentStatuses);

        int databaseSizeBeforeDelete = employmentStatusesRepository.findAll().size();

        // Delete the employmentStatuses
        restEmploymentStatusesMockMvc
            .perform(delete(ENTITY_API_URL_ID, employmentStatuses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmploymentStatuses> employmentStatusesList = employmentStatusesRepository.findAll();
        assertThat(employmentStatusesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
