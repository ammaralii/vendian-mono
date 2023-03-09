package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealResourceSkills;
import com.venturedive.vendian_mono.domain.DealResources;
import com.venturedive.vendian_mono.domain.Skills;
import com.venturedive.vendian_mono.repository.DealResourceSkillsRepository;
import com.venturedive.vendian_mono.service.criteria.DealResourceSkillsCriteria;
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
 * Integration tests for the {@link DealResourceSkillsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealResourceSkillsResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/deal-resource-skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealResourceSkillsRepository dealResourceSkillsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealResourceSkillsMockMvc;

    private DealResourceSkills dealResourceSkills;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealResourceSkills createEntity(EntityManager em) {
        DealResourceSkills dealResourceSkills = new DealResourceSkills()
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return dealResourceSkills;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealResourceSkills createUpdatedEntity(EntityManager em) {
        DealResourceSkills dealResourceSkills = new DealResourceSkills()
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return dealResourceSkills;
    }

    @BeforeEach
    public void initTest() {
        dealResourceSkills = createEntity(em);
    }

    @Test
    @Transactional
    void createDealResourceSkills() throws Exception {
        int databaseSizeBeforeCreate = dealResourceSkillsRepository.findAll().size();
        // Create the DealResourceSkills
        restDealResourceSkillsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceSkills))
            )
            .andExpect(status().isCreated());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeCreate + 1);
        DealResourceSkills testDealResourceSkills = dealResourceSkillsList.get(dealResourceSkillsList.size() - 1);
        assertThat(testDealResourceSkills.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDealResourceSkills.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDealResourceSkills.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createDealResourceSkillsWithExistingId() throws Exception {
        // Create the DealResourceSkills with an existing ID
        dealResourceSkills.setId(1L);

        int databaseSizeBeforeCreate = dealResourceSkillsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealResourceSkillsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDealResourceSkills() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList
        restDealResourceSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealResourceSkills.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    @Transactional
    void getDealResourceSkills() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get the dealResourceSkills
        restDealResourceSkillsMockMvc
            .perform(get(ENTITY_API_URL_ID, dealResourceSkills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealResourceSkills.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getDealResourceSkillsByIdFiltering() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        Long id = dealResourceSkills.getId();

        defaultDealResourceSkillsShouldBeFound("id.equals=" + id);
        defaultDealResourceSkillsShouldNotBeFound("id.notEquals=" + id);

        defaultDealResourceSkillsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealResourceSkillsShouldNotBeFound("id.greaterThan=" + id);

        defaultDealResourceSkillsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealResourceSkillsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where createdat equals to DEFAULT_CREATEDAT
        defaultDealResourceSkillsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the dealResourceSkillsList where createdat equals to UPDATED_CREATEDAT
        defaultDealResourceSkillsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDealResourceSkillsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the dealResourceSkillsList where createdat equals to UPDATED_CREATEDAT
        defaultDealResourceSkillsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where createdat is not null
        defaultDealResourceSkillsShouldBeFound("createdat.specified=true");

        // Get all the dealResourceSkillsList where createdat is null
        defaultDealResourceSkillsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDealResourceSkillsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the dealResourceSkillsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealResourceSkillsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDealResourceSkillsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the dealResourceSkillsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealResourceSkillsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where updatedat is not null
        defaultDealResourceSkillsShouldBeFound("updatedat.specified=true");

        // Get all the dealResourceSkillsList where updatedat is null
        defaultDealResourceSkillsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where deletedat equals to DEFAULT_DELETEDAT
        defaultDealResourceSkillsShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the dealResourceSkillsList where deletedat equals to UPDATED_DELETEDAT
        defaultDealResourceSkillsShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultDealResourceSkillsShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the dealResourceSkillsList where deletedat equals to UPDATED_DELETEDAT
        defaultDealResourceSkillsShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        // Get all the dealResourceSkillsList where deletedat is not null
        defaultDealResourceSkillsShouldBeFound("deletedat.specified=true");

        // Get all the dealResourceSkillsList where deletedat is null
        defaultDealResourceSkillsShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsByResourceIsEqualToSomething() throws Exception {
        DealResources resource;
        if (TestUtil.findAll(em, DealResources.class).isEmpty()) {
            dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);
            resource = DealResourcesResourceIT.createEntity(em);
        } else {
            resource = TestUtil.findAll(em, DealResources.class).get(0);
        }
        em.persist(resource);
        em.flush();
        dealResourceSkills.setResource(resource);
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);
        Long resourceId = resource.getId();

        // Get all the dealResourceSkillsList where resource equals to resourceId
        defaultDealResourceSkillsShouldBeFound("resourceId.equals=" + resourceId);

        // Get all the dealResourceSkillsList where resource equals to (resourceId + 1)
        defaultDealResourceSkillsShouldNotBeFound("resourceId.equals=" + (resourceId + 1));
    }

    @Test
    @Transactional
    void getAllDealResourceSkillsBySkillIsEqualToSomething() throws Exception {
        Skills skill;
        if (TestUtil.findAll(em, Skills.class).isEmpty()) {
            dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);
            skill = SkillsResourceIT.createEntity(em);
        } else {
            skill = TestUtil.findAll(em, Skills.class).get(0);
        }
        em.persist(skill);
        em.flush();
        dealResourceSkills.setSkill(skill);
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);
        Long skillId = skill.getId();

        // Get all the dealResourceSkillsList where skill equals to skillId
        defaultDealResourceSkillsShouldBeFound("skillId.equals=" + skillId);

        // Get all the dealResourceSkillsList where skill equals to (skillId + 1)
        defaultDealResourceSkillsShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealResourceSkillsShouldBeFound(String filter) throws Exception {
        restDealResourceSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealResourceSkills.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restDealResourceSkillsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealResourceSkillsShouldNotBeFound(String filter) throws Exception {
        restDealResourceSkillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealResourceSkillsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDealResourceSkills() throws Exception {
        // Get the dealResourceSkills
        restDealResourceSkillsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDealResourceSkills() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();

        // Update the dealResourceSkills
        DealResourceSkills updatedDealResourceSkills = dealResourceSkillsRepository.findById(dealResourceSkills.getId()).get();
        // Disconnect from session so that the updates on updatedDealResourceSkills are not directly saved in db
        em.detach(updatedDealResourceSkills);
        updatedDealResourceSkills.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).deletedat(UPDATED_DELETEDAT);

        restDealResourceSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealResourceSkills.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealResourceSkills))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
        DealResourceSkills testDealResourceSkills = dealResourceSkillsList.get(dealResourceSkillsList.size() - 1);
        assertThat(testDealResourceSkills.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealResourceSkills.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealResourceSkills.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDealResourceSkills() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();
        dealResourceSkills.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealResourceSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealResourceSkills.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealResourceSkills() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();
        dealResourceSkills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceSkillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealResourceSkills() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();
        dealResourceSkills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceSkillsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceSkills))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealResourceSkillsWithPatch() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();

        // Update the dealResourceSkills using partial update
        DealResourceSkills partialUpdatedDealResourceSkills = new DealResourceSkills();
        partialUpdatedDealResourceSkills.setId(dealResourceSkills.getId());

        restDealResourceSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealResourceSkills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealResourceSkills))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
        DealResourceSkills testDealResourceSkills = dealResourceSkillsList.get(dealResourceSkillsList.size() - 1);
        assertThat(testDealResourceSkills.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDealResourceSkills.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDealResourceSkills.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDealResourceSkillsWithPatch() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();

        // Update the dealResourceSkills using partial update
        DealResourceSkills partialUpdatedDealResourceSkills = new DealResourceSkills();
        partialUpdatedDealResourceSkills.setId(dealResourceSkills.getId());

        partialUpdatedDealResourceSkills.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).deletedat(UPDATED_DELETEDAT);

        restDealResourceSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealResourceSkills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealResourceSkills))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
        DealResourceSkills testDealResourceSkills = dealResourceSkillsList.get(dealResourceSkillsList.size() - 1);
        assertThat(testDealResourceSkills.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealResourceSkills.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealResourceSkills.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDealResourceSkills() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();
        dealResourceSkills.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealResourceSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealResourceSkills.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealResourceSkills() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();
        dealResourceSkills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceSkills))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealResourceSkills() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceSkillsRepository.findAll().size();
        dealResourceSkills.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceSkillsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceSkills))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealResourceSkills in the database
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealResourceSkills() throws Exception {
        // Initialize the database
        dealResourceSkillsRepository.saveAndFlush(dealResourceSkills);

        int databaseSizeBeforeDelete = dealResourceSkillsRepository.findAll().size();

        // Delete the dealResourceSkills
        restDealResourceSkillsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealResourceSkills.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealResourceSkills> dealResourceSkillsList = dealResourceSkillsRepository.findAll();
        assertThat(dealResourceSkillsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
