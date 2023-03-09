package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealResourceSkills;
import com.venturedive.vendian_mono.domain.EmployeeSkills;
import com.venturedive.vendian_mono.domain.Skills;
import com.venturedive.vendian_mono.repository.SkillsRepository;
import com.venturedive.vendian_mono.service.criteria.SkillsCriteria;
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
 * Integration tests for the {@link SkillsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkillsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillsMockMvc;

    private Skills skills;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skills createEntity(EntityManager em) {
        Skills skills = new Skills().name(DEFAULT_NAME).createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        return skills;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skills createUpdatedEntity(EntityManager em) {
        Skills skills = new Skills().name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        return skills;
    }

    @BeforeEach
    public void initTest() {
        skills = createEntity(em);
    }

    @Test
    @Transactional
    void createSkills() throws Exception {
        int databaseSizeBeforeCreate = skillsRepository.findAll().size();
        // Create the Skills
        restSkillsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isCreated());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeCreate + 1);
        Skills testSkills = skillsList.get(skillsList.size() - 1);
        assertThat(testSkills.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkills.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testSkills.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createSkillsWithExistingId() throws Exception {
        // Create the Skills with an existing ID
        skills.setId(1L);

        int databaseSizeBeforeCreate = skillsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillsRepository.findAll().size();
        // set the field null
        skills.setCreatedat(null);

        // Create the Skills, which fails.

        restSkillsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isBadRequest());

        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillsRepository.findAll().size();
        // set the field null
        skills.setUpdatedat(null);

        // Create the Skills, which fails.

        restSkillsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isBadRequest());

        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSkills() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList
        restSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skills.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getSkills() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get the skills
        restSkillsMockMvc
            .perform(get(ENTITY_API_URL_ID, skills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skills.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getSkillsByIdFiltering() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        Long id = skills.getId();

        defaultSkillsShouldBeFound("id.equals=" + id);
        defaultSkillsShouldNotBeFound("id.notEquals=" + id);

        defaultSkillsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSkillsShouldNotBeFound("id.greaterThan=" + id);

        defaultSkillsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSkillsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSkillsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where name equals to DEFAULT_NAME
        defaultSkillsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the skillsList where name equals to UPDATED_NAME
        defaultSkillsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSkillsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSkillsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the skillsList where name equals to UPDATED_NAME
        defaultSkillsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSkillsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where name is not null
        defaultSkillsShouldBeFound("name.specified=true");

        // Get all the skillsList where name is null
        defaultSkillsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByNameContainsSomething() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where name contains DEFAULT_NAME
        defaultSkillsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the skillsList where name contains UPDATED_NAME
        defaultSkillsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSkillsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where name does not contain DEFAULT_NAME
        defaultSkillsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the skillsList where name does not contain UPDATED_NAME
        defaultSkillsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSkillsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where createdat equals to DEFAULT_CREATEDAT
        defaultSkillsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the skillsList where createdat equals to UPDATED_CREATEDAT
        defaultSkillsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllSkillsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultSkillsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the skillsList where createdat equals to UPDATED_CREATEDAT
        defaultSkillsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllSkillsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where createdat is not null
        defaultSkillsShouldBeFound("createdat.specified=true");

        // Get all the skillsList where createdat is null
        defaultSkillsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultSkillsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the skillsList where updatedat equals to UPDATED_UPDATEDAT
        defaultSkillsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllSkillsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultSkillsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the skillsList where updatedat equals to UPDATED_UPDATEDAT
        defaultSkillsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllSkillsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where updatedat is not null
        defaultSkillsShouldBeFound("updatedat.specified=true");

        // Get all the skillsList where updatedat is null
        defaultSkillsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByDealresourceskillsSkillIsEqualToSomething() throws Exception {
        DealResourceSkills dealresourceskillsSkill;
        if (TestUtil.findAll(em, DealResourceSkills.class).isEmpty()) {
            skillsRepository.saveAndFlush(skills);
            dealresourceskillsSkill = DealResourceSkillsResourceIT.createEntity(em);
        } else {
            dealresourceskillsSkill = TestUtil.findAll(em, DealResourceSkills.class).get(0);
        }
        em.persist(dealresourceskillsSkill);
        em.flush();
        skills.addDealresourceskillsSkill(dealresourceskillsSkill);
        skillsRepository.saveAndFlush(skills);
        Long dealresourceskillsSkillId = dealresourceskillsSkill.getId();

        // Get all the skillsList where dealresourceskillsSkill equals to dealresourceskillsSkillId
        defaultSkillsShouldBeFound("dealresourceskillsSkillId.equals=" + dealresourceskillsSkillId);

        // Get all the skillsList where dealresourceskillsSkill equals to (dealresourceskillsSkillId + 1)
        defaultSkillsShouldNotBeFound("dealresourceskillsSkillId.equals=" + (dealresourceskillsSkillId + 1));
    }

    @Test
    @Transactional
    void getAllSkillsByEmployeeskillsSkillIsEqualToSomething() throws Exception {
        EmployeeSkills employeeskillsSkill;
        if (TestUtil.findAll(em, EmployeeSkills.class).isEmpty()) {
            skillsRepository.saveAndFlush(skills);
            employeeskillsSkill = EmployeeSkillsResourceIT.createEntity(em);
        } else {
            employeeskillsSkill = TestUtil.findAll(em, EmployeeSkills.class).get(0);
        }
        em.persist(employeeskillsSkill);
        em.flush();
        skills.addEmployeeskillsSkill(employeeskillsSkill);
        skillsRepository.saveAndFlush(skills);
        Long employeeskillsSkillId = employeeskillsSkill.getId();

        // Get all the skillsList where employeeskillsSkill equals to employeeskillsSkillId
        defaultSkillsShouldBeFound("employeeskillsSkillId.equals=" + employeeskillsSkillId);

        // Get all the skillsList where employeeskillsSkill equals to (employeeskillsSkillId + 1)
        defaultSkillsShouldNotBeFound("employeeskillsSkillId.equals=" + (employeeskillsSkillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillsShouldBeFound(String filter) throws Exception {
        restSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skills.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restSkillsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillsShouldNotBeFound(String filter) throws Exception {
        restSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSkills() throws Exception {
        // Get the skills
        restSkillsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSkills() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();

        // Update the skills
        Skills updatedSkills = skillsRepository.findById(skills.getId()).get();
        // Disconnect from session so that the updates on updatedSkills are not directly saved in db
        em.detach(updatedSkills);
        updatedSkills.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSkills.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSkills))
            )
            .andExpect(status().isOk());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
        Skills testSkills = skillsList.get(skillsList.size() - 1);
        assertThat(testSkills.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkills.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testSkills.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingSkills() throws Exception {
        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();
        skills.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skills.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSkills() throws Exception {
        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();
        skills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkills() throws Exception {
        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();
        skills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillsMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkillsWithPatch() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();

        // Update the skills using partial update
        Skills partialUpdatedSkills = new Skills();
        partialUpdatedSkills.setId(skills.getId());

        partialUpdatedSkills.name(UPDATED_NAME).updatedat(UPDATED_UPDATEDAT);

        restSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkills))
            )
            .andExpect(status().isOk());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
        Skills testSkills = skillsList.get(skillsList.size() - 1);
        assertThat(testSkills.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkills.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testSkills.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateSkillsWithPatch() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();

        // Update the skills using partial update
        Skills partialUpdatedSkills = new Skills();
        partialUpdatedSkills.setId(skills.getId());

        partialUpdatedSkills.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkills))
            )
            .andExpect(status().isOk());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
        Skills testSkills = skillsList.get(skillsList.size() - 1);
        assertThat(testSkills.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkills.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testSkills.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingSkills() throws Exception {
        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();
        skills.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkills() throws Exception {
        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();
        skills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkills() throws Exception {
        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();
        skills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skills))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSkills() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        int databaseSizeBeforeDelete = skillsRepository.findAll().size();

        // Delete the skills
        restSkillsMockMvc
            .perform(delete(ENTITY_API_URL_ID, skills.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
