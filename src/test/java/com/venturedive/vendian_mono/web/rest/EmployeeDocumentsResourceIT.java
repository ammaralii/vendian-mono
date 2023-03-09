package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Documents;
import com.venturedive.vendian_mono.domain.EmployeeDocuments;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeDocumentsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeDocumentsCriteria;
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
 * Integration tests for the {@link EmployeeDocumentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeDocumentsResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeDocumentsRepository employeeDocumentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeDocumentsMockMvc;

    private EmployeeDocuments employeeDocuments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDocuments createEntity(EntityManager em) {
        EmployeeDocuments employeeDocuments = new EmployeeDocuments().createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT);
        // Add required entity
        Documents documents;
        if (TestUtil.findAll(em, Documents.class).isEmpty()) {
            documents = DocumentsResourceIT.createEntity(em);
            em.persist(documents);
            em.flush();
        } else {
            documents = TestUtil.findAll(em, Documents.class).get(0);
        }
        employeeDocuments.setDocument(documents);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeDocuments.setEmployee(employees);
        return employeeDocuments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDocuments createUpdatedEntity(EntityManager em) {
        EmployeeDocuments employeeDocuments = new EmployeeDocuments().createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);
        // Add required entity
        Documents documents;
        if (TestUtil.findAll(em, Documents.class).isEmpty()) {
            documents = DocumentsResourceIT.createUpdatedEntity(em);
            em.persist(documents);
            em.flush();
        } else {
            documents = TestUtil.findAll(em, Documents.class).get(0);
        }
        employeeDocuments.setDocument(documents);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeDocuments.setEmployee(employees);
        return employeeDocuments;
    }

    @BeforeEach
    public void initTest() {
        employeeDocuments = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeDocuments() throws Exception {
        int databaseSizeBeforeCreate = employeeDocumentsRepository.findAll().size();
        // Create the EmployeeDocuments
        restEmployeeDocumentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDocuments))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDocuments testEmployeeDocuments = employeeDocumentsList.get(employeeDocumentsList.size() - 1);
        assertThat(testEmployeeDocuments.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeDocuments.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeDocumentsWithExistingId() throws Exception {
        // Create the EmployeeDocuments with an existing ID
        employeeDocuments.setId(1L);

        int databaseSizeBeforeCreate = employeeDocumentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDocumentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeDocuments() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        // Get all the employeeDocumentsList
        restEmployeeDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeDocuments() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        // Get the employeeDocuments
        restEmployeeDocumentsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDocuments.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        Long id = employeeDocuments.getId();

        defaultEmployeeDocumentsShouldBeFound("id.equals=" + id);
        defaultEmployeeDocumentsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeDocumentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeDocumentsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeDocumentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeDocumentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeDocumentsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        // Get all the employeeDocumentsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeDocumentsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeDocumentsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeDocumentsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeDocumentsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        // Get all the employeeDocumentsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeDocumentsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeDocumentsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeDocumentsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeDocumentsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        // Get all the employeeDocumentsList where createdat is not null
        defaultEmployeeDocumentsShouldBeFound("createdat.specified=true");

        // Get all the employeeDocumentsList where createdat is null
        defaultEmployeeDocumentsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDocumentsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        // Get all the employeeDocumentsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeDocumentsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeDocumentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeDocumentsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeDocumentsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        // Get all the employeeDocumentsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeDocumentsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeDocumentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeDocumentsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeDocumentsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        // Get all the employeeDocumentsList where updatedat is not null
        defaultEmployeeDocumentsShouldBeFound("updatedat.specified=true");

        // Get all the employeeDocumentsList where updatedat is null
        defaultEmployeeDocumentsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDocumentsByDocumentIsEqualToSomething() throws Exception {
        Documents document;
        if (TestUtil.findAll(em, Documents.class).isEmpty()) {
            employeeDocumentsRepository.saveAndFlush(employeeDocuments);
            document = DocumentsResourceIT.createEntity(em);
        } else {
            document = TestUtil.findAll(em, Documents.class).get(0);
        }
        em.persist(document);
        em.flush();
        employeeDocuments.setDocument(document);
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);
        Long documentId = document.getId();

        // Get all the employeeDocumentsList where document equals to documentId
        defaultEmployeeDocumentsShouldBeFound("documentId.equals=" + documentId);

        // Get all the employeeDocumentsList where document equals to (documentId + 1)
        defaultEmployeeDocumentsShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeDocumentsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeDocumentsRepository.saveAndFlush(employeeDocuments);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeDocuments.setEmployee(employee);
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);
        Long employeeId = employee.getId();

        // Get all the employeeDocumentsList where employee equals to employeeId
        defaultEmployeeDocumentsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeDocumentsList where employee equals to (employeeId + 1)
        defaultEmployeeDocumentsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeDocumentsShouldBeFound(String filter) throws Exception {
        restEmployeeDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeDocumentsShouldNotBeFound(String filter) throws Exception {
        restEmployeeDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeDocuments() throws Exception {
        // Get the employeeDocuments
        restEmployeeDocumentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeDocuments() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();

        // Update the employeeDocuments
        EmployeeDocuments updatedEmployeeDocuments = employeeDocumentsRepository.findById(employeeDocuments.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDocuments are not directly saved in db
        em.detach(updatedEmployeeDocuments);
        updatedEmployeeDocuments.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restEmployeeDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeDocuments.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeDocuments))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDocuments testEmployeeDocuments = employeeDocumentsList.get(employeeDocumentsList.size() - 1);
        assertThat(testEmployeeDocuments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeDocuments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeDocuments() throws Exception {
        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();
        employeeDocuments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDocuments.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeDocuments() throws Exception {
        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();
        employeeDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeDocuments() throws Exception {
        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();
        employeeDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDocuments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeDocumentsWithPatch() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();

        // Update the employeeDocuments using partial update
        EmployeeDocuments partialUpdatedEmployeeDocuments = new EmployeeDocuments();
        partialUpdatedEmployeeDocuments.setId(employeeDocuments.getId());

        restEmployeeDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeDocuments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeDocuments))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDocuments testEmployeeDocuments = employeeDocumentsList.get(employeeDocumentsList.size() - 1);
        assertThat(testEmployeeDocuments.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeDocuments.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeDocumentsWithPatch() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();

        // Update the employeeDocuments using partial update
        EmployeeDocuments partialUpdatedEmployeeDocuments = new EmployeeDocuments();
        partialUpdatedEmployeeDocuments.setId(employeeDocuments.getId());

        partialUpdatedEmployeeDocuments.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restEmployeeDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeDocuments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeDocuments))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDocuments testEmployeeDocuments = employeeDocumentsList.get(employeeDocumentsList.size() - 1);
        assertThat(testEmployeeDocuments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeDocuments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeDocuments() throws Exception {
        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();
        employeeDocuments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeDocuments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeDocuments() throws Exception {
        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();
        employeeDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeDocuments() throws Exception {
        int databaseSizeBeforeUpdate = employeeDocumentsRepository.findAll().size();
        employeeDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDocuments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeDocuments in the database
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeDocuments() throws Exception {
        // Initialize the database
        employeeDocumentsRepository.saveAndFlush(employeeDocuments);

        int databaseSizeBeforeDelete = employeeDocumentsRepository.findAll().size();

        // Delete the employeeDocuments
        restEmployeeDocumentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeDocuments.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDocuments> employeeDocumentsList = employeeDocumentsRepository.findAll();
        assertThat(employeeDocumentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
