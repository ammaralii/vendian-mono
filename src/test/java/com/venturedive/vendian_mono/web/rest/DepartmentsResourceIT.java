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
import com.venturedive.vendian_mono.repository.DepartmentsRepository;
import com.venturedive.vendian_mono.service.criteria.DepartmentsCriteria;
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
 * Integration tests for the {@link DepartmentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepartmentsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/departments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartmentsMockMvc;

    private Departments departments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departments createEntity(EntityManager em) {
        Departments departments = new Departments().name(DEFAULT_NAME).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return departments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departments createUpdatedEntity(EntityManager em) {
        Departments departments = new Departments().name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return departments;
    }

    @BeforeEach
    public void initTest() {
        departments = createEntity(em);
    }

    @Test
    @Transactional
    void createDepartments() throws Exception {
        int databaseSizeBeforeCreate = departmentsRepository.findAll().size();
        // Create the Departments
        restDepartmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departments))
            )
            .andExpect(status().isCreated());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeCreate + 1);
        Departments testDepartments = departmentsList.get(departmentsList.size() - 1);
        assertThat(testDepartments.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDepartments.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDepartments.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createDepartmentsWithExistingId() throws Exception {
        // Create the Departments with an existing ID
        departments.setId(1L);

        int databaseSizeBeforeCreate = departmentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepartments() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList
        restDepartmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departments.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getDepartments() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get the departments
        restDepartmentsMockMvc
            .perform(get(ENTITY_API_URL_ID, departments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departments.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getDepartmentsByIdFiltering() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        Long id = departments.getId();

        defaultDepartmentsShouldBeFound("id.equals=" + id);
        defaultDepartmentsShouldNotBeFound("id.notEquals=" + id);

        defaultDepartmentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDepartmentsShouldNotBeFound("id.greaterThan=" + id);

        defaultDepartmentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDepartmentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where name equals to DEFAULT_NAME
        defaultDepartmentsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the departmentsList where name equals to UPDATED_NAME
        defaultDepartmentsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDepartmentsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the departmentsList where name equals to UPDATED_NAME
        defaultDepartmentsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where name is not null
        defaultDepartmentsShouldBeFound("name.specified=true");

        // Get all the departmentsList where name is null
        defaultDepartmentsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameContainsSomething() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where name contains DEFAULT_NAME
        defaultDepartmentsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the departmentsList where name contains UPDATED_NAME
        defaultDepartmentsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where name does not contain DEFAULT_NAME
        defaultDepartmentsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the departmentsList where name does not contain UPDATED_NAME
        defaultDepartmentsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where createdat equals to DEFAULT_CREATEDAT
        defaultDepartmentsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the departmentsList where createdat equals to UPDATED_CREATEDAT
        defaultDepartmentsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDepartmentsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the departmentsList where createdat equals to UPDATED_CREATEDAT
        defaultDepartmentsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where createdat is not null
        defaultDepartmentsShouldBeFound("createdat.specified=true");

        // Get all the departmentsList where createdat is null
        defaultDepartmentsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDepartmentsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the departmentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDepartmentsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDepartmentsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDepartmentsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the departmentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDepartmentsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDepartmentsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        // Get all the departmentsList where updatedat is not null
        defaultDepartmentsShouldBeFound("updatedat.specified=true");

        // Get all the departmentsList where updatedat is null
        defaultDepartmentsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByDivisionIsEqualToSomething() throws Exception {
        Divisions division;
        if (TestUtil.findAll(em, Divisions.class).isEmpty()) {
            departmentsRepository.saveAndFlush(departments);
            division = DivisionsResourceIT.createEntity(em);
        } else {
            division = TestUtil.findAll(em, Divisions.class).get(0);
        }
        em.persist(division);
        em.flush();
        departments.setDivision(division);
        departmentsRepository.saveAndFlush(departments);
        Long divisionId = division.getId();

        // Get all the departmentsList where division equals to divisionId
        defaultDepartmentsShouldBeFound("divisionId.equals=" + divisionId);

        // Get all the departmentsList where division equals to (divisionId + 1)
        defaultDepartmentsShouldNotBeFound("divisionId.equals=" + (divisionId + 1));
    }

    @Test
    @Transactional
    void getAllDepartmentsByEmployeejobinfoDepartmentIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoDepartment;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            departmentsRepository.saveAndFlush(departments);
            employeejobinfoDepartment = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoDepartment = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoDepartment);
        em.flush();
        departments.addEmployeejobinfoDepartment(employeejobinfoDepartment);
        departmentsRepository.saveAndFlush(departments);
        Long employeejobinfoDepartmentId = employeejobinfoDepartment.getId();

        // Get all the departmentsList where employeejobinfoDepartment equals to employeejobinfoDepartmentId
        defaultDepartmentsShouldBeFound("employeejobinfoDepartmentId.equals=" + employeejobinfoDepartmentId);

        // Get all the departmentsList where employeejobinfoDepartment equals to (employeejobinfoDepartmentId + 1)
        defaultDepartmentsShouldNotBeFound("employeejobinfoDepartmentId.equals=" + (employeejobinfoDepartmentId + 1));
    }

    @Test
    @Transactional
    void getAllDepartmentsByEmployeesDepartmentIsEqualToSomething() throws Exception {
        Employees employeesDepartment;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            departmentsRepository.saveAndFlush(departments);
            employeesDepartment = EmployeesResourceIT.createEntity(em);
        } else {
            employeesDepartment = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesDepartment);
        em.flush();
        departments.addEmployeesDepartment(employeesDepartment);
        departmentsRepository.saveAndFlush(departments);
        Long employeesDepartmentId = employeesDepartment.getId();

        // Get all the departmentsList where employeesDepartment equals to employeesDepartmentId
        defaultDepartmentsShouldBeFound("employeesDepartmentId.equals=" + employeesDepartmentId);

        // Get all the departmentsList where employeesDepartment equals to (employeesDepartmentId + 1)
        defaultDepartmentsShouldNotBeFound("employeesDepartmentId.equals=" + (employeesDepartmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartmentsShouldBeFound(String filter) throws Exception {
        restDepartmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departments.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restDepartmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartmentsShouldNotBeFound(String filter) throws Exception {
        restDepartmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDepartments() throws Exception {
        // Get the departments
        restDepartmentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepartments() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();

        // Update the departments
        Departments updatedDepartments = departmentsRepository.findById(departments.getId()).get();
        // Disconnect from session so that the updates on updatedDepartments are not directly saved in db
        em.detach(updatedDepartments);
        updatedDepartments.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restDepartmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDepartments.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDepartments))
            )
            .andExpect(status().isOk());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
        Departments testDepartments = departmentsList.get(departmentsList.size() - 1);
        assertThat(testDepartments.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDepartments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDepartments() throws Exception {
        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();
        departments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departments.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartments() throws Exception {
        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();
        departments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartments() throws Exception {
        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();
        departments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartmentsWithPatch() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();

        // Update the departments using partial update
        Departments partialUpdatedDepartments = new Departments();
        partialUpdatedDepartments.setId(departments.getId());

        partialUpdatedDepartments.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restDepartmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepartments))
            )
            .andExpect(status().isOk());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
        Departments testDepartments = departmentsList.get(departmentsList.size() - 1);
        assertThat(testDepartments.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDepartments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDepartments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDepartmentsWithPatch() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();

        // Update the departments using partial update
        Departments partialUpdatedDepartments = new Departments();
        partialUpdatedDepartments.setId(departments.getId());

        partialUpdatedDepartments.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restDepartmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepartments))
            )
            .andExpect(status().isOk());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
        Departments testDepartments = departmentsList.get(departmentsList.size() - 1);
        assertThat(testDepartments.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDepartments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDepartments() throws Exception {
        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();
        departments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartments() throws Exception {
        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();
        departments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartments() throws Exception {
        int databaseSizeBeforeUpdate = departmentsRepository.findAll().size();
        departments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Departments in the database
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartments() throws Exception {
        // Initialize the database
        departmentsRepository.saveAndFlush(departments);

        int databaseSizeBeforeDelete = departmentsRepository.findAll().size();

        // Delete the departments
        restDepartmentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, departments.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Departments> departmentsList = departmentsRepository.findAll();
        assertThat(departmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
