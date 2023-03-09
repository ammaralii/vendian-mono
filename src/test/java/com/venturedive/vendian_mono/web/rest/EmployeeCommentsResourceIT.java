package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Documents;
import com.venturedive.vendian_mono.domain.EmployeeComments;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeCommentsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeCommentsCriteria;
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
 * Integration tests for the {@link EmployeeCommentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeCommentsResourceIT {

    private static final byte[] DEFAULT_TITLE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TITLE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TITLE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TITLE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DATED = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATED = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATED_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATED_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employee-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeCommentsRepository employeeCommentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeCommentsMockMvc;

    private EmployeeComments employeeComments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeComments createEntity(EntityManager em) {
        EmployeeComments employeeComments = new EmployeeComments()
            .title(DEFAULT_TITLE)
            .titleContentType(DEFAULT_TITLE_CONTENT_TYPE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .dated(DEFAULT_DATED)
            .datedContentType(DEFAULT_DATED_CONTENT_TYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeComments.setCommenter(employees);
        // Add required entity
        employeeComments.setEmployee(employees);
        return employeeComments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeComments createUpdatedEntity(EntityManager em) {
        EmployeeComments employeeComments = new EmployeeComments()
            .title(UPDATED_TITLE)
            .titleContentType(UPDATED_TITLE_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .dated(UPDATED_DATED)
            .datedContentType(UPDATED_DATED_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeComments.setCommenter(employees);
        // Add required entity
        employeeComments.setEmployee(employees);
        return employeeComments;
    }

    @BeforeEach
    public void initTest() {
        employeeComments = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeComments() throws Exception {
        int databaseSizeBeforeCreate = employeeCommentsRepository.findAll().size();
        // Create the EmployeeComments
        restEmployeeCommentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComments))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeComments testEmployeeComments = employeeCommentsList.get(employeeCommentsList.size() - 1);
        assertThat(testEmployeeComments.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmployeeComments.getTitleContentType()).isEqualTo(DEFAULT_TITLE_CONTENT_TYPE);
        assertThat(testEmployeeComments.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testEmployeeComments.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testEmployeeComments.getDated()).isEqualTo(DEFAULT_DATED);
        assertThat(testEmployeeComments.getDatedContentType()).isEqualTo(DEFAULT_DATED_CONTENT_TYPE);
        assertThat(testEmployeeComments.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeComments.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createEmployeeCommentsWithExistingId() throws Exception {
        // Create the EmployeeComments with an existing ID
        employeeComments.setId(1L);

        int databaseSizeBeforeCreate = employeeCommentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeCommentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeComments() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        // Get all the employeeCommentsList
        restEmployeeCommentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeComments.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleContentType").value(hasItem(DEFAULT_TITLE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(Base64Utils.encodeToString(DEFAULT_TITLE))))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].datedContentType").value(hasItem(DEFAULT_DATED_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dated").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATED))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getEmployeeComments() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        // Get the employeeComments
        restEmployeeCommentsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeComments.getId().intValue()))
            .andExpect(jsonPath("$.titleContentType").value(DEFAULT_TITLE_CONTENT_TYPE))
            .andExpect(jsonPath("$.title").value(Base64Utils.encodeToString(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.datedContentType").value(DEFAULT_DATED_CONTENT_TYPE))
            .andExpect(jsonPath("$.dated").value(Base64Utils.encodeToString(DEFAULT_DATED)))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getEmployeeCommentsByIdFiltering() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        Long id = employeeComments.getId();

        defaultEmployeeCommentsShouldBeFound("id.equals=" + id);
        defaultEmployeeCommentsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeCommentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeCommentsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeCommentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeCommentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        // Get all the employeeCommentsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeCommentsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeCommentsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeCommentsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        // Get all the employeeCommentsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeCommentsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeCommentsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeCommentsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        // Get all the employeeCommentsList where createdat is not null
        defaultEmployeeCommentsShouldBeFound("createdat.specified=true");

        // Get all the employeeCommentsList where createdat is null
        defaultEmployeeCommentsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        // Get all the employeeCommentsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeCommentsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeCommentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeCommentsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        // Get all the employeeCommentsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeCommentsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeCommentsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeCommentsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        // Get all the employeeCommentsList where updatedat is not null
        defaultEmployeeCommentsShouldBeFound("updatedat.specified=true");

        // Get all the employeeCommentsList where updatedat is null
        defaultEmployeeCommentsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByDocumentIsEqualToSomething() throws Exception {
        Documents document;
        if (TestUtil.findAll(em, Documents.class).isEmpty()) {
            employeeCommentsRepository.saveAndFlush(employeeComments);
            document = DocumentsResourceIT.createEntity(em);
        } else {
            document = TestUtil.findAll(em, Documents.class).get(0);
        }
        em.persist(document);
        em.flush();
        employeeComments.setDocument(document);
        employeeCommentsRepository.saveAndFlush(employeeComments);
        Long documentId = document.getId();

        // Get all the employeeCommentsList where document equals to documentId
        defaultEmployeeCommentsShouldBeFound("documentId.equals=" + documentId);

        // Get all the employeeCommentsList where document equals to (documentId + 1)
        defaultEmployeeCommentsShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByCommenterIsEqualToSomething() throws Exception {
        Employees commenter;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeCommentsRepository.saveAndFlush(employeeComments);
            commenter = EmployeesResourceIT.createEntity(em);
        } else {
            commenter = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(commenter);
        em.flush();
        employeeComments.setCommenter(commenter);
        employeeCommentsRepository.saveAndFlush(employeeComments);
        Long commenterId = commenter.getId();

        // Get all the employeeCommentsList where commenter equals to commenterId
        defaultEmployeeCommentsShouldBeFound("commenterId.equals=" + commenterId);

        // Get all the employeeCommentsList where commenter equals to (commenterId + 1)
        defaultEmployeeCommentsShouldNotBeFound("commenterId.equals=" + (commenterId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeCommentsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeCommentsRepository.saveAndFlush(employeeComments);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeComments.setEmployee(employee);
        employeeCommentsRepository.saveAndFlush(employeeComments);
        Long employeeId = employee.getId();

        // Get all the employeeCommentsList where employee equals to employeeId
        defaultEmployeeCommentsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeCommentsList where employee equals to (employeeId + 1)
        defaultEmployeeCommentsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeCommentsShouldBeFound(String filter) throws Exception {
        restEmployeeCommentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeComments.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleContentType").value(hasItem(DEFAULT_TITLE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(Base64Utils.encodeToString(DEFAULT_TITLE))))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].datedContentType").value(hasItem(DEFAULT_DATED_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dated").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATED))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restEmployeeCommentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeCommentsShouldNotBeFound(String filter) throws Exception {
        restEmployeeCommentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeCommentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeComments() throws Exception {
        // Get the employeeComments
        restEmployeeCommentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeComments() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();

        // Update the employeeComments
        EmployeeComments updatedEmployeeComments = employeeCommentsRepository.findById(employeeComments.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeComments are not directly saved in db
        em.detach(updatedEmployeeComments);
        updatedEmployeeComments
            .title(UPDATED_TITLE)
            .titleContentType(UPDATED_TITLE_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .dated(UPDATED_DATED)
            .datedContentType(UPDATED_DATED_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeComments.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeComments))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeComments testEmployeeComments = employeeCommentsList.get(employeeCommentsList.size() - 1);
        assertThat(testEmployeeComments.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployeeComments.getTitleContentType()).isEqualTo(UPDATED_TITLE_CONTENT_TYPE);
        assertThat(testEmployeeComments.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testEmployeeComments.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testEmployeeComments.getDated()).isEqualTo(UPDATED_DATED);
        assertThat(testEmployeeComments.getDatedContentType()).isEqualTo(UPDATED_DATED_CONTENT_TYPE);
        assertThat(testEmployeeComments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeComments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeComments() throws Exception {
        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();
        employeeComments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeComments.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeComments() throws Exception {
        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();
        employeeComments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeComments() throws Exception {
        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();
        employeeComments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCommentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeCommentsWithPatch() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();

        // Update the employeeComments using partial update
        EmployeeComments partialUpdatedEmployeeComments = new EmployeeComments();
        partialUpdatedEmployeeComments.setId(employeeComments.getId());

        partialUpdatedEmployeeComments
            .title(UPDATED_TITLE)
            .titleContentType(UPDATED_TITLE_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeComments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeComments))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeComments testEmployeeComments = employeeCommentsList.get(employeeCommentsList.size() - 1);
        assertThat(testEmployeeComments.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployeeComments.getTitleContentType()).isEqualTo(UPDATED_TITLE_CONTENT_TYPE);
        assertThat(testEmployeeComments.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testEmployeeComments.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testEmployeeComments.getDated()).isEqualTo(DEFAULT_DATED);
        assertThat(testEmployeeComments.getDatedContentType()).isEqualTo(DEFAULT_DATED_CONTENT_TYPE);
        assertThat(testEmployeeComments.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeComments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeCommentsWithPatch() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();

        // Update the employeeComments using partial update
        EmployeeComments partialUpdatedEmployeeComments = new EmployeeComments();
        partialUpdatedEmployeeComments.setId(employeeComments.getId());

        partialUpdatedEmployeeComments
            .title(UPDATED_TITLE)
            .titleContentType(UPDATED_TITLE_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .dated(UPDATED_DATED)
            .datedContentType(UPDATED_DATED_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restEmployeeCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeComments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeComments))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeComments testEmployeeComments = employeeCommentsList.get(employeeCommentsList.size() - 1);
        assertThat(testEmployeeComments.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployeeComments.getTitleContentType()).isEqualTo(UPDATED_TITLE_CONTENT_TYPE);
        assertThat(testEmployeeComments.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testEmployeeComments.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testEmployeeComments.getDated()).isEqualTo(UPDATED_DATED);
        assertThat(testEmployeeComments.getDatedContentType()).isEqualTo(UPDATED_DATED_CONTENT_TYPE);
        assertThat(testEmployeeComments.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeComments.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeComments() throws Exception {
        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();
        employeeComments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeComments.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeComments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeComments() throws Exception {
        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();
        employeeComments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeComments))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeComments() throws Exception {
        int databaseSizeBeforeUpdate = employeeCommentsRepository.findAll().size();
        employeeComments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeComments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeComments in the database
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeComments() throws Exception {
        // Initialize the database
        employeeCommentsRepository.saveAndFlush(employeeComments);

        int databaseSizeBeforeDelete = employeeCommentsRepository.findAll().size();

        // Delete the employeeComments
        restEmployeeCommentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeComments.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeComments> employeeCommentsList = employeeCommentsRepository.findAll();
        assertThat(employeeCommentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
