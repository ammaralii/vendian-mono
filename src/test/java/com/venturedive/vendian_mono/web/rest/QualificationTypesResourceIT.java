package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeEducation;
import com.venturedive.vendian_mono.domain.QualificationTypes;
import com.venturedive.vendian_mono.repository.QualificationTypesRepository;
import com.venturedive.vendian_mono.service.criteria.QualificationTypesCriteria;
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
 * Integration tests for the {@link QualificationTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QualificationTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/qualification-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QualificationTypesRepository qualificationTypesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQualificationTypesMockMvc;

    private QualificationTypes qualificationTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QualificationTypes createEntity(EntityManager em) {
        QualificationTypes qualificationTypes = new QualificationTypes()
            .name(DEFAULT_NAME)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return qualificationTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QualificationTypes createUpdatedEntity(EntityManager em) {
        QualificationTypes qualificationTypes = new QualificationTypes()
            .name(UPDATED_NAME)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return qualificationTypes;
    }

    @BeforeEach
    public void initTest() {
        qualificationTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createQualificationTypes() throws Exception {
        int databaseSizeBeforeCreate = qualificationTypesRepository.findAll().size();
        // Create the QualificationTypes
        restQualificationTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isCreated());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeCreate + 1);
        QualificationTypes testQualificationTypes = qualificationTypesList.get(qualificationTypesList.size() - 1);
        assertThat(testQualificationTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testQualificationTypes.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testQualificationTypes.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createQualificationTypesWithExistingId() throws Exception {
        // Create the QualificationTypes with an existing ID
        qualificationTypes.setId(1L);

        int databaseSizeBeforeCreate = qualificationTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualificationTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = qualificationTypesRepository.findAll().size();
        // set the field null
        qualificationTypes.setName(null);

        // Create the QualificationTypes, which fails.

        restQualificationTypesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isBadRequest());

        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQualificationTypes() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList
        restQualificationTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualificationTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getQualificationTypes() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get the qualificationTypes
        restQualificationTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, qualificationTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qualificationTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getQualificationTypesByIdFiltering() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        Long id = qualificationTypes.getId();

        defaultQualificationTypesShouldBeFound("id.equals=" + id);
        defaultQualificationTypesShouldNotBeFound("id.notEquals=" + id);

        defaultQualificationTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQualificationTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultQualificationTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQualificationTypesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where name equals to DEFAULT_NAME
        defaultQualificationTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the qualificationTypesList where name equals to UPDATED_NAME
        defaultQualificationTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultQualificationTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the qualificationTypesList where name equals to UPDATED_NAME
        defaultQualificationTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where name is not null
        defaultQualificationTypesShouldBeFound("name.specified=true");

        // Get all the qualificationTypesList where name is null
        defaultQualificationTypesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllQualificationTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where name contains DEFAULT_NAME
        defaultQualificationTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the qualificationTypesList where name contains UPDATED_NAME
        defaultQualificationTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where name does not contain DEFAULT_NAME
        defaultQualificationTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the qualificationTypesList where name does not contain UPDATED_NAME
        defaultQualificationTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where createdat equals to DEFAULT_CREATEDAT
        defaultQualificationTypesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the qualificationTypesList where createdat equals to UPDATED_CREATEDAT
        defaultQualificationTypesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultQualificationTypesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the qualificationTypesList where createdat equals to UPDATED_CREATEDAT
        defaultQualificationTypesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where createdat is not null
        defaultQualificationTypesShouldBeFound("createdat.specified=true");

        // Get all the qualificationTypesList where createdat is null
        defaultQualificationTypesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllQualificationTypesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultQualificationTypesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the qualificationTypesList where updatedat equals to UPDATED_UPDATEDAT
        defaultQualificationTypesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultQualificationTypesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the qualificationTypesList where updatedat equals to UPDATED_UPDATEDAT
        defaultQualificationTypesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllQualificationTypesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        // Get all the qualificationTypesList where updatedat is not null
        defaultQualificationTypesShouldBeFound("updatedat.specified=true");

        // Get all the qualificationTypesList where updatedat is null
        defaultQualificationTypesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllQualificationTypesByEmployeeeducationQualificationtypeIsEqualToSomething() throws Exception {
        EmployeeEducation employeeeducationQualificationtype;
        if (TestUtil.findAll(em, EmployeeEducation.class).isEmpty()) {
            qualificationTypesRepository.saveAndFlush(qualificationTypes);
            employeeeducationQualificationtype = EmployeeEducationResourceIT.createEntity(em);
        } else {
            employeeeducationQualificationtype = TestUtil.findAll(em, EmployeeEducation.class).get(0);
        }
        em.persist(employeeeducationQualificationtype);
        em.flush();
        qualificationTypes.addEmployeeeducationQualificationtype(employeeeducationQualificationtype);
        qualificationTypesRepository.saveAndFlush(qualificationTypes);
        Long employeeeducationQualificationtypeId = employeeeducationQualificationtype.getId();

        // Get all the qualificationTypesList where employeeeducationQualificationtype equals to employeeeducationQualificationtypeId
        defaultQualificationTypesShouldBeFound("employeeeducationQualificationtypeId.equals=" + employeeeducationQualificationtypeId);

        // Get all the qualificationTypesList where employeeeducationQualificationtype equals to (employeeeducationQualificationtypeId + 1)
        defaultQualificationTypesShouldNotBeFound(
            "employeeeducationQualificationtypeId.equals=" + (employeeeducationQualificationtypeId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQualificationTypesShouldBeFound(String filter) throws Exception {
        restQualificationTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualificationTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restQualificationTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQualificationTypesShouldNotBeFound(String filter) throws Exception {
        restQualificationTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQualificationTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQualificationTypes() throws Exception {
        // Get the qualificationTypes
        restQualificationTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQualificationTypes() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();

        // Update the qualificationTypes
        QualificationTypes updatedQualificationTypes = qualificationTypesRepository.findById(qualificationTypes.getId()).get();
        // Disconnect from session so that the updates on updatedQualificationTypes are not directly saved in db
        em.detach(updatedQualificationTypes);
        updatedQualificationTypes.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restQualificationTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQualificationTypes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQualificationTypes))
            )
            .andExpect(status().isOk());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
        QualificationTypes testQualificationTypes = qualificationTypesList.get(qualificationTypesList.size() - 1);
        assertThat(testQualificationTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQualificationTypes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQualificationTypes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingQualificationTypes() throws Exception {
        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();
        qualificationTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualificationTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qualificationTypes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQualificationTypes() throws Exception {
        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();
        qualificationTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQualificationTypes() throws Exception {
        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();
        qualificationTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationTypesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQualificationTypesWithPatch() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();

        // Update the qualificationTypes using partial update
        QualificationTypes partialUpdatedQualificationTypes = new QualificationTypes();
        partialUpdatedQualificationTypes.setId(qualificationTypes.getId());

        partialUpdatedQualificationTypes.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restQualificationTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQualificationTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQualificationTypes))
            )
            .andExpect(status().isOk());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
        QualificationTypes testQualificationTypes = qualificationTypesList.get(qualificationTypesList.size() - 1);
        assertThat(testQualificationTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQualificationTypes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQualificationTypes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateQualificationTypesWithPatch() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();

        // Update the qualificationTypes using partial update
        QualificationTypes partialUpdatedQualificationTypes = new QualificationTypes();
        partialUpdatedQualificationTypes.setId(qualificationTypes.getId());

        partialUpdatedQualificationTypes.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restQualificationTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQualificationTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQualificationTypes))
            )
            .andExpect(status().isOk());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
        QualificationTypes testQualificationTypes = qualificationTypesList.get(qualificationTypesList.size() - 1);
        assertThat(testQualificationTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQualificationTypes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testQualificationTypes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingQualificationTypes() throws Exception {
        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();
        qualificationTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualificationTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qualificationTypes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQualificationTypes() throws Exception {
        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();
        qualificationTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQualificationTypes() throws Exception {
        int databaseSizeBeforeUpdate = qualificationTypesRepository.findAll().size();
        qualificationTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationTypesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qualificationTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QualificationTypes in the database
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQualificationTypes() throws Exception {
        // Initialize the database
        qualificationTypesRepository.saveAndFlush(qualificationTypes);

        int databaseSizeBeforeDelete = qualificationTypesRepository.findAll().size();

        // Delete the qualificationTypes
        restQualificationTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, qualificationTypes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QualificationTypes> qualificationTypesList = qualificationTypesRepository.findAll();
        assertThat(qualificationTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
