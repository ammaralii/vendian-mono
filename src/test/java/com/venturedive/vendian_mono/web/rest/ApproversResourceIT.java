package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ApproverGroups;
import com.venturedive.vendian_mono.domain.Approvers;
import com.venturedive.vendian_mono.repository.ApproversRepository;
import com.venturedive.vendian_mono.service.criteria.ApproversCriteria;
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
 * Integration tests for the {@link ApproversResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApproversResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_AS = "AAAAAAAAAA";
    private static final String UPDATED_AS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBB";

    private static final Instant DEFAULT_STAUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STAUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final Integer SMALLER_PRIORITY = 1 - 1;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/approvers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApproversRepository approversRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApproversMockMvc;

    private Approvers approvers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approvers createEntity(EntityManager em) {
        Approvers approvers = new Approvers()
            .userId(DEFAULT_USER_ID)
            .reference(DEFAULT_REFERENCE)
            .as(DEFAULT_AS)
            .comment(DEFAULT_COMMENT)
            .status(DEFAULT_STATUS)
            .stausDate(DEFAULT_STAUS_DATE)
            .priority(DEFAULT_PRIORITY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        // Add required entity
        ApproverGroups approverGroups;
        if (TestUtil.findAll(em, ApproverGroups.class).isEmpty()) {
            approverGroups = ApproverGroupsResourceIT.createEntity(em);
            em.persist(approverGroups);
            em.flush();
        } else {
            approverGroups = TestUtil.findAll(em, ApproverGroups.class).get(0);
        }
        approvers.setApproverGroup(approverGroups);
        return approvers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approvers createUpdatedEntity(EntityManager em) {
        Approvers approvers = new Approvers()
            .userId(UPDATED_USER_ID)
            .reference(UPDATED_REFERENCE)
            .as(UPDATED_AS)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS)
            .stausDate(UPDATED_STAUS_DATE)
            .priority(UPDATED_PRIORITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        // Add required entity
        ApproverGroups approverGroups;
        if (TestUtil.findAll(em, ApproverGroups.class).isEmpty()) {
            approverGroups = ApproverGroupsResourceIT.createUpdatedEntity(em);
            em.persist(approverGroups);
            em.flush();
        } else {
            approverGroups = TestUtil.findAll(em, ApproverGroups.class).get(0);
        }
        approvers.setApproverGroup(approverGroups);
        return approvers;
    }

    @BeforeEach
    public void initTest() {
        approvers = createEntity(em);
    }

    @Test
    @Transactional
    void createApprovers() throws Exception {
        int databaseSizeBeforeCreate = approversRepository.findAll().size();
        // Create the Approvers
        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isCreated());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeCreate + 1);
        Approvers testApprovers = approversList.get(approversList.size() - 1);
        assertThat(testApprovers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testApprovers.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testApprovers.getAs()).isEqualTo(DEFAULT_AS);
        assertThat(testApprovers.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testApprovers.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApprovers.getStausDate()).isEqualTo(DEFAULT_STAUS_DATE);
        assertThat(testApprovers.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testApprovers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testApprovers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testApprovers.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testApprovers.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createApproversWithExistingId() throws Exception {
        // Create the Approvers with an existing ID
        approvers.setId(1L);

        int databaseSizeBeforeCreate = approversRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = approversRepository.findAll().size();
        // set the field null
        approvers.setReference(null);

        // Create the Approvers, which fails.

        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAsIsRequired() throws Exception {
        int databaseSizeBeforeTest = approversRepository.findAll().size();
        // set the field null
        approvers.setAs(null);

        // Create the Approvers, which fails.

        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = approversRepository.findAll().size();
        // set the field null
        approvers.setStatus(null);

        // Create the Approvers, which fails.

        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStausDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = approversRepository.findAll().size();
        // set the field null
        approvers.setStausDate(null);

        // Create the Approvers, which fails.

        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = approversRepository.findAll().size();
        // set the field null
        approvers.setPriority(null);

        // Create the Approvers, which fails.

        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = approversRepository.findAll().size();
        // set the field null
        approvers.setCreatedAt(null);

        // Create the Approvers, which fails.

        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = approversRepository.findAll().size();
        // set the field null
        approvers.setUpdatedAt(null);

        // Create the Approvers, which fails.

        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = approversRepository.findAll().size();
        // set the field null
        approvers.setVersion(null);

        // Create the Approvers, which fails.

        restApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApprovers() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList
        restApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approvers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].as").value(hasItem(DEFAULT_AS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].stausDate").value(hasItem(DEFAULT_STAUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getApprovers() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get the approvers
        restApproversMockMvc
            .perform(get(ENTITY_API_URL_ID, approvers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approvers.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.as").value(DEFAULT_AS))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.stausDate").value(DEFAULT_STAUS_DATE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getApproversByIdFiltering() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        Long id = approvers.getId();

        defaultApproversShouldBeFound("id.equals=" + id);
        defaultApproversShouldNotBeFound("id.notEquals=" + id);

        defaultApproversShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApproversShouldNotBeFound("id.greaterThan=" + id);

        defaultApproversShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApproversShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApproversByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where userId equals to DEFAULT_USER_ID
        defaultApproversShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the approversList where userId equals to UPDATED_USER_ID
        defaultApproversShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllApproversByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultApproversShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the approversList where userId equals to UPDATED_USER_ID
        defaultApproversShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllApproversByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where userId is not null
        defaultApproversShouldBeFound("userId.specified=true");

        // Get all the approversList where userId is null
        defaultApproversShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByUserIdContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where userId contains DEFAULT_USER_ID
        defaultApproversShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the approversList where userId contains UPDATED_USER_ID
        defaultApproversShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllApproversByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where userId does not contain DEFAULT_USER_ID
        defaultApproversShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the approversList where userId does not contain UPDATED_USER_ID
        defaultApproversShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllApproversByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where reference equals to DEFAULT_REFERENCE
        defaultApproversShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the approversList where reference equals to UPDATED_REFERENCE
        defaultApproversShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllApproversByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultApproversShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the approversList where reference equals to UPDATED_REFERENCE
        defaultApproversShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllApproversByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where reference is not null
        defaultApproversShouldBeFound("reference.specified=true");

        // Get all the approversList where reference is null
        defaultApproversShouldNotBeFound("reference.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByReferenceContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where reference contains DEFAULT_REFERENCE
        defaultApproversShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the approversList where reference contains UPDATED_REFERENCE
        defaultApproversShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllApproversByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where reference does not contain DEFAULT_REFERENCE
        defaultApproversShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the approversList where reference does not contain UPDATED_REFERENCE
        defaultApproversShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllApproversByAsIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where as equals to DEFAULT_AS
        defaultApproversShouldBeFound("as.equals=" + DEFAULT_AS);

        // Get all the approversList where as equals to UPDATED_AS
        defaultApproversShouldNotBeFound("as.equals=" + UPDATED_AS);
    }

    @Test
    @Transactional
    void getAllApproversByAsIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where as in DEFAULT_AS or UPDATED_AS
        defaultApproversShouldBeFound("as.in=" + DEFAULT_AS + "," + UPDATED_AS);

        // Get all the approversList where as equals to UPDATED_AS
        defaultApproversShouldNotBeFound("as.in=" + UPDATED_AS);
    }

    @Test
    @Transactional
    void getAllApproversByAsIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where as is not null
        defaultApproversShouldBeFound("as.specified=true");

        // Get all the approversList where as is null
        defaultApproversShouldNotBeFound("as.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByAsContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where as contains DEFAULT_AS
        defaultApproversShouldBeFound("as.contains=" + DEFAULT_AS);

        // Get all the approversList where as contains UPDATED_AS
        defaultApproversShouldNotBeFound("as.contains=" + UPDATED_AS);
    }

    @Test
    @Transactional
    void getAllApproversByAsNotContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where as does not contain DEFAULT_AS
        defaultApproversShouldNotBeFound("as.doesNotContain=" + DEFAULT_AS);

        // Get all the approversList where as does not contain UPDATED_AS
        defaultApproversShouldBeFound("as.doesNotContain=" + UPDATED_AS);
    }

    @Test
    @Transactional
    void getAllApproversByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where comment equals to DEFAULT_COMMENT
        defaultApproversShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the approversList where comment equals to UPDATED_COMMENT
        defaultApproversShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllApproversByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultApproversShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the approversList where comment equals to UPDATED_COMMENT
        defaultApproversShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllApproversByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where comment is not null
        defaultApproversShouldBeFound("comment.specified=true");

        // Get all the approversList where comment is null
        defaultApproversShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByCommentContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where comment contains DEFAULT_COMMENT
        defaultApproversShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the approversList where comment contains UPDATED_COMMENT
        defaultApproversShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllApproversByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where comment does not contain DEFAULT_COMMENT
        defaultApproversShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the approversList where comment does not contain UPDATED_COMMENT
        defaultApproversShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllApproversByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where status equals to DEFAULT_STATUS
        defaultApproversShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the approversList where status equals to UPDATED_STATUS
        defaultApproversShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApproversByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApproversShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the approversList where status equals to UPDATED_STATUS
        defaultApproversShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApproversByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where status is not null
        defaultApproversShouldBeFound("status.specified=true");

        // Get all the approversList where status is null
        defaultApproversShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByStatusContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where status contains DEFAULT_STATUS
        defaultApproversShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the approversList where status contains UPDATED_STATUS
        defaultApproversShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApproversByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where status does not contain DEFAULT_STATUS
        defaultApproversShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the approversList where status does not contain UPDATED_STATUS
        defaultApproversShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApproversByStausDateIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where stausDate equals to DEFAULT_STAUS_DATE
        defaultApproversShouldBeFound("stausDate.equals=" + DEFAULT_STAUS_DATE);

        // Get all the approversList where stausDate equals to UPDATED_STAUS_DATE
        defaultApproversShouldNotBeFound("stausDate.equals=" + UPDATED_STAUS_DATE);
    }

    @Test
    @Transactional
    void getAllApproversByStausDateIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where stausDate in DEFAULT_STAUS_DATE or UPDATED_STAUS_DATE
        defaultApproversShouldBeFound("stausDate.in=" + DEFAULT_STAUS_DATE + "," + UPDATED_STAUS_DATE);

        // Get all the approversList where stausDate equals to UPDATED_STAUS_DATE
        defaultApproversShouldNotBeFound("stausDate.in=" + UPDATED_STAUS_DATE);
    }

    @Test
    @Transactional
    void getAllApproversByStausDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where stausDate is not null
        defaultApproversShouldBeFound("stausDate.specified=true");

        // Get all the approversList where stausDate is null
        defaultApproversShouldNotBeFound("stausDate.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where priority equals to DEFAULT_PRIORITY
        defaultApproversShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the approversList where priority equals to UPDATED_PRIORITY
        defaultApproversShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllApproversByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultApproversShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the approversList where priority equals to UPDATED_PRIORITY
        defaultApproversShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllApproversByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where priority is not null
        defaultApproversShouldBeFound("priority.specified=true");

        // Get all the approversList where priority is null
        defaultApproversShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultApproversShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the approversList where priority is greater than or equal to UPDATED_PRIORITY
        defaultApproversShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllApproversByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where priority is less than or equal to DEFAULT_PRIORITY
        defaultApproversShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the approversList where priority is less than or equal to SMALLER_PRIORITY
        defaultApproversShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllApproversByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where priority is less than DEFAULT_PRIORITY
        defaultApproversShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the approversList where priority is less than UPDATED_PRIORITY
        defaultApproversShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllApproversByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where priority is greater than DEFAULT_PRIORITY
        defaultApproversShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the approversList where priority is greater than SMALLER_PRIORITY
        defaultApproversShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllApproversByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where createdAt equals to DEFAULT_CREATED_AT
        defaultApproversShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the approversList where createdAt equals to UPDATED_CREATED_AT
        defaultApproversShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApproversByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultApproversShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the approversList where createdAt equals to UPDATED_CREATED_AT
        defaultApproversShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllApproversByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where createdAt is not null
        defaultApproversShouldBeFound("createdAt.specified=true");

        // Get all the approversList where createdAt is null
        defaultApproversShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultApproversShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the approversList where updatedAt equals to UPDATED_UPDATED_AT
        defaultApproversShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllApproversByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultApproversShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the approversList where updatedAt equals to UPDATED_UPDATED_AT
        defaultApproversShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllApproversByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where updatedAt is not null
        defaultApproversShouldBeFound("updatedAt.specified=true");

        // Get all the approversList where updatedAt is null
        defaultApproversShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where deletedAt equals to DEFAULT_DELETED_AT
        defaultApproversShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the approversList where deletedAt equals to UPDATED_DELETED_AT
        defaultApproversShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllApproversByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultApproversShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the approversList where deletedAt equals to UPDATED_DELETED_AT
        defaultApproversShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllApproversByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where deletedAt is not null
        defaultApproversShouldBeFound("deletedAt.specified=true");

        // Get all the approversList where deletedAt is null
        defaultApproversShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where version equals to DEFAULT_VERSION
        defaultApproversShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the approversList where version equals to UPDATED_VERSION
        defaultApproversShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproversByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultApproversShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the approversList where version equals to UPDATED_VERSION
        defaultApproversShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproversByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where version is not null
        defaultApproversShouldBeFound("version.specified=true");

        // Get all the approversList where version is null
        defaultApproversShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllApproversByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where version is greater than or equal to DEFAULT_VERSION
        defaultApproversShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the approversList where version is greater than or equal to UPDATED_VERSION
        defaultApproversShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproversByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where version is less than or equal to DEFAULT_VERSION
        defaultApproversShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the approversList where version is less than or equal to SMALLER_VERSION
        defaultApproversShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllApproversByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where version is less than DEFAULT_VERSION
        defaultApproversShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the approversList where version is less than UPDATED_VERSION
        defaultApproversShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllApproversByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        // Get all the approversList where version is greater than DEFAULT_VERSION
        defaultApproversShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the approversList where version is greater than SMALLER_VERSION
        defaultApproversShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllApproversByApproverGroupIsEqualToSomething() throws Exception {
        ApproverGroups approverGroup;
        if (TestUtil.findAll(em, ApproverGroups.class).isEmpty()) {
            approversRepository.saveAndFlush(approvers);
            approverGroup = ApproverGroupsResourceIT.createEntity(em);
        } else {
            approverGroup = TestUtil.findAll(em, ApproverGroups.class).get(0);
        }
        em.persist(approverGroup);
        em.flush();
        approvers.setApproverGroup(approverGroup);
        approversRepository.saveAndFlush(approvers);
        Long approverGroupId = approverGroup.getId();

        // Get all the approversList where approverGroup equals to approverGroupId
        defaultApproversShouldBeFound("approverGroupId.equals=" + approverGroupId);

        // Get all the approversList where approverGroup equals to (approverGroupId + 1)
        defaultApproversShouldNotBeFound("approverGroupId.equals=" + (approverGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApproversShouldBeFound(String filter) throws Exception {
        restApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approvers.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].as").value(hasItem(DEFAULT_AS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].stausDate").value(hasItem(DEFAULT_STAUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApproversShouldNotBeFound(String filter) throws Exception {
        restApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApprovers() throws Exception {
        // Get the approvers
        restApproversMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApprovers() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        int databaseSizeBeforeUpdate = approversRepository.findAll().size();

        // Update the approvers
        Approvers updatedApprovers = approversRepository.findById(approvers.getId()).get();
        // Disconnect from session so that the updates on updatedApprovers are not directly saved in db
        em.detach(updatedApprovers);
        updatedApprovers
            .userId(UPDATED_USER_ID)
            .reference(UPDATED_REFERENCE)
            .as(UPDATED_AS)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS)
            .stausDate(UPDATED_STAUS_DATE)
            .priority(UPDATED_PRIORITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApprovers))
            )
            .andExpect(status().isOk());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
        Approvers testApprovers = approversList.get(approversList.size() - 1);
        assertThat(testApprovers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testApprovers.getAs()).isEqualTo(UPDATED_AS);
        assertThat(testApprovers.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testApprovers.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovers.getStausDate()).isEqualTo(UPDATED_STAUS_DATE);
        assertThat(testApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApprovers.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingApprovers() throws Exception {
        int databaseSizeBeforeUpdate = approversRepository.findAll().size();
        approvers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprovers() throws Exception {
        int databaseSizeBeforeUpdate = approversRepository.findAll().size();
        approvers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprovers() throws Exception {
        int databaseSizeBeforeUpdate = approversRepository.findAll().size();
        approvers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproversMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApproversWithPatch() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        int databaseSizeBeforeUpdate = approversRepository.findAll().size();

        // Update the approvers using partial update
        Approvers partialUpdatedApprovers = new Approvers();
        partialUpdatedApprovers.setId(approvers.getId());

        partialUpdatedApprovers
            .reference(UPDATED_REFERENCE)
            .as(UPDATED_AS)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS)
            .stausDate(UPDATED_STAUS_DATE)
            .priority(UPDATED_PRIORITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovers))
            )
            .andExpect(status().isOk());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
        Approvers testApprovers = approversList.get(approversList.size() - 1);
        assertThat(testApprovers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testApprovers.getAs()).isEqualTo(UPDATED_AS);
        assertThat(testApprovers.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testApprovers.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovers.getStausDate()).isEqualTo(UPDATED_STAUS_DATE);
        assertThat(testApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApprovers.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateApproversWithPatch() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        int databaseSizeBeforeUpdate = approversRepository.findAll().size();

        // Update the approvers using partial update
        Approvers partialUpdatedApprovers = new Approvers();
        partialUpdatedApprovers.setId(approvers.getId());

        partialUpdatedApprovers
            .userId(UPDATED_USER_ID)
            .reference(UPDATED_REFERENCE)
            .as(UPDATED_AS)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS)
            .stausDate(UPDATED_STAUS_DATE)
            .priority(UPDATED_PRIORITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovers))
            )
            .andExpect(status().isOk());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
        Approvers testApprovers = approversList.get(approversList.size() - 1);
        assertThat(testApprovers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testApprovers.getAs()).isEqualTo(UPDATED_AS);
        assertThat(testApprovers.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testApprovers.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovers.getStausDate()).isEqualTo(UPDATED_STAUS_DATE);
        assertThat(testApprovers.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testApprovers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testApprovers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testApprovers.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testApprovers.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingApprovers() throws Exception {
        int databaseSizeBeforeUpdate = approversRepository.findAll().size();
        approvers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approvers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprovers() throws Exception {
        int databaseSizeBeforeUpdate = approversRepository.findAll().size();
        approvers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprovers() throws Exception {
        int databaseSizeBeforeUpdate = approversRepository.findAll().size();
        approvers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproversMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approvers in the database
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprovers() throws Exception {
        // Initialize the database
        approversRepository.saveAndFlush(approvers);

        int databaseSizeBeforeDelete = approversRepository.findAll().size();

        // Delete the approvers
        restApproversMockMvc
            .perform(delete(ENTITY_API_URL_ID, approvers.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Approvers> approversList = approversRepository.findAll();
        assertThat(approversList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
