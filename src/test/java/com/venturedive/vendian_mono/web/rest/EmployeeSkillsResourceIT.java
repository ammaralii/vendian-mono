package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeSkills;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Skills;
import com.venturedive.vendian_mono.repository.EmployeeSkillsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeSkillsCriteria;
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
 * Integration tests for the {@link EmployeeSkillsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeSkillsResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_EXPERTISE = 1;
    private static final Integer UPDATED_EXPERTISE = 2;
    private static final Integer SMALLER_EXPERTISE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/employee-skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeSkillsRepository employeeSkillsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeSkillsMockMvc;

    private EmployeeSkills employeeSkills;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSkills createEntity(EntityManager em) {
        EmployeeSkills employeeSkills = new EmployeeSkills()
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .expertise(DEFAULT_EXPERTISE);
        return employeeSkills;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSkills createUpdatedEntity(EntityManager em) {
        EmployeeSkills employeeSkills = new EmployeeSkills()
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .expertise(UPDATED_EXPERTISE);
        return employeeSkills;
    }

    @BeforeEach
    public void initTest() {
        employeeSkills = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeSkills() throws Exception {
        int databaseSizeBeforeCreate = employeeSkillsRepository.findAll().size();
        // Create the EmployeeSkills
        restEmployeeSkillsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSkills testEmployeeSkills = employeeSkillsList.get(employeeSkillsList.size() - 1);
        assertThat(testEmployeeSkills.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeSkills.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeSkills.getExpertise()).isEqualTo(DEFAULT_EXPERTISE);
    }

    @Test
    @Transactional
    void createEmployeeSkillsWithExistingId() throws Exception {
        // Create the EmployeeSkills with an existing ID
        employeeSkills.setId(1L);

        int databaseSizeBeforeCreate = employeeSkillsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSkillsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSkillsRepository.findAll().size();
        // set the field null
        employeeSkills.setCreatedat(null);

        // Create the EmployeeSkills, which fails.

        restEmployeeSkillsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSkillsRepository.findAll().size();
        // set the field null
        employeeSkills.setUpdatedat(null);

        // Create the EmployeeSkills, which fails.

        restEmployeeSkillsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeSkills() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList
        restEmployeeSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSkills.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].expertise").value(hasItem(DEFAULT_EXPERTISE)));
    }

    @Test
    @Transactional
    void getEmployeeSkills() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get the employeeSkills
        restEmployeeSkillsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeSkills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeSkills.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.expertise").value(DEFAULT_EXPERTISE));
    }

    @Test
    @Transactional
    void getEmployeeSkillsByIdFiltering() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        Long id = employeeSkills.getId();

        defaultEmployeeSkillsShouldBeFound("id.equals=" + id);
        defaultEmployeeSkillsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeSkillsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeSkillsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeSkillsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeSkillsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeSkillsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeSkillsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeSkillsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeSkillsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeSkillsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeSkillsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where createdat is not null
        defaultEmployeeSkillsShouldBeFound("createdat.specified=true");

        // Get all the employeeSkillsList where createdat is null
        defaultEmployeeSkillsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeSkillsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeSkillsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeSkillsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeSkillsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeSkillsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeSkillsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where updatedat is not null
        defaultEmployeeSkillsShouldBeFound("updatedat.specified=true");

        // Get all the employeeSkillsList where updatedat is null
        defaultEmployeeSkillsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByExpertiseIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where expertise equals to DEFAULT_EXPERTISE
        defaultEmployeeSkillsShouldBeFound("expertise.equals=" + DEFAULT_EXPERTISE);

        // Get all the employeeSkillsList where expertise equals to UPDATED_EXPERTISE
        defaultEmployeeSkillsShouldNotBeFound("expertise.equals=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByExpertiseIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where expertise in DEFAULT_EXPERTISE or UPDATED_EXPERTISE
        defaultEmployeeSkillsShouldBeFound("expertise.in=" + DEFAULT_EXPERTISE + "," + UPDATED_EXPERTISE);

        // Get all the employeeSkillsList where expertise equals to UPDATED_EXPERTISE
        defaultEmployeeSkillsShouldNotBeFound("expertise.in=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByExpertiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where expertise is not null
        defaultEmployeeSkillsShouldBeFound("expertise.specified=true");

        // Get all the employeeSkillsList where expertise is null
        defaultEmployeeSkillsShouldNotBeFound("expertise.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByExpertiseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where expertise is greater than or equal to DEFAULT_EXPERTISE
        defaultEmployeeSkillsShouldBeFound("expertise.greaterThanOrEqual=" + DEFAULT_EXPERTISE);

        // Get all the employeeSkillsList where expertise is greater than or equal to UPDATED_EXPERTISE
        defaultEmployeeSkillsShouldNotBeFound("expertise.greaterThanOrEqual=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByExpertiseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where expertise is less than or equal to DEFAULT_EXPERTISE
        defaultEmployeeSkillsShouldBeFound("expertise.lessThanOrEqual=" + DEFAULT_EXPERTISE);

        // Get all the employeeSkillsList where expertise is less than or equal to SMALLER_EXPERTISE
        defaultEmployeeSkillsShouldNotBeFound("expertise.lessThanOrEqual=" + SMALLER_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByExpertiseIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where expertise is less than DEFAULT_EXPERTISE
        defaultEmployeeSkillsShouldNotBeFound("expertise.lessThan=" + DEFAULT_EXPERTISE);

        // Get all the employeeSkillsList where expertise is less than UPDATED_EXPERTISE
        defaultEmployeeSkillsShouldBeFound("expertise.lessThan=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByExpertiseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        // Get all the employeeSkillsList where expertise is greater than DEFAULT_EXPERTISE
        defaultEmployeeSkillsShouldNotBeFound("expertise.greaterThan=" + DEFAULT_EXPERTISE);

        // Get all the employeeSkillsList where expertise is greater than SMALLER_EXPERTISE
        defaultEmployeeSkillsShouldBeFound("expertise.greaterThan=" + SMALLER_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeSkillsRepository.saveAndFlush(employeeSkills);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeSkills.setEmployee(employee);
        employeeSkillsRepository.saveAndFlush(employeeSkills);
        Long employeeId = employee.getId();

        // Get all the employeeSkillsList where employee equals to employeeId
        defaultEmployeeSkillsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeSkillsList where employee equals to (employeeId + 1)
        defaultEmployeeSkillsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeSkillsBySkillIsEqualToSomething() throws Exception {
        Skills skill;
        if (TestUtil.findAll(em, Skills.class).isEmpty()) {
            employeeSkillsRepository.saveAndFlush(employeeSkills);
            skill = SkillsResourceIT.createEntity(em);
        } else {
            skill = TestUtil.findAll(em, Skills.class).get(0);
        }
        em.persist(skill);
        em.flush();
        employeeSkills.setSkill(skill);
        employeeSkillsRepository.saveAndFlush(employeeSkills);
        Long skillId = skill.getId();

        // Get all the employeeSkillsList where skill equals to skillId
        defaultEmployeeSkillsShouldBeFound("skillId.equals=" + skillId);

        // Get all the employeeSkillsList where skill equals to (skillId + 1)
        defaultEmployeeSkillsShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeSkillsShouldBeFound(String filter) throws Exception {
        restEmployeeSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSkills.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].expertise").value(hasItem(DEFAULT_EXPERTISE)));

        // Check, that the count call also returns 1
        restEmployeeSkillsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeSkillsShouldNotBeFound(String filter) throws Exception {
        restEmployeeSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeSkillsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeSkills() throws Exception {
        // Get the employeeSkills
        restEmployeeSkillsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeSkills() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();

        // Update the employeeSkills
        EmployeeSkills updatedEmployeeSkills = employeeSkillsRepository.findById(employeeSkills.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSkills are not directly saved in db
        em.detach(updatedEmployeeSkills);
        updatedEmployeeSkills.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).expertise(UPDATED_EXPERTISE);

        restEmployeeSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeSkills.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeSkills))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkills testEmployeeSkills = employeeSkillsList.get(employeeSkillsList.size() - 1);
        assertThat(testEmployeeSkills.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeSkills.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeSkills.getExpertise()).isEqualTo(UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeSkills() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();
        employeeSkills.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeSkills.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeSkills() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();
        employeeSkills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeSkills() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();
        employeeSkills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeSkillsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeSkillsWithPatch() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();

        // Update the employeeSkills using partial update
        EmployeeSkills partialUpdatedEmployeeSkills = new EmployeeSkills();
        partialUpdatedEmployeeSkills.setId(employeeSkills.getId());

        restEmployeeSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeSkills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeSkills))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkills testEmployeeSkills = employeeSkillsList.get(employeeSkillsList.size() - 1);
        assertThat(testEmployeeSkills.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeSkills.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeSkills.getExpertise()).isEqualTo(DEFAULT_EXPERTISE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeSkillsWithPatch() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();

        // Update the employeeSkills using partial update
        EmployeeSkills partialUpdatedEmployeeSkills = new EmployeeSkills();
        partialUpdatedEmployeeSkills.setId(employeeSkills.getId());

        partialUpdatedEmployeeSkills.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).expertise(UPDATED_EXPERTISE);

        restEmployeeSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeSkills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeSkills))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkills testEmployeeSkills = employeeSkillsList.get(employeeSkillsList.size() - 1);
        assertThat(testEmployeeSkills.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeSkills.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeSkills.getExpertise()).isEqualTo(UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeSkills() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();
        employeeSkills.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeSkills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeSkills() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();
        employeeSkills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeSkills() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillsRepository.findAll().size();
        employeeSkills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkills))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeSkills in the database
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeSkills() throws Exception {
        // Initialize the database
        employeeSkillsRepository.saveAndFlush(employeeSkills);

        int databaseSizeBeforeDelete = employeeSkillsRepository.findAll().size();

        // Delete the employeeSkills
        restEmployeeSkillsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeSkills.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeSkills> employeeSkillsList = employeeSkillsRepository.findAll();
        assertThat(employeeSkillsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
