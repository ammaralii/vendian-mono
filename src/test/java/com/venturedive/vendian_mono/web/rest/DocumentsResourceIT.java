package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DesignationJobDescriptions;
import com.venturedive.vendian_mono.domain.Documents;
import com.venturedive.vendian_mono.domain.EmployeeComments;
import com.venturedive.vendian_mono.domain.EmployeeDocuments;
import com.venturedive.vendian_mono.repository.DocumentsRepository;
import com.venturedive.vendian_mono.service.criteria.DocumentsCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DocumentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentsResourceIT {

    private static final byte[] DEFAULT_NAME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NAME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NAME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NAME_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentsMockMvc;

    private Documents documents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documents createEntity(EntityManager em) {
        Documents documents = new Documents()
            .name(DEFAULT_NAME)
            .nameContentType(DEFAULT_NAME_CONTENT_TYPE)
            .url(DEFAULT_URL)
            .urlContentType(DEFAULT_URL_CONTENT_TYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return documents;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documents createUpdatedEntity(EntityManager em) {
        Documents documents = new Documents()
            .name(UPDATED_NAME)
            .nameContentType(UPDATED_NAME_CONTENT_TYPE)
            .url(UPDATED_URL)
            .urlContentType(UPDATED_URL_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return documents;
    }

    @BeforeEach
    public void initTest() {
        documents = createEntity(em);
    }

    @Test
    @Transactional
    void createDocuments() throws Exception {
        int databaseSizeBeforeCreate = documentsRepository.findAll().size();
        // Create the Documents
        restDocumentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documents))
            )
            .andExpect(status().isCreated());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeCreate + 1);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocuments.getNameContentType()).isEqualTo(DEFAULT_NAME_CONTENT_TYPE);
        assertThat(testDocuments.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testDocuments.getUrlContentType()).isEqualTo(DEFAULT_URL_CONTENT_TYPE);
        assertThat(testDocuments.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDocuments.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createDocumentsWithExistingId() throws Exception {
        // Create the Documents with an existing ID
        documents.setId(1L);

        int databaseSizeBeforeCreate = documentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documents))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documents.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameContentType").value(hasItem(DEFAULT_NAME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(Base64Utils.encodeToString(DEFAULT_NAME))))
            .andExpect(jsonPath("$.[*].urlContentType").value(hasItem(DEFAULT_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get the documents
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL_ID, documents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documents.getId().intValue()))
            .andExpect(jsonPath("$.nameContentType").value(DEFAULT_NAME_CONTENT_TYPE))
            .andExpect(jsonPath("$.name").value(Base64Utils.encodeToString(DEFAULT_NAME)))
            .andExpect(jsonPath("$.urlContentType").value(DEFAULT_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.url").value(Base64Utils.encodeToString(DEFAULT_URL)))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        Long id = documents.getId();

        defaultDocumentsShouldBeFound("id.equals=" + id);
        defaultDocumentsShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentsShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdat equals to DEFAULT_CREATEDAT
        defaultDocumentsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the documentsList where createdat equals to UPDATED_CREATEDAT
        defaultDocumentsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDocumentsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the documentsList where createdat equals to UPDATED_CREATEDAT
        defaultDocumentsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdat is not null
        defaultDocumentsShouldBeFound("createdat.specified=true");

        // Get all the documentsList where createdat is null
        defaultDocumentsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDocumentsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the documentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDocumentsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDocumentsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDocumentsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the documentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDocumentsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDocumentsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where updatedat is not null
        defaultDocumentsShouldBeFound("updatedat.specified=true");

        // Get all the documentsList where updatedat is null
        defaultDocumentsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByDesignationjobdescriptionsDocumentIsEqualToSomething() throws Exception {
        DesignationJobDescriptions designationjobdescriptionsDocument;
        if (TestUtil.findAll(em, DesignationJobDescriptions.class).isEmpty()) {
            documentsRepository.saveAndFlush(documents);
            designationjobdescriptionsDocument = DesignationJobDescriptionsResourceIT.createEntity(em);
        } else {
            designationjobdescriptionsDocument = TestUtil.findAll(em, DesignationJobDescriptions.class).get(0);
        }
        em.persist(designationjobdescriptionsDocument);
        em.flush();
        documents.addDesignationjobdescriptionsDocument(designationjobdescriptionsDocument);
        documentsRepository.saveAndFlush(documents);
        Long designationjobdescriptionsDocumentId = designationjobdescriptionsDocument.getId();

        // Get all the documentsList where designationjobdescriptionsDocument equals to designationjobdescriptionsDocumentId
        defaultDocumentsShouldBeFound("designationjobdescriptionsDocumentId.equals=" + designationjobdescriptionsDocumentId);

        // Get all the documentsList where designationjobdescriptionsDocument equals to (designationjobdescriptionsDocumentId + 1)
        defaultDocumentsShouldNotBeFound("designationjobdescriptionsDocumentId.equals=" + (designationjobdescriptionsDocumentId + 1));
    }

    @Test
    @Transactional
    void getAllDocumentsByEmployeecommentsDocumentIsEqualToSomething() throws Exception {
        EmployeeComments employeecommentsDocument;
        if (TestUtil.findAll(em, EmployeeComments.class).isEmpty()) {
            documentsRepository.saveAndFlush(documents);
            employeecommentsDocument = EmployeeCommentsResourceIT.createEntity(em);
        } else {
            employeecommentsDocument = TestUtil.findAll(em, EmployeeComments.class).get(0);
        }
        em.persist(employeecommentsDocument);
        em.flush();
        documents.addEmployeecommentsDocument(employeecommentsDocument);
        documentsRepository.saveAndFlush(documents);
        Long employeecommentsDocumentId = employeecommentsDocument.getId();

        // Get all the documentsList where employeecommentsDocument equals to employeecommentsDocumentId
        defaultDocumentsShouldBeFound("employeecommentsDocumentId.equals=" + employeecommentsDocumentId);

        // Get all the documentsList where employeecommentsDocument equals to (employeecommentsDocumentId + 1)
        defaultDocumentsShouldNotBeFound("employeecommentsDocumentId.equals=" + (employeecommentsDocumentId + 1));
    }

    @Test
    @Transactional
    void getAllDocumentsByEmployeedocumentsDocumentIsEqualToSomething() throws Exception {
        EmployeeDocuments employeedocumentsDocument;
        if (TestUtil.findAll(em, EmployeeDocuments.class).isEmpty()) {
            documentsRepository.saveAndFlush(documents);
            employeedocumentsDocument = EmployeeDocumentsResourceIT.createEntity(em);
        } else {
            employeedocumentsDocument = TestUtil.findAll(em, EmployeeDocuments.class).get(0);
        }
        em.persist(employeedocumentsDocument);
        em.flush();
        documents.addEmployeedocumentsDocument(employeedocumentsDocument);
        documentsRepository.saveAndFlush(documents);
        Long employeedocumentsDocumentId = employeedocumentsDocument.getId();

        // Get all the documentsList where employeedocumentsDocument equals to employeedocumentsDocumentId
        defaultDocumentsShouldBeFound("employeedocumentsDocumentId.equals=" + employeedocumentsDocumentId);

        // Get all the documentsList where employeedocumentsDocument equals to (employeedocumentsDocumentId + 1)
        defaultDocumentsShouldNotBeFound("employeedocumentsDocumentId.equals=" + (employeedocumentsDocumentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentsShouldBeFound(String filter) throws Exception {
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documents.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameContentType").value(hasItem(DEFAULT_NAME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(Base64Utils.encodeToString(DEFAULT_NAME))))
            .andExpect(jsonPath("$.[*].urlContentType").value(hasItem(DEFAULT_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentsShouldNotBeFound(String filter) throws Exception {
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocuments() throws Exception {
        // Get the documents
        restDocumentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();

        // Update the documents
        Documents updatedDocuments = documentsRepository.findById(documents.getId()).get();
        // Disconnect from session so that the updates on updatedDocuments are not directly saved in db
        em.detach(updatedDocuments);
        updatedDocuments
            .name(UPDATED_NAME)
            .nameContentType(UPDATED_NAME_CONTENT_TYPE)
            .url(UPDATED_URL)
            .urlContentType(UPDATED_URL_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocuments.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocuments))
            )
            .andExpect(status().isOk());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocuments.getNameContentType()).isEqualTo(UPDATED_NAME_CONTENT_TYPE);
        assertThat(testDocuments.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testDocuments.getUrlContentType()).isEqualTo(UPDATED_URL_CONTENT_TYPE);
        assertThat(testDocuments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDocuments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documents))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documents))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentsWithPatch() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();

        // Update the documents using partial update
        Documents partialUpdatedDocuments = new Documents();
        partialUpdatedDocuments.setId(documents.getId());

        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocuments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocuments))
            )
            .andExpect(status().isOk());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocuments.getNameContentType()).isEqualTo(DEFAULT_NAME_CONTENT_TYPE);
        assertThat(testDocuments.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testDocuments.getUrlContentType()).isEqualTo(DEFAULT_URL_CONTENT_TYPE);
        assertThat(testDocuments.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDocuments.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDocumentsWithPatch() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();

        // Update the documents using partial update
        Documents partialUpdatedDocuments = new Documents();
        partialUpdatedDocuments.setId(documents.getId());

        partialUpdatedDocuments
            .name(UPDATED_NAME)
            .nameContentType(UPDATED_NAME_CONTENT_TYPE)
            .url(UPDATED_URL)
            .urlContentType(UPDATED_URL_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocuments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocuments))
            )
            .andExpect(status().isOk());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocuments.getNameContentType()).isEqualTo(UPDATED_NAME_CONTENT_TYPE);
        assertThat(testDocuments.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testDocuments.getUrlContentType()).isEqualTo(UPDATED_URL_CONTENT_TYPE);
        assertThat(testDocuments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDocuments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documents))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documents))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeDelete = documentsRepository.findAll().size();

        // Delete the documents
        restDocumentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, documents.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
