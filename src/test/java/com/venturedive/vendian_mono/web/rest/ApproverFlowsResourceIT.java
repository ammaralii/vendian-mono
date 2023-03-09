package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ApproverFlows;
import com.venturedive.vendian_mono.repository.ApproverFlowsRepository;
import com.venturedive.vendian_mono.service.criteria.ApproverFlowsCriteria;
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
 * Integration tests for the {@link ApproverFlowsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApproverFlowsResourceIT {

    private static final String DEFAULT_REFERENCE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_STATUS = "AAAAAAAAA";
    private static final String UPDATED_APPROVER_STATUS = "BBBBBBBBB";

    private static final String DEFAULT_APPROVAL = "AAA";
    private static final String UPDATED_APPROVAL = "BBB";

    private static final String DEFAULT_CURRENT_STATUS = "AAAAAAAAA";
    private static final String UPDATED_CURRENT_STATUS = "BBBBBBBBB";

    private static final String DEFAULT_NEXT_STATUS = "AAAAAAAAA";
    private static final String UPDATED_NEXT_STATUS = "BBBBBBBBB";

    private static final Instant DEFAULT_EFF_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFF_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/approver-flows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApproverFlowsRepository approverFlowsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApproverFlowsMockMvc;

    private ApproverFlows approverFlows;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApproverFlows createEntity(EntityManager em) {
        ApproverFlows approverFlows = new ApproverFlows()
            .referenceType(DEFAULT_REFERENCE_TYPE)
            .approverStatus(DEFAULT_APPROVER_STATUS)
            .approval(DEFAULT_APPROVAL)
            .currentStatus(DEFAULT_CURRENT_STATUS)
            .nextStatus(DEFAULT_NEXT_STATUS)
            .effDate(DEFAULT_EFF_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .version(DEFAULT_VERSION);
        return approverFlows;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApproverFlows createUpdatedEntity(EntityManager em) {
        ApproverFlows approverFlows = new ApproverFlows()
            .referenceType(UPDATED_REFERENCE_TYPE)
            .approverStatus(UPDATED_APPROVER_STATUS)
            .approval(UPDATED_APPROVAL)
            .currentStatus(UPDATED_CURRENT_STATUS)
            .nextStatus(UPDATED_NEXT_STATUS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION);
        return approverFlows;
    }

    @BeforeEach
    public void initTest() {
        approverFlows = createEntity(em);
    }

    @Test
    @Transactional
    void createApproverFlows() throws Exception {
        int databaseSizeBeforeCreate = approverFlowsRepository.findAll().size();
        // Create the ApproverFlows
        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isCreated());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeCreate + 1);
        ApproverFlows testApproverFlows = approverFlowsList.get(approverFlowsList.size() - 1);
        assertThat(testApproverFlows.getReferenceType()).isEqualTo(DEFAULT_REFERENCE_TYPE);
        assertThat(testApproverFlows.getApproverStatus()).isEqualTo(DEFAULT_APPROVER_STATUS);
        assertThat(testApproverFlows.getApproval()).isEqualTo(DEFAULT_APPROVAL);
        assertThat(testApproverFlows.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
        assertThat(testApproverFlows.getNextStatus()).isEqualTo(DEFAULT_NEXT_STATUS);
        assertThat(testApproverFlows.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
        assertThat(testApproverFlows.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApproverFlows.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testApproverFlows.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createApproverFlowsWithExistingId() throws Exception {
        // Create the ApproverFlows with an existing ID
        approverFlows.setId(1L);

        int databaseSizeBeforeCreate = approverFlowsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReferenceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setReferenceType(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApproverStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setApproverStatus(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApprovalIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setApproval(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setCurrentStatus(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNextStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setNextStatus(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setEffDate(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setCreatedAt(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setUpdatedAt(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverFlowsRepository.findAll().size();
        // set the field null
        approverFlows.setVersion(null);

        // Create the ApproverFlows, which fails.

        restApproverFlowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApproverFlows() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList
        restApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approverFlows.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE)))
            .andExpect(jsonPath("$.[*].approverStatus").value(hasItem(DEFAULT_APPROVER_STATUS)))
            .andExpect(jsonPath("$.[*].approval").value(hasItem(DEFAULT_APPROVAL)))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS)))
            .andExpect(jsonPath("$.[*].nextStatus").value(hasItem(DEFAULT_NEXT_STATUS)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getApproverFlows() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get the approverFlows
        restApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL_ID, approverFlows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approverFlows.getId().intValue()))
            .andExpect(jsonPath("$.referenceType").value(DEFAULT_REFERENCE_TYPE))
            .andExpect(jsonPath("$.approverStatus").value(DEFAULT_APPROVER_STATUS))
            .andExpect(jsonPath("$.approval").value(DEFAULT_APPROVAL))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS))
            .andExpect(jsonPath("$.nextStatus").value(DEFAULT_NEXT_STATUS))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getApproverFlowsByIdFiltering() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        Long id = approverFlows.getId();

        defaultApproverFlowsShouldBeFound("id.equals=" + id);
        defaultApproverFlowsShouldNotBeFound("id.notEquals=" + id);

        defaultApproverFlowsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApproverFlowsShouldNotBeFound("id.greaterThan=" + id);

        defaultApproverFlowsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApproverFlowsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByReferenceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where referenceType equals to DEFAULT_REFERENCE_TYPE
        defaultApproverFlowsShouldBeFound("referenceType.equals=" + DEFAULT_REFERENCE_TYPE);

        // Get all the approverFlowsList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultApproverFlowsShouldNotBeFound("referenceType.equals=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByReferenceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where referenceType in DEFAULT_REFERENCE_TYPE or UPDATED_REFERENCE_TYPE
        defaultApproverFlowsShouldBeFound("referenceType.in=" + DEFAULT_REFERENCE_TYPE + "," + UPDATED_REFERENCE_TYPE);

        // Get all the approverFlowsList where referenceType equals to UPDATED_REFERENCE_TYPE
        defaultApproverFlowsShouldNotBeFound("referenceType.in=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByReferenceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where referenceType is not null
        defaultApproverFlowsShouldBeFound("referenceType.specified=true");

        // Get all the approverFlowsList where referenceType is null
        defaultApproverFlowsShouldNotBeFound("referenceType.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByReferenceTypeContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where referenceType contains DEFAULT_REFERENCE_TYPE
        defaultApproverFlowsShouldBeFound("referenceType.contains=" + DEFAULT_REFERENCE_TYPE);

        // Get all the approverFlowsList where referenceType contains UPDATED_REFERENCE_TYPE
        defaultApproverFlowsShouldNotBeFound("referenceType.contains=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByReferenceTypeNotContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where referenceType does not contain DEFAULT_REFERENCE_TYPE
        defaultApproverFlowsShouldNotBeFound("referenceType.doesNotContain=" + DEFAULT_REFERENCE_TYPE);

        // Get all the approverFlowsList where referenceType does not contain UPDATED_REFERENCE_TYPE
        defaultApproverFlowsShouldBeFound("referenceType.doesNotContain=" + UPDATED_REFERENCE_TYPE);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApproverStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approverStatus equals to DEFAULT_APPROVER_STATUS
        defaultApproverFlowsShouldBeFound("approverStatus.equals=" + DEFAULT_APPROVER_STATUS);

        // Get all the approverFlowsList where approverStatus equals to UPDATED_APPROVER_STATUS
        defaultApproverFlowsShouldNotBeFound("approverStatus.equals=" + UPDATED_APPROVER_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApproverStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approverStatus in DEFAULT_APPROVER_STATUS or UPDATED_APPROVER_STATUS
        defaultApproverFlowsShouldBeFound("approverStatus.in=" + DEFAULT_APPROVER_STATUS + "," + UPDATED_APPROVER_STATUS);

        // Get all the approverFlowsList where approverStatus equals to UPDATED_APPROVER_STATUS
        defaultApproverFlowsShouldNotBeFound("approverStatus.in=" + UPDATED_APPROVER_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApproverStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approverStatus is not null
        defaultApproverFlowsShouldBeFound("approverStatus.specified=true");

        // Get all the approverFlowsList where approverStatus is null
        defaultApproverFlowsShouldNotBeFound("approverStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApproverStatusContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approverStatus contains DEFAULT_APPROVER_STATUS
        defaultApproverFlowsShouldBeFound("approverStatus.contains=" + DEFAULT_APPROVER_STATUS);

        // Get all the approverFlowsList where approverStatus contains UPDATED_APPROVER_STATUS
        defaultApproverFlowsShouldNotBeFound("approverStatus.contains=" + UPDATED_APPROVER_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApproverStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approverStatus does not contain DEFAULT_APPROVER_STATUS
        defaultApproverFlowsShouldNotBeFound("approverStatus.doesNotContain=" + DEFAULT_APPROVER_STATUS);

        // Get all the approverFlowsList where approverStatus does not contain UPDATED_APPROVER_STATUS
        defaultApproverFlowsShouldBeFound("approverStatus.doesNotContain=" + UPDATED_APPROVER_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApprovalIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approval equals to DEFAULT_APPROVAL
        defaultApproverFlowsShouldBeFound("approval.equals=" + DEFAULT_APPROVAL);

        // Get all the approverFlowsList where approval equals to UPDATED_APPROVAL
        defaultApproverFlowsShouldNotBeFound("approval.equals=" + UPDATED_APPROVAL);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApprovalIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approval in DEFAULT_APPROVAL or UPDATED_APPROVAL
        defaultApproverFlowsShouldBeFound("approval.in=" + DEFAULT_APPROVAL + "," + UPDATED_APPROVAL);

        // Get all the approverFlowsList where approval equals to UPDATED_APPROVAL
        defaultApproverFlowsShouldNotBeFound("approval.in=" + UPDATED_APPROVAL);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApprovalIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approval is not null
        defaultApproverFlowsShouldBeFound("approval.specified=true");

        // Get all the approverFlowsList where approval is null
        defaultApproverFlowsShouldNotBeFound("approval.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApprovalContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approval contains DEFAULT_APPROVAL
        defaultApproverFlowsShouldBeFound("approval.contains=" + DEFAULT_APPROVAL);

        // Get all the approverFlowsList where approval contains UPDATED_APPROVAL
        defaultApproverFlowsShouldNotBeFound("approval.contains=" + UPDATED_APPROVAL);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByApprovalNotContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where approval does not contain DEFAULT_APPROVAL
        defaultApproverFlowsShouldNotBeFound("approval.doesNotContain=" + DEFAULT_APPROVAL);

        // Get all the approverFlowsList where approval does not contain UPDATED_APPROVAL
        defaultApproverFlowsShouldBeFound("approval.doesNotContain=" + UPDATED_APPROVAL);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByCurrentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where currentStatus equals to DEFAULT_CURRENT_STATUS
        defaultApproverFlowsShouldBeFound("currentStatus.equals=" + DEFAULT_CURRENT_STATUS);

        // Get all the approverFlowsList where currentStatus equals to UPDATED_CURRENT_STATUS
        defaultApproverFlowsShouldNotBeFound("currentStatus.equals=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByCurrentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where currentStatus in DEFAULT_CURRENT_STATUS or UPDATED_CURRENT_STATUS
        defaultApproverFlowsShouldBeFound("currentStatus.in=" + DEFAULT_CURRENT_STATUS + "," + UPDATED_CURRENT_STATUS);

        // Get all the approverFlowsList where currentStatus equals to UPDATED_CURRENT_STATUS
        defaultApproverFlowsShouldNotBeFound("currentStatus.in=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByCurrentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where currentStatus is not null
        defaultApproverFlowsShouldBeFound("currentStatus.specified=true");

        // Get all the approverFlowsList where currentStatus is null
        defaultApproverFlowsShouldNotBeFound("currentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByCurrentStatusContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where currentStatus contains DEFAULT_CURRENT_STATUS
        defaultApproverFlowsShouldBeFound("currentStatus.contains=" + DEFAULT_CURRENT_STATUS);

        // Get all the approverFlowsList where currentStatus contains UPDATED_CURRENT_STATUS
        defaultApproverFlowsShouldNotBeFound("currentStatus.contains=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByCurrentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where currentStatus does not contain DEFAULT_CURRENT_STATUS
        defaultApproverFlowsShouldNotBeFound("currentStatus.doesNotContain=" + DEFAULT_CURRENT_STATUS);

        // Get all the approverFlowsList where currentStatus does not contain UPDATED_CURRENT_STATUS
        defaultApproverFlowsShouldBeFound("currentStatus.doesNotContain=" + UPDATED_CURRENT_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByNextStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where nextStatus equals to DEFAULT_NEXT_STATUS
        defaultApproverFlowsShouldBeFound("nextStatus.equals=" + DEFAULT_NEXT_STATUS);

        // Get all the approverFlowsList where nextStatus equals to UPDATED_NEXT_STATUS
        defaultApproverFlowsShouldNotBeFound("nextStatus.equals=" + UPDATED_NEXT_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByNextStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where nextStatus in DEFAULT_NEXT_STATUS or UPDATED_NEXT_STATUS
        defaultApproverFlowsShouldBeFound("nextStatus.in=" + DEFAULT_NEXT_STATUS + "," + UPDATED_NEXT_STATUS);

        // Get all the approverFlowsList where nextStatus equals to UPDATED_NEXT_STATUS
        defaultApproverFlowsShouldNotBeFound("nextStatus.in=" + UPDATED_NEXT_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByNextStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where nextStatus is not null
        defaultApproverFlowsShouldBeFound("nextStatus.specified=true");

        // Get all the approverFlowsList where nextStatus is null
        defaultApproverFlowsShouldNotBeFound("nextStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByNextStatusContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where nextStatus contains DEFAULT_NEXT_STATUS
        defaultApproverFlowsShouldBeFound("nextStatus.contains=" + DEFAULT_NEXT_STATUS);

        // Get all the approverFlowsList where nextStatus contains UPDATED_NEXT_STATUS
        defaultApproverFlowsShouldNotBeFound("nextStatus.contains=" + UPDATED_NEXT_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByNextStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where nextStatus does not contain DEFAULT_NEXT_STATUS
        defaultApproverFlowsShouldNotBeFound("nextStatus.doesNotContain=" + DEFAULT_NEXT_STATUS);

        // Get all the approverFlowsList where nextStatus does not contain UPDATED_NEXT_STATUS
        defaultApproverFlowsShouldBeFound("nextStatus.doesNotContain=" + UPDATED_NEXT_STATUS);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where effDate equals to DEFAULT_EFF_DATE
        defaultApproverFlowsShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the approverFlowsList where effDate equals to UPDATED_EFF_DATE
        defaultApproverFlowsShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultApproverFlowsShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the approverFlowsList where effDate equals to UPDATED_EFF_DATE
        defaultApproverFlowsShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where effDate is not null
        defaultApproverFlowsShouldBeFound("effDate.specified=true");

        // Get all the approverFlowsList where effDate is null
        defaultApproverFlowsShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where createdAt equals to DEFAULT_CREATED_AT
        defaultApproverFlowsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the approverFlowsList where createdAt equals to UPDATED_CREATED_AT
        defaultApproverFlowsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultApproverFlowsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the approverFlowsList where createdAt equals to UPDATED_CREATED_AT
        defaultApproverFlowsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where createdAt is not null
        defaultApproverFlowsShouldBeFound("createdAt.specified=true");

        // Get all the approverFlowsList where createdAt is null
        defaultApproverFlowsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultApproverFlowsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the approverFlowsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultApproverFlowsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultApproverFlowsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the approverFlowsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultApproverFlowsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where updatedAt is not null
        defaultApproverFlowsShouldBeFound("updatedAt.specified=true");

        // Get all the approverFlowsList where updatedAt is null
        defaultApproverFlowsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where version equals to DEFAULT_VERSION
        defaultApproverFlowsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the approverFlowsList where version equals to UPDATED_VERSION
        defaultApproverFlowsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultApproverFlowsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the approverFlowsList where version equals to UPDATED_VERSION
        defaultApproverFlowsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where version is not null
        defaultApproverFlowsShouldBeFound("version.specified=true");

        // Get all the approverFlowsList where version is null
        defaultApproverFlowsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllApproverFlowsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where version is greater than or equal to DEFAULT_VERSION
        defaultApproverFlowsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the approverFlowsList where version is greater than or equal to UPDATED_VERSION
        defaultApproverFlowsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where version is less than or equal to DEFAULT_VERSION
        defaultApproverFlowsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the approverFlowsList where version is less than or equal to SMALLER_VERSION
        defaultApproverFlowsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where version is less than DEFAULT_VERSION
        defaultApproverFlowsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the approverFlowsList where version is less than UPDATED_VERSION
        defaultApproverFlowsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproverFlowsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        // Get all the approverFlowsList where version is greater than DEFAULT_VERSION
        defaultApproverFlowsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the approverFlowsList where version is greater than SMALLER_VERSION
        defaultApproverFlowsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApproverFlowsShouldBeFound(String filter) throws Exception {
        restApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approverFlows.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceType").value(hasItem(DEFAULT_REFERENCE_TYPE)))
            .andExpect(jsonPath("$.[*].approverStatus").value(hasItem(DEFAULT_APPROVER_STATUS)))
            .andExpect(jsonPath("$.[*].approval").value(hasItem(DEFAULT_APPROVAL)))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS)))
            .andExpect(jsonPath("$.[*].nextStatus").value(hasItem(DEFAULT_NEXT_STATUS)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApproverFlowsShouldNotBeFound(String filter) throws Exception {
        restApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApproverFlowsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApproverFlows() throws Exception {
        // Get the approverFlows
        restApproverFlowsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApproverFlows() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();

        // Update the approverFlows
        ApproverFlows updatedApproverFlows = approverFlowsRepository.findById(approverFlows.getId()).get();
        // Disconnect from session so that the updates on updatedApproverFlows are not directly saved in db
        em.detach(updatedApproverFlows);
        updatedApproverFlows
            .referenceType(UPDATED_REFERENCE_TYPE)
            .approverStatus(UPDATED_APPROVER_STATUS)
            .approval(UPDATED_APPROVAL)
            .currentStatus(UPDATED_CURRENT_STATUS)
            .nextStatus(UPDATED_NEXT_STATUS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION);

        restApproverFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApproverFlows.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApproverFlows))
            )
            .andExpect(status().isOk());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
        ApproverFlows testApproverFlows = approverFlowsList.get(approverFlowsList.size() - 1);
        assertThat(testApproverFlows.getReferenceType()).isEqualTo(UPDATED_REFERENCE_TYPE);
        assertThat(testApproverFlows.getApproverStatus()).isEqualTo(UPDATED_APPROVER_STATUS);
        assertThat(testApproverFlows.getApproval()).isEqualTo(UPDATED_APPROVAL);
        assertThat(testApproverFlows.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
        assertThat(testApproverFlows.getNextStatus()).isEqualTo(UPDATED_NEXT_STATUS);
        assertThat(testApproverFlows.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testApproverFlows.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApproverFlows.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApproverFlows.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();
        approverFlows.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approverFlows.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();
        approverFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverFlowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();
        approverFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverFlowsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApproverFlowsWithPatch() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();

        // Update the approverFlows using partial update
        ApproverFlows partialUpdatedApproverFlows = new ApproverFlows();
        partialUpdatedApproverFlows.setId(approverFlows.getId());

        partialUpdatedApproverFlows.approverStatus(UPDATED_APPROVER_STATUS).effDate(UPDATED_EFF_DATE).updatedAt(UPDATED_UPDATED_AT);

        restApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproverFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproverFlows))
            )
            .andExpect(status().isOk());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
        ApproverFlows testApproverFlows = approverFlowsList.get(approverFlowsList.size() - 1);
        assertThat(testApproverFlows.getReferenceType()).isEqualTo(DEFAULT_REFERENCE_TYPE);
        assertThat(testApproverFlows.getApproverStatus()).isEqualTo(UPDATED_APPROVER_STATUS);
        assertThat(testApproverFlows.getApproval()).isEqualTo(DEFAULT_APPROVAL);
        assertThat(testApproverFlows.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
        assertThat(testApproverFlows.getNextStatus()).isEqualTo(DEFAULT_NEXT_STATUS);
        assertThat(testApproverFlows.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testApproverFlows.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApproverFlows.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApproverFlows.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateApproverFlowsWithPatch() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();

        // Update the approverFlows using partial update
        ApproverFlows partialUpdatedApproverFlows = new ApproverFlows();
        partialUpdatedApproverFlows.setId(approverFlows.getId());

        partialUpdatedApproverFlows
            .referenceType(UPDATED_REFERENCE_TYPE)
            .approverStatus(UPDATED_APPROVER_STATUS)
            .approval(UPDATED_APPROVAL)
            .currentStatus(UPDATED_CURRENT_STATUS)
            .nextStatus(UPDATED_NEXT_STATUS)
            .effDate(UPDATED_EFF_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION);

        restApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproverFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproverFlows))
            )
            .andExpect(status().isOk());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
        ApproverFlows testApproverFlows = approverFlowsList.get(approverFlowsList.size() - 1);
        assertThat(testApproverFlows.getReferenceType()).isEqualTo(UPDATED_REFERENCE_TYPE);
        assertThat(testApproverFlows.getApproverStatus()).isEqualTo(UPDATED_APPROVER_STATUS);
        assertThat(testApproverFlows.getApproval()).isEqualTo(UPDATED_APPROVAL);
        assertThat(testApproverFlows.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
        assertThat(testApproverFlows.getNextStatus()).isEqualTo(UPDATED_NEXT_STATUS);
        assertThat(testApproverFlows.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
        assertThat(testApproverFlows.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApproverFlows.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApproverFlows.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();
        approverFlows.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approverFlows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();
        approverFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApproverFlows() throws Exception {
        int databaseSizeBeforeUpdate = approverFlowsRepository.findAll().size();
        approverFlows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverFlowsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverFlows))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApproverFlows in the database
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApproverFlows() throws Exception {
        // Initialize the database
        approverFlowsRepository.saveAndFlush(approverFlows);

        int databaseSizeBeforeDelete = approverFlowsRepository.findAll().size();

        // Delete the approverFlows
        restApproverFlowsMockMvc
            .perform(delete(ENTITY_API_URL_ID, approverFlows.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApproverFlows> approverFlowsList = approverFlowsRepository.findAll();
        assertThat(approverFlowsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
