package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DesignationJobDescriptions;
import com.venturedive.vendian_mono.domain.Designations;
import com.venturedive.vendian_mono.domain.Documents;
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.repository.DesignationJobDescriptionsRepository;
import com.venturedive.vendian_mono.service.criteria.DesignationJobDescriptionsCriteria;
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
 * Integration tests for the {@link DesignationJobDescriptionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DesignationJobDescriptionsResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/designation-job-descriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DesignationJobDescriptionsRepository designationJobDescriptionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesignationJobDescriptionsMockMvc;

    private DesignationJobDescriptions designationJobDescriptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DesignationJobDescriptions createEntity(EntityManager em) {
        DesignationJobDescriptions designationJobDescriptions = new DesignationJobDescriptions()
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        // Add required entity
        Documents documents;
        if (TestUtil.findAll(em, Documents.class).isEmpty()) {
            documents = DocumentsResourceIT.createEntity(em);
            em.persist(documents);
            em.flush();
        } else {
            documents = TestUtil.findAll(em, Documents.class).get(0);
        }
        designationJobDescriptions.setDocument(documents);
        // Add required entity
        Designations designations;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            designations = DesignationsResourceIT.createEntity(em);
            em.persist(designations);
            em.flush();
        } else {
            designations = TestUtil.findAll(em, Designations.class).get(0);
        }
        designationJobDescriptions.setDesignation(designations);
        return designationJobDescriptions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DesignationJobDescriptions createUpdatedEntity(EntityManager em) {
        DesignationJobDescriptions designationJobDescriptions = new DesignationJobDescriptions()
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        // Add required entity
        Documents documents;
        if (TestUtil.findAll(em, Documents.class).isEmpty()) {
            documents = DocumentsResourceIT.createUpdatedEntity(em);
            em.persist(documents);
            em.flush();
        } else {
            documents = TestUtil.findAll(em, Documents.class).get(0);
        }
        designationJobDescriptions.setDocument(documents);
        // Add required entity
        Designations designations;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            designations = DesignationsResourceIT.createUpdatedEntity(em);
            em.persist(designations);
            em.flush();
        } else {
            designations = TestUtil.findAll(em, Designations.class).get(0);
        }
        designationJobDescriptions.setDesignation(designations);
        return designationJobDescriptions;
    }

    @BeforeEach
    public void initTest() {
        designationJobDescriptions = createEntity(em);
    }

    @Test
    @Transactional
    void createDesignationJobDescriptions() throws Exception {
        int databaseSizeBeforeCreate = designationJobDescriptionsRepository.findAll().size();
        // Create the DesignationJobDescriptions
        restDesignationJobDescriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationJobDescriptions))
            )
            .andExpect(status().isCreated());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeCreate + 1);
        DesignationJobDescriptions testDesignationJobDescriptions = designationJobDescriptionsList.get(
            designationJobDescriptionsList.size() - 1
        );
        assertThat(testDesignationJobDescriptions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDesignationJobDescriptions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createDesignationJobDescriptionsWithExistingId() throws Exception {
        // Create the DesignationJobDescriptions with an existing ID
        designationJobDescriptions.setId(1L);

        int databaseSizeBeforeCreate = designationJobDescriptionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationJobDescriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationJobDescriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptions() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        // Get all the designationJobDescriptionsList
        restDesignationJobDescriptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationJobDescriptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getDesignationJobDescriptions() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        // Get the designationJobDescriptions
        restDesignationJobDescriptionsMockMvc
            .perform(get(ENTITY_API_URL_ID, designationJobDescriptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(designationJobDescriptions.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getDesignationJobDescriptionsByIdFiltering() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        Long id = designationJobDescriptions.getId();

        defaultDesignationJobDescriptionsShouldBeFound("id.equals=" + id);
        defaultDesignationJobDescriptionsShouldNotBeFound("id.notEquals=" + id);

        defaultDesignationJobDescriptionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDesignationJobDescriptionsShouldNotBeFound("id.greaterThan=" + id);

        defaultDesignationJobDescriptionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDesignationJobDescriptionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        // Get all the designationJobDescriptionsList where createdat equals to DEFAULT_CREATEDAT
        defaultDesignationJobDescriptionsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the designationJobDescriptionsList where createdat equals to UPDATED_CREATEDAT
        defaultDesignationJobDescriptionsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        // Get all the designationJobDescriptionsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDesignationJobDescriptionsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the designationJobDescriptionsList where createdat equals to UPDATED_CREATEDAT
        defaultDesignationJobDescriptionsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        // Get all the designationJobDescriptionsList where createdat is not null
        defaultDesignationJobDescriptionsShouldBeFound("createdat.specified=true");

        // Get all the designationJobDescriptionsList where createdat is null
        defaultDesignationJobDescriptionsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        // Get all the designationJobDescriptionsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDesignationJobDescriptionsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the designationJobDescriptionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDesignationJobDescriptionsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        // Get all the designationJobDescriptionsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDesignationJobDescriptionsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the designationJobDescriptionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDesignationJobDescriptionsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        // Get all the designationJobDescriptionsList where updatedat is not null
        defaultDesignationJobDescriptionsShouldBeFound("updatedat.specified=true");

        // Get all the designationJobDescriptionsList where updatedat is null
        defaultDesignationJobDescriptionsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByDocumentIsEqualToSomething() throws Exception {
        Documents document;
        if (TestUtil.findAll(em, Documents.class).isEmpty()) {
            designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);
            document = DocumentsResourceIT.createEntity(em);
        } else {
            document = TestUtil.findAll(em, Documents.class).get(0);
        }
        em.persist(document);
        em.flush();
        designationJobDescriptions.setDocument(document);
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);
        Long documentId = document.getId();

        // Get all the designationJobDescriptionsList where document equals to documentId
        defaultDesignationJobDescriptionsShouldBeFound("documentId.equals=" + documentId);

        // Get all the designationJobDescriptionsList where document equals to (documentId + 1)
        defaultDesignationJobDescriptionsShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByDesignationIsEqualToSomething() throws Exception {
        Designations designation;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);
            designation = DesignationsResourceIT.createEntity(em);
        } else {
            designation = TestUtil.findAll(em, Designations.class).get(0);
        }
        em.persist(designation);
        em.flush();
        designationJobDescriptions.setDesignation(designation);
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);
        Long designationId = designation.getId();

        // Get all the designationJobDescriptionsList where designation equals to designationId
        defaultDesignationJobDescriptionsShouldBeFound("designationId.equals=" + designationId);

        // Get all the designationJobDescriptionsList where designation equals to (designationId + 1)
        defaultDesignationJobDescriptionsShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }

    @Test
    @Transactional
    void getAllDesignationJobDescriptionsByEmployeejobinfoJobdescriptionIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoJobdescription;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);
            employeejobinfoJobdescription = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoJobdescription = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoJobdescription);
        em.flush();
        designationJobDescriptions.addEmployeejobinfoJobdescription(employeejobinfoJobdescription);
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);
        Long employeejobinfoJobdescriptionId = employeejobinfoJobdescription.getId();

        // Get all the designationJobDescriptionsList where employeejobinfoJobdescription equals to employeejobinfoJobdescriptionId
        defaultDesignationJobDescriptionsShouldBeFound("employeejobinfoJobdescriptionId.equals=" + employeejobinfoJobdescriptionId);

        // Get all the designationJobDescriptionsList where employeejobinfoJobdescription equals to (employeejobinfoJobdescriptionId + 1)
        defaultDesignationJobDescriptionsShouldNotBeFound(
            "employeejobinfoJobdescriptionId.equals=" + (employeejobinfoJobdescriptionId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDesignationJobDescriptionsShouldBeFound(String filter) throws Exception {
        restDesignationJobDescriptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationJobDescriptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restDesignationJobDescriptionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDesignationJobDescriptionsShouldNotBeFound(String filter) throws Exception {
        restDesignationJobDescriptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDesignationJobDescriptionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDesignationJobDescriptions() throws Exception {
        // Get the designationJobDescriptions
        restDesignationJobDescriptionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDesignationJobDescriptions() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();

        // Update the designationJobDescriptions
        DesignationJobDescriptions updatedDesignationJobDescriptions = designationJobDescriptionsRepository
            .findById(designationJobDescriptions.getId())
            .get();
        // Disconnect from session so that the updates on updatedDesignationJobDescriptions are not directly saved in db
        em.detach(updatedDesignationJobDescriptions);
        updatedDesignationJobDescriptions.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restDesignationJobDescriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDesignationJobDescriptions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDesignationJobDescriptions))
            )
            .andExpect(status().isOk());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
        DesignationJobDescriptions testDesignationJobDescriptions = designationJobDescriptionsList.get(
            designationJobDescriptionsList.size() - 1
        );
        assertThat(testDesignationJobDescriptions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDesignationJobDescriptions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDesignationJobDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();
        designationJobDescriptions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationJobDescriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, designationJobDescriptions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationJobDescriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDesignationJobDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();
        designationJobDescriptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationJobDescriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationJobDescriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDesignationJobDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();
        designationJobDescriptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationJobDescriptionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationJobDescriptions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDesignationJobDescriptionsWithPatch() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();

        // Update the designationJobDescriptions using partial update
        DesignationJobDescriptions partialUpdatedDesignationJobDescriptions = new DesignationJobDescriptions();
        partialUpdatedDesignationJobDescriptions.setId(designationJobDescriptions.getId());

        partialUpdatedDesignationJobDescriptions.createdat(UPDATED_CREATEDAT);

        restDesignationJobDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignationJobDescriptions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignationJobDescriptions))
            )
            .andExpect(status().isOk());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
        DesignationJobDescriptions testDesignationJobDescriptions = designationJobDescriptionsList.get(
            designationJobDescriptionsList.size() - 1
        );
        assertThat(testDesignationJobDescriptions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDesignationJobDescriptions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDesignationJobDescriptionsWithPatch() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();

        // Update the designationJobDescriptions using partial update
        DesignationJobDescriptions partialUpdatedDesignationJobDescriptions = new DesignationJobDescriptions();
        partialUpdatedDesignationJobDescriptions.setId(designationJobDescriptions.getId());

        partialUpdatedDesignationJobDescriptions.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restDesignationJobDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignationJobDescriptions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignationJobDescriptions))
            )
            .andExpect(status().isOk());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
        DesignationJobDescriptions testDesignationJobDescriptions = designationJobDescriptionsList.get(
            designationJobDescriptionsList.size() - 1
        );
        assertThat(testDesignationJobDescriptions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDesignationJobDescriptions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDesignationJobDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();
        designationJobDescriptions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationJobDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, designationJobDescriptions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationJobDescriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDesignationJobDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();
        designationJobDescriptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationJobDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationJobDescriptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDesignationJobDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = designationJobDescriptionsRepository.findAll().size();
        designationJobDescriptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationJobDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationJobDescriptions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DesignationJobDescriptions in the database
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDesignationJobDescriptions() throws Exception {
        // Initialize the database
        designationJobDescriptionsRepository.saveAndFlush(designationJobDescriptions);

        int databaseSizeBeforeDelete = designationJobDescriptionsRepository.findAll().size();

        // Delete the designationJobDescriptions
        restDesignationJobDescriptionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, designationJobDescriptions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DesignationJobDescriptions> designationJobDescriptionsList = designationJobDescriptionsRepository.findAll();
        assertThat(designationJobDescriptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
