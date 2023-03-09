package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeCompensation;
import com.venturedive.vendian_mono.domain.Reasons;
import com.venturedive.vendian_mono.repository.ReasonsRepository;
import com.venturedive.vendian_mono.service.criteria.ReasonsCriteria;
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
 * Integration tests for the {@link ReasonsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReasonsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISDEFAULT = false;
    private static final Boolean UPDATED_ISDEFAULT = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/reasons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReasonsRepository reasonsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReasonsMockMvc;

    private Reasons reasons;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reasons createEntity(EntityManager em) {
        Reasons reasons = new Reasons()
            .name(DEFAULT_NAME)
            .isdefault(DEFAULT_ISDEFAULT)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return reasons;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reasons createUpdatedEntity(EntityManager em) {
        Reasons reasons = new Reasons()
            .name(UPDATED_NAME)
            .isdefault(UPDATED_ISDEFAULT)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return reasons;
    }

    @BeforeEach
    public void initTest() {
        reasons = createEntity(em);
    }

    @Test
    @Transactional
    void createReasons() throws Exception {
        int databaseSizeBeforeCreate = reasonsRepository.findAll().size();
        // Create the Reasons
        restReasonsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isCreated());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeCreate + 1);
        Reasons testReasons = reasonsList.get(reasonsList.size() - 1);
        assertThat(testReasons.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReasons.getIsdefault()).isEqualTo(DEFAULT_ISDEFAULT);
        assertThat(testReasons.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testReasons.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createReasonsWithExistingId() throws Exception {
        // Create the Reasons with an existing ID
        reasons.setId(1L);

        int databaseSizeBeforeCreate = reasonsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReasonsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reasonsRepository.findAll().size();
        // set the field null
        reasons.setName(null);

        // Create the Reasons, which fails.

        restReasonsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isBadRequest());

        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReasons() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList
        restReasonsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reasons.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isdefault").value(hasItem(DEFAULT_ISDEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getReasons() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get the reasons
        restReasonsMockMvc
            .perform(get(ENTITY_API_URL_ID, reasons.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reasons.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isdefault").value(DEFAULT_ISDEFAULT.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getReasonsByIdFiltering() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        Long id = reasons.getId();

        defaultReasonsShouldBeFound("id.equals=" + id);
        defaultReasonsShouldNotBeFound("id.notEquals=" + id);

        defaultReasonsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReasonsShouldNotBeFound("id.greaterThan=" + id);

        defaultReasonsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReasonsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllReasonsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where name equals to DEFAULT_NAME
        defaultReasonsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the reasonsList where name equals to UPDATED_NAME
        defaultReasonsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllReasonsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReasonsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the reasonsList where name equals to UPDATED_NAME
        defaultReasonsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllReasonsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where name is not null
        defaultReasonsShouldBeFound("name.specified=true");

        // Get all the reasonsList where name is null
        defaultReasonsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllReasonsByNameContainsSomething() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where name contains DEFAULT_NAME
        defaultReasonsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the reasonsList where name contains UPDATED_NAME
        defaultReasonsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllReasonsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where name does not contain DEFAULT_NAME
        defaultReasonsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the reasonsList where name does not contain UPDATED_NAME
        defaultReasonsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllReasonsByIsdefaultIsEqualToSomething() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where isdefault equals to DEFAULT_ISDEFAULT
        defaultReasonsShouldBeFound("isdefault.equals=" + DEFAULT_ISDEFAULT);

        // Get all the reasonsList where isdefault equals to UPDATED_ISDEFAULT
        defaultReasonsShouldNotBeFound("isdefault.equals=" + UPDATED_ISDEFAULT);
    }

    @Test
    @Transactional
    void getAllReasonsByIsdefaultIsInShouldWork() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where isdefault in DEFAULT_ISDEFAULT or UPDATED_ISDEFAULT
        defaultReasonsShouldBeFound("isdefault.in=" + DEFAULT_ISDEFAULT + "," + UPDATED_ISDEFAULT);

        // Get all the reasonsList where isdefault equals to UPDATED_ISDEFAULT
        defaultReasonsShouldNotBeFound("isdefault.in=" + UPDATED_ISDEFAULT);
    }

    @Test
    @Transactional
    void getAllReasonsByIsdefaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where isdefault is not null
        defaultReasonsShouldBeFound("isdefault.specified=true");

        // Get all the reasonsList where isdefault is null
        defaultReasonsShouldNotBeFound("isdefault.specified=false");
    }

    @Test
    @Transactional
    void getAllReasonsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where createdat equals to DEFAULT_CREATEDAT
        defaultReasonsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the reasonsList where createdat equals to UPDATED_CREATEDAT
        defaultReasonsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllReasonsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultReasonsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the reasonsList where createdat equals to UPDATED_CREATEDAT
        defaultReasonsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllReasonsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where createdat is not null
        defaultReasonsShouldBeFound("createdat.specified=true");

        // Get all the reasonsList where createdat is null
        defaultReasonsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllReasonsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultReasonsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the reasonsList where updatedat equals to UPDATED_UPDATEDAT
        defaultReasonsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllReasonsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultReasonsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the reasonsList where updatedat equals to UPDATED_UPDATEDAT
        defaultReasonsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllReasonsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        // Get all the reasonsList where updatedat is not null
        defaultReasonsShouldBeFound("updatedat.specified=true");

        // Get all the reasonsList where updatedat is null
        defaultReasonsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllReasonsByEmployeecompensationReasonIsEqualToSomething() throws Exception {
        EmployeeCompensation employeecompensationReason;
        if (TestUtil.findAll(em, EmployeeCompensation.class).isEmpty()) {
            reasonsRepository.saveAndFlush(reasons);
            employeecompensationReason = EmployeeCompensationResourceIT.createEntity(em);
        } else {
            employeecompensationReason = TestUtil.findAll(em, EmployeeCompensation.class).get(0);
        }
        em.persist(employeecompensationReason);
        em.flush();
        reasons.addEmployeecompensationReason(employeecompensationReason);
        reasonsRepository.saveAndFlush(reasons);
        Long employeecompensationReasonId = employeecompensationReason.getId();

        // Get all the reasonsList where employeecompensationReason equals to employeecompensationReasonId
        defaultReasonsShouldBeFound("employeecompensationReasonId.equals=" + employeecompensationReasonId);

        // Get all the reasonsList where employeecompensationReason equals to (employeecompensationReasonId + 1)
        defaultReasonsShouldNotBeFound("employeecompensationReasonId.equals=" + (employeecompensationReasonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReasonsShouldBeFound(String filter) throws Exception {
        restReasonsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reasons.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isdefault").value(hasItem(DEFAULT_ISDEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restReasonsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReasonsShouldNotBeFound(String filter) throws Exception {
        restReasonsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReasonsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingReasons() throws Exception {
        // Get the reasons
        restReasonsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReasons() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();

        // Update the reasons
        Reasons updatedReasons = reasonsRepository.findById(reasons.getId()).get();
        // Disconnect from session so that the updates on updatedReasons are not directly saved in db
        em.detach(updatedReasons);
        updatedReasons.name(UPDATED_NAME).isdefault(UPDATED_ISDEFAULT).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restReasonsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReasons.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReasons))
            )
            .andExpect(status().isOk());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
        Reasons testReasons = reasonsList.get(reasonsList.size() - 1);
        assertThat(testReasons.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReasons.getIsdefault()).isEqualTo(UPDATED_ISDEFAULT);
        assertThat(testReasons.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testReasons.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingReasons() throws Exception {
        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();
        reasons.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReasonsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reasons.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReasons() throws Exception {
        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();
        reasons.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReasonsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReasons() throws Exception {
        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();
        reasons.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReasonsMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReasonsWithPatch() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();

        // Update the reasons using partial update
        Reasons partialUpdatedReasons = new Reasons();
        partialUpdatedReasons.setId(reasons.getId());

        partialUpdatedReasons.name(UPDATED_NAME).isdefault(UPDATED_ISDEFAULT).updatedat(UPDATED_UPDATEDAT);

        restReasonsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReasons.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReasons))
            )
            .andExpect(status().isOk());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
        Reasons testReasons = reasonsList.get(reasonsList.size() - 1);
        assertThat(testReasons.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReasons.getIsdefault()).isEqualTo(UPDATED_ISDEFAULT);
        assertThat(testReasons.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testReasons.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateReasonsWithPatch() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();

        // Update the reasons using partial update
        Reasons partialUpdatedReasons = new Reasons();
        partialUpdatedReasons.setId(reasons.getId());

        partialUpdatedReasons.name(UPDATED_NAME).isdefault(UPDATED_ISDEFAULT).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restReasonsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReasons.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReasons))
            )
            .andExpect(status().isOk());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
        Reasons testReasons = reasonsList.get(reasonsList.size() - 1);
        assertThat(testReasons.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReasons.getIsdefault()).isEqualTo(UPDATED_ISDEFAULT);
        assertThat(testReasons.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testReasons.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingReasons() throws Exception {
        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();
        reasons.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReasonsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reasons.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReasons() throws Exception {
        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();
        reasons.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReasonsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReasons() throws Exception {
        int databaseSizeBeforeUpdate = reasonsRepository.findAll().size();
        reasons.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReasonsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reasons))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reasons in the database
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReasons() throws Exception {
        // Initialize the database
        reasonsRepository.saveAndFlush(reasons);

        int databaseSizeBeforeDelete = reasonsRepository.findAll().size();

        // Delete the reasons
        restReasonsMockMvc
            .perform(delete(ENTITY_API_URL_ID, reasons.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reasons> reasonsList = reasonsRepository.findAll();
        assertThat(reasonsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
