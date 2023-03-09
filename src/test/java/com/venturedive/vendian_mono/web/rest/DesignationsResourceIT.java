package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DesignationJobDescriptions;
import com.venturedive.vendian_mono.domain.Designations;
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.UserRatingsRemarks;
import com.venturedive.vendian_mono.repository.DesignationsRepository;
import com.venturedive.vendian_mono.service.criteria.DesignationsCriteria;
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
 * Integration tests for the {@link DesignationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DesignationsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/designations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DesignationsRepository designationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesignationsMockMvc;

    private Designations designations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designations createEntity(EntityManager em) {
        Designations designations = new Designations()
            .name(DEFAULT_NAME)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return designations;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designations createUpdatedEntity(EntityManager em) {
        Designations designations = new Designations()
            .name(UPDATED_NAME)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return designations;
    }

    @BeforeEach
    public void initTest() {
        designations = createEntity(em);
    }

    @Test
    @Transactional
    void createDesignations() throws Exception {
        int databaseSizeBeforeCreate = designationsRepository.findAll().size();
        // Create the Designations
        restDesignationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isCreated());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeCreate + 1);
        Designations testDesignations = designationsList.get(designationsList.size() - 1);
        assertThat(testDesignations.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDesignations.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDesignations.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDesignations.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createDesignationsWithExistingId() throws Exception {
        // Create the Designations with an existing ID
        designations.setId(1L);

        int databaseSizeBeforeCreate = designationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationsRepository.findAll().size();
        // set the field null
        designations.setName(null);

        // Create the Designations, which fails.

        restDesignationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isBadRequest());

        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDesignations() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList
        restDesignationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    @Transactional
    void getDesignations() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get the designations
        restDesignationsMockMvc
            .perform(get(ENTITY_API_URL_ID, designations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(designations.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getDesignationsByIdFiltering() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        Long id = designations.getId();

        defaultDesignationsShouldBeFound("id.equals=" + id);
        defaultDesignationsShouldNotBeFound("id.notEquals=" + id);

        defaultDesignationsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDesignationsShouldNotBeFound("id.greaterThan=" + id);

        defaultDesignationsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDesignationsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDesignationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where name equals to DEFAULT_NAME
        defaultDesignationsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the designationsList where name equals to UPDATED_NAME
        defaultDesignationsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDesignationsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the designationsList where name equals to UPDATED_NAME
        defaultDesignationsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where name is not null
        defaultDesignationsShouldBeFound("name.specified=true");

        // Get all the designationsList where name is null
        defaultDesignationsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByNameContainsSomething() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where name contains DEFAULT_NAME
        defaultDesignationsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the designationsList where name contains UPDATED_NAME
        defaultDesignationsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where name does not contain DEFAULT_NAME
        defaultDesignationsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the designationsList where name does not contain UPDATED_NAME
        defaultDesignationsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where createdat equals to DEFAULT_CREATEDAT
        defaultDesignationsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the designationsList where createdat equals to UPDATED_CREATEDAT
        defaultDesignationsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDesignationsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the designationsList where createdat equals to UPDATED_CREATEDAT
        defaultDesignationsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where createdat is not null
        defaultDesignationsShouldBeFound("createdat.specified=true");

        // Get all the designationsList where createdat is null
        defaultDesignationsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDesignationsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the designationsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDesignationsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDesignationsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the designationsList where updatedat equals to UPDATED_UPDATEDAT
        defaultDesignationsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where updatedat is not null
        defaultDesignationsShouldBeFound("updatedat.specified=true");

        // Get all the designationsList where updatedat is null
        defaultDesignationsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where deletedat equals to DEFAULT_DELETEDAT
        defaultDesignationsShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the designationsList where deletedat equals to UPDATED_DELETEDAT
        defaultDesignationsShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultDesignationsShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the designationsList where deletedat equals to UPDATED_DELETEDAT
        defaultDesignationsShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDesignationsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        // Get all the designationsList where deletedat is not null
        defaultDesignationsShouldBeFound("deletedat.specified=true");

        // Get all the designationsList where deletedat is null
        defaultDesignationsShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByDesignationjobdescriptionsDesignationIsEqualToSomething() throws Exception {
        DesignationJobDescriptions designationjobdescriptionsDesignation;
        if (TestUtil.findAll(em, DesignationJobDescriptions.class).isEmpty()) {
            designationsRepository.saveAndFlush(designations);
            designationjobdescriptionsDesignation = DesignationJobDescriptionsResourceIT.createEntity(em);
        } else {
            designationjobdescriptionsDesignation = TestUtil.findAll(em, DesignationJobDescriptions.class).get(0);
        }
        em.persist(designationjobdescriptionsDesignation);
        em.flush();
        designations.addDesignationjobdescriptionsDesignation(designationjobdescriptionsDesignation);
        designationsRepository.saveAndFlush(designations);
        Long designationjobdescriptionsDesignationId = designationjobdescriptionsDesignation.getId();

        // Get all the designationsList where designationjobdescriptionsDesignation equals to designationjobdescriptionsDesignationId
        defaultDesignationsShouldBeFound("designationjobdescriptionsDesignationId.equals=" + designationjobdescriptionsDesignationId);

        // Get all the designationsList where designationjobdescriptionsDesignation equals to (designationjobdescriptionsDesignationId + 1)
        defaultDesignationsShouldNotBeFound(
            "designationjobdescriptionsDesignationId.equals=" + (designationjobdescriptionsDesignationId + 1)
        );
    }

    @Test
    @Transactional
    void getAllDesignationsByEmployeejobinfoDesignationIsEqualToSomething() throws Exception {
        EmployeeJobInfo employeejobinfoDesignation;
        if (TestUtil.findAll(em, EmployeeJobInfo.class).isEmpty()) {
            designationsRepository.saveAndFlush(designations);
            employeejobinfoDesignation = EmployeeJobInfoResourceIT.createEntity(em);
        } else {
            employeejobinfoDesignation = TestUtil.findAll(em, EmployeeJobInfo.class).get(0);
        }
        em.persist(employeejobinfoDesignation);
        em.flush();
        designations.addEmployeejobinfoDesignation(employeejobinfoDesignation);
        designationsRepository.saveAndFlush(designations);
        Long employeejobinfoDesignationId = employeejobinfoDesignation.getId();

        // Get all the designationsList where employeejobinfoDesignation equals to employeejobinfoDesignationId
        defaultDesignationsShouldBeFound("employeejobinfoDesignationId.equals=" + employeejobinfoDesignationId);

        // Get all the designationsList where employeejobinfoDesignation equals to (employeejobinfoDesignationId + 1)
        defaultDesignationsShouldNotBeFound("employeejobinfoDesignationId.equals=" + (employeejobinfoDesignationId + 1));
    }

    @Test
    @Transactional
    void getAllDesignationsByEmployeesDesignationIsEqualToSomething() throws Exception {
        Employees employeesDesignation;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            designationsRepository.saveAndFlush(designations);
            employeesDesignation = EmployeesResourceIT.createEntity(em);
        } else {
            employeesDesignation = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesDesignation);
        em.flush();
        designations.addEmployeesDesignation(employeesDesignation);
        designationsRepository.saveAndFlush(designations);
        Long employeesDesignationId = employeesDesignation.getId();

        // Get all the designationsList where employeesDesignation equals to employeesDesignationId
        defaultDesignationsShouldBeFound("employeesDesignationId.equals=" + employeesDesignationId);

        // Get all the designationsList where employeesDesignation equals to (employeesDesignationId + 1)
        defaultDesignationsShouldNotBeFound("employeesDesignationId.equals=" + (employeesDesignationId + 1));
    }

    @Test
    @Transactional
    void getAllDesignationsByUserratingsremarksDesignationafterpromotionIsEqualToSomething() throws Exception {
        UserRatingsRemarks userratingsremarksDesignationafterpromotion;
        if (TestUtil.findAll(em, UserRatingsRemarks.class).isEmpty()) {
            designationsRepository.saveAndFlush(designations);
            userratingsremarksDesignationafterpromotion = UserRatingsRemarksResourceIT.createEntity(em);
        } else {
            userratingsremarksDesignationafterpromotion = TestUtil.findAll(em, UserRatingsRemarks.class).get(0);
        }
        em.persist(userratingsremarksDesignationafterpromotion);
        em.flush();
        designations.addUserratingsremarksDesignationafterpromotion(userratingsremarksDesignationafterpromotion);
        designationsRepository.saveAndFlush(designations);
        Long userratingsremarksDesignationafterpromotionId = userratingsremarksDesignationafterpromotion.getId();

        // Get all the designationsList where userratingsremarksDesignationafterpromotion equals to userratingsremarksDesignationafterpromotionId
        defaultDesignationsShouldBeFound(
            "userratingsremarksDesignationafterpromotionId.equals=" + userratingsremarksDesignationafterpromotionId
        );

        // Get all the designationsList where userratingsremarksDesignationafterpromotion equals to (userratingsremarksDesignationafterpromotionId + 1)
        defaultDesignationsShouldNotBeFound(
            "userratingsremarksDesignationafterpromotionId.equals=" + (userratingsremarksDesignationafterpromotionId + 1)
        );
    }

    @Test
    @Transactional
    void getAllDesignationsByUserratingsremarksDesignationafterredesignationIsEqualToSomething() throws Exception {
        UserRatingsRemarks userratingsremarksDesignationafterredesignation;
        if (TestUtil.findAll(em, UserRatingsRemarks.class).isEmpty()) {
            designationsRepository.saveAndFlush(designations);
            userratingsremarksDesignationafterredesignation = UserRatingsRemarksResourceIT.createEntity(em);
        } else {
            userratingsremarksDesignationafterredesignation = TestUtil.findAll(em, UserRatingsRemarks.class).get(0);
        }
        em.persist(userratingsremarksDesignationafterredesignation);
        em.flush();
        designations.addUserratingsremarksDesignationafterredesignation(userratingsremarksDesignationafterredesignation);
        designationsRepository.saveAndFlush(designations);
        Long userratingsremarksDesignationafterredesignationId = userratingsremarksDesignationafterredesignation.getId();

        // Get all the designationsList where userratingsremarksDesignationafterredesignation equals to userratingsremarksDesignationafterredesignationId
        defaultDesignationsShouldBeFound(
            "userratingsremarksDesignationafterredesignationId.equals=" + userratingsremarksDesignationafterredesignationId
        );

        // Get all the designationsList where userratingsremarksDesignationafterredesignation equals to (userratingsremarksDesignationafterredesignationId + 1)
        defaultDesignationsShouldNotBeFound(
            "userratingsremarksDesignationafterredesignationId.equals=" + (userratingsremarksDesignationafterredesignationId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDesignationsShouldBeFound(String filter) throws Exception {
        restDesignationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restDesignationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDesignationsShouldNotBeFound(String filter) throws Exception {
        restDesignationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDesignationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDesignations() throws Exception {
        // Get the designations
        restDesignationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDesignations() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();

        // Update the designations
        Designations updatedDesignations = designationsRepository.findById(designations.getId()).get();
        // Disconnect from session so that the updates on updatedDesignations are not directly saved in db
        em.detach(updatedDesignations);
        updatedDesignations.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).deletedat(UPDATED_DELETEDAT);

        restDesignationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDesignations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDesignations))
            )
            .andExpect(status().isOk());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
        Designations testDesignations = designationsList.get(designationsList.size() - 1);
        assertThat(testDesignations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignations.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDesignations.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDesignations.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDesignations() throws Exception {
        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();
        designations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, designations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDesignations() throws Exception {
        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();
        designations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDesignations() throws Exception {
        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();
        designations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDesignationsWithPatch() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();

        // Update the designations using partial update
        Designations partialUpdatedDesignations = new Designations();
        partialUpdatedDesignations.setId(designations.getId());

        partialUpdatedDesignations.name(UPDATED_NAME);

        restDesignationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignations))
            )
            .andExpect(status().isOk());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
        Designations testDesignations = designationsList.get(designationsList.size() - 1);
        assertThat(testDesignations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignations.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDesignations.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDesignations.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDesignationsWithPatch() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();

        // Update the designations using partial update
        Designations partialUpdatedDesignations = new Designations();
        partialUpdatedDesignations.setId(designations.getId());

        partialUpdatedDesignations
            .name(UPDATED_NAME)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restDesignationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignations))
            )
            .andExpect(status().isOk());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
        Designations testDesignations = designationsList.get(designationsList.size() - 1);
        assertThat(testDesignations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignations.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDesignations.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDesignations.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDesignations() throws Exception {
        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();
        designations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, designations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDesignations() throws Exception {
        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();
        designations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDesignations() throws Exception {
        int databaseSizeBeforeUpdate = designationsRepository.findAll().size();
        designations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Designations in the database
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDesignations() throws Exception {
        // Initialize the database
        designationsRepository.saveAndFlush(designations);

        int databaseSizeBeforeDelete = designationsRepository.findAll().size();

        // Delete the designations
        restDesignationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, designations.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Designations> designationsList = designationsRepository.findAll();
        assertThat(designationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
