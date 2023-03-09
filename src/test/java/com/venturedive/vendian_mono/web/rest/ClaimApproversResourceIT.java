package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.ClaimApprovers;
import com.venturedive.vendian_mono.domain.ClaimRequests;
import com.venturedive.vendian_mono.domain.ClaimStatus;
import com.venturedive.vendian_mono.repository.ClaimApproversRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimApproversCriteria;
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
 * Integration tests for the {@link ClaimApproversResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimApproversResourceIT {

    private static final Integer DEFAULT_REFERENCEID = 1;
    private static final Integer UPDATED_REFERENCEID = 2;
    private static final Integer SMALLER_REFERENCEID = 1 - 1;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_APPROVEORDER = 1;
    private static final Integer UPDATED_APPROVEORDER = 2;
    private static final Integer SMALLER_APPROVEORDER = 1 - 1;

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVEDBY = "AAAAAAAAAA";
    private static final String UPDATED_APPROVEDBY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/claim-approvers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimApproversRepository claimApproversRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimApproversMockMvc;

    private ClaimApprovers claimApprovers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimApprovers createEntity(EntityManager em) {
        ClaimApprovers claimApprovers = new ClaimApprovers()
            .referenceid(DEFAULT_REFERENCEID)
            .designation(DEFAULT_DESIGNATION)
            .approveorder(DEFAULT_APPROVEORDER)
            .reference(DEFAULT_REFERENCE)
            .comments(DEFAULT_COMMENTS)
            .approvedby(DEFAULT_APPROVEDBY)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return claimApprovers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimApprovers createUpdatedEntity(EntityManager em) {
        ClaimApprovers claimApprovers = new ClaimApprovers()
            .referenceid(UPDATED_REFERENCEID)
            .designation(UPDATED_DESIGNATION)
            .approveorder(UPDATED_APPROVEORDER)
            .reference(UPDATED_REFERENCE)
            .comments(UPDATED_COMMENTS)
            .approvedby(UPDATED_APPROVEDBY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return claimApprovers;
    }

    @BeforeEach
    public void initTest() {
        claimApprovers = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimApprovers() throws Exception {
        int databaseSizeBeforeCreate = claimApproversRepository.findAll().size();
        // Create the ClaimApprovers
        restClaimApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimApprovers))
            )
            .andExpect(status().isCreated());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimApprovers testClaimApprovers = claimApproversList.get(claimApproversList.size() - 1);
        assertThat(testClaimApprovers.getReferenceid()).isEqualTo(DEFAULT_REFERENCEID);
        assertThat(testClaimApprovers.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testClaimApprovers.getApproveorder()).isEqualTo(DEFAULT_APPROVEORDER);
        assertThat(testClaimApprovers.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testClaimApprovers.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testClaimApprovers.getApprovedby()).isEqualTo(DEFAULT_APPROVEDBY);
        assertThat(testClaimApprovers.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimApprovers.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createClaimApproversWithExistingId() throws Exception {
        // Create the ClaimApprovers with an existing ID
        claimApprovers.setId(1L);

        int databaseSizeBeforeCreate = claimApproversRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimApproversMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaimApprovers() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList
        restClaimApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimApprovers.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceid").value(hasItem(DEFAULT_REFERENCEID)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].approveorder").value(hasItem(DEFAULT_APPROVEORDER)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].approvedby").value(hasItem(DEFAULT_APPROVEDBY)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getClaimApprovers() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get the claimApprovers
        restClaimApproversMockMvc
            .perform(get(ENTITY_API_URL_ID, claimApprovers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimApprovers.getId().intValue()))
            .andExpect(jsonPath("$.referenceid").value(DEFAULT_REFERENCEID))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.approveorder").value(DEFAULT_APPROVEORDER))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.approvedby").value(DEFAULT_APPROVEDBY))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getClaimApproversByIdFiltering() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        Long id = claimApprovers.getId();

        defaultClaimApproversShouldBeFound("id.equals=" + id);
        defaultClaimApproversShouldNotBeFound("id.notEquals=" + id);

        defaultClaimApproversShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClaimApproversShouldNotBeFound("id.greaterThan=" + id);

        defaultClaimApproversShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClaimApproversShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceidIsEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where referenceid equals to DEFAULT_REFERENCEID
        defaultClaimApproversShouldBeFound("referenceid.equals=" + DEFAULT_REFERENCEID);

        // Get all the claimApproversList where referenceid equals to UPDATED_REFERENCEID
        defaultClaimApproversShouldNotBeFound("referenceid.equals=" + UPDATED_REFERENCEID);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceidIsInShouldWork() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where referenceid in DEFAULT_REFERENCEID or UPDATED_REFERENCEID
        defaultClaimApproversShouldBeFound("referenceid.in=" + DEFAULT_REFERENCEID + "," + UPDATED_REFERENCEID);

        // Get all the claimApproversList where referenceid equals to UPDATED_REFERENCEID
        defaultClaimApproversShouldNotBeFound("referenceid.in=" + UPDATED_REFERENCEID);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceidIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where referenceid is not null
        defaultClaimApproversShouldBeFound("referenceid.specified=true");

        // Get all the claimApproversList where referenceid is null
        defaultClaimApproversShouldNotBeFound("referenceid.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where referenceid is greater than or equal to DEFAULT_REFERENCEID
        defaultClaimApproversShouldBeFound("referenceid.greaterThanOrEqual=" + DEFAULT_REFERENCEID);

        // Get all the claimApproversList where referenceid is greater than or equal to UPDATED_REFERENCEID
        defaultClaimApproversShouldNotBeFound("referenceid.greaterThanOrEqual=" + UPDATED_REFERENCEID);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where referenceid is less than or equal to DEFAULT_REFERENCEID
        defaultClaimApproversShouldBeFound("referenceid.lessThanOrEqual=" + DEFAULT_REFERENCEID);

        // Get all the claimApproversList where referenceid is less than or equal to SMALLER_REFERENCEID
        defaultClaimApproversShouldNotBeFound("referenceid.lessThanOrEqual=" + SMALLER_REFERENCEID);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceidIsLessThanSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where referenceid is less than DEFAULT_REFERENCEID
        defaultClaimApproversShouldNotBeFound("referenceid.lessThan=" + DEFAULT_REFERENCEID);

        // Get all the claimApproversList where referenceid is less than UPDATED_REFERENCEID
        defaultClaimApproversShouldBeFound("referenceid.lessThan=" + UPDATED_REFERENCEID);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where referenceid is greater than DEFAULT_REFERENCEID
        defaultClaimApproversShouldNotBeFound("referenceid.greaterThan=" + DEFAULT_REFERENCEID);

        // Get all the claimApproversList where referenceid is greater than SMALLER_REFERENCEID
        defaultClaimApproversShouldBeFound("referenceid.greaterThan=" + SMALLER_REFERENCEID);
    }

    @Test
    @Transactional
    void getAllClaimApproversByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where designation equals to DEFAULT_DESIGNATION
        defaultClaimApproversShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the claimApproversList where designation equals to UPDATED_DESIGNATION
        defaultClaimApproversShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimApproversByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultClaimApproversShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the claimApproversList where designation equals to UPDATED_DESIGNATION
        defaultClaimApproversShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimApproversByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where designation is not null
        defaultClaimApproversShouldBeFound("designation.specified=true");

        // Get all the claimApproversList where designation is null
        defaultClaimApproversShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimApproversByDesignationContainsSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where designation contains DEFAULT_DESIGNATION
        defaultClaimApproversShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the claimApproversList where designation contains UPDATED_DESIGNATION
        defaultClaimApproversShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimApproversByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where designation does not contain DEFAULT_DESIGNATION
        defaultClaimApproversShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the claimApproversList where designation does not contain UPDATED_DESIGNATION
        defaultClaimApproversShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApproveorderIsEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approveorder equals to DEFAULT_APPROVEORDER
        defaultClaimApproversShouldBeFound("approveorder.equals=" + DEFAULT_APPROVEORDER);

        // Get all the claimApproversList where approveorder equals to UPDATED_APPROVEORDER
        defaultClaimApproversShouldNotBeFound("approveorder.equals=" + UPDATED_APPROVEORDER);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApproveorderIsInShouldWork() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approveorder in DEFAULT_APPROVEORDER or UPDATED_APPROVEORDER
        defaultClaimApproversShouldBeFound("approveorder.in=" + DEFAULT_APPROVEORDER + "," + UPDATED_APPROVEORDER);

        // Get all the claimApproversList where approveorder equals to UPDATED_APPROVEORDER
        defaultClaimApproversShouldNotBeFound("approveorder.in=" + UPDATED_APPROVEORDER);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApproveorderIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approveorder is not null
        defaultClaimApproversShouldBeFound("approveorder.specified=true");

        // Get all the claimApproversList where approveorder is null
        defaultClaimApproversShouldNotBeFound("approveorder.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimApproversByApproveorderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approveorder is greater than or equal to DEFAULT_APPROVEORDER
        defaultClaimApproversShouldBeFound("approveorder.greaterThanOrEqual=" + DEFAULT_APPROVEORDER);

        // Get all the claimApproversList where approveorder is greater than or equal to UPDATED_APPROVEORDER
        defaultClaimApproversShouldNotBeFound("approveorder.greaterThanOrEqual=" + UPDATED_APPROVEORDER);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApproveorderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approveorder is less than or equal to DEFAULT_APPROVEORDER
        defaultClaimApproversShouldBeFound("approveorder.lessThanOrEqual=" + DEFAULT_APPROVEORDER);

        // Get all the claimApproversList where approveorder is less than or equal to SMALLER_APPROVEORDER
        defaultClaimApproversShouldNotBeFound("approveorder.lessThanOrEqual=" + SMALLER_APPROVEORDER);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApproveorderIsLessThanSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approveorder is less than DEFAULT_APPROVEORDER
        defaultClaimApproversShouldNotBeFound("approveorder.lessThan=" + DEFAULT_APPROVEORDER);

        // Get all the claimApproversList where approveorder is less than UPDATED_APPROVEORDER
        defaultClaimApproversShouldBeFound("approveorder.lessThan=" + UPDATED_APPROVEORDER);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApproveorderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approveorder is greater than DEFAULT_APPROVEORDER
        defaultClaimApproversShouldNotBeFound("approveorder.greaterThan=" + DEFAULT_APPROVEORDER);

        // Get all the claimApproversList where approveorder is greater than SMALLER_APPROVEORDER
        defaultClaimApproversShouldBeFound("approveorder.greaterThan=" + SMALLER_APPROVEORDER);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where reference equals to DEFAULT_REFERENCE
        defaultClaimApproversShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the claimApproversList where reference equals to UPDATED_REFERENCE
        defaultClaimApproversShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultClaimApproversShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the claimApproversList where reference equals to UPDATED_REFERENCE
        defaultClaimApproversShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where reference is not null
        defaultClaimApproversShouldBeFound("reference.specified=true");

        // Get all the claimApproversList where reference is null
        defaultClaimApproversShouldNotBeFound("reference.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceContainsSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where reference contains DEFAULT_REFERENCE
        defaultClaimApproversShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the claimApproversList where reference contains UPDATED_REFERENCE
        defaultClaimApproversShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllClaimApproversByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where reference does not contain DEFAULT_REFERENCE
        defaultClaimApproversShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the claimApproversList where reference does not contain UPDATED_REFERENCE
        defaultClaimApproversShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllClaimApproversByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where comments equals to DEFAULT_COMMENTS
        defaultClaimApproversShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the claimApproversList where comments equals to UPDATED_COMMENTS
        defaultClaimApproversShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimApproversByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultClaimApproversShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the claimApproversList where comments equals to UPDATED_COMMENTS
        defaultClaimApproversShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimApproversByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where comments is not null
        defaultClaimApproversShouldBeFound("comments.specified=true");

        // Get all the claimApproversList where comments is null
        defaultClaimApproversShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimApproversByCommentsContainsSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where comments contains DEFAULT_COMMENTS
        defaultClaimApproversShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the claimApproversList where comments contains UPDATED_COMMENTS
        defaultClaimApproversShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimApproversByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where comments does not contain DEFAULT_COMMENTS
        defaultClaimApproversShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the claimApproversList where comments does not contain UPDATED_COMMENTS
        defaultClaimApproversShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApprovedbyIsEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approvedby equals to DEFAULT_APPROVEDBY
        defaultClaimApproversShouldBeFound("approvedby.equals=" + DEFAULT_APPROVEDBY);

        // Get all the claimApproversList where approvedby equals to UPDATED_APPROVEDBY
        defaultClaimApproversShouldNotBeFound("approvedby.equals=" + UPDATED_APPROVEDBY);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApprovedbyIsInShouldWork() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approvedby in DEFAULT_APPROVEDBY or UPDATED_APPROVEDBY
        defaultClaimApproversShouldBeFound("approvedby.in=" + DEFAULT_APPROVEDBY + "," + UPDATED_APPROVEDBY);

        // Get all the claimApproversList where approvedby equals to UPDATED_APPROVEDBY
        defaultClaimApproversShouldNotBeFound("approvedby.in=" + UPDATED_APPROVEDBY);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApprovedbyIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approvedby is not null
        defaultClaimApproversShouldBeFound("approvedby.specified=true");

        // Get all the claimApproversList where approvedby is null
        defaultClaimApproversShouldNotBeFound("approvedby.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimApproversByApprovedbyContainsSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approvedby contains DEFAULT_APPROVEDBY
        defaultClaimApproversShouldBeFound("approvedby.contains=" + DEFAULT_APPROVEDBY);

        // Get all the claimApproversList where approvedby contains UPDATED_APPROVEDBY
        defaultClaimApproversShouldNotBeFound("approvedby.contains=" + UPDATED_APPROVEDBY);
    }

    @Test
    @Transactional
    void getAllClaimApproversByApprovedbyNotContainsSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where approvedby does not contain DEFAULT_APPROVEDBY
        defaultClaimApproversShouldNotBeFound("approvedby.doesNotContain=" + DEFAULT_APPROVEDBY);

        // Get all the claimApproversList where approvedby does not contain UPDATED_APPROVEDBY
        defaultClaimApproversShouldBeFound("approvedby.doesNotContain=" + UPDATED_APPROVEDBY);
    }

    @Test
    @Transactional
    void getAllClaimApproversByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where createdat equals to DEFAULT_CREATEDAT
        defaultClaimApproversShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the claimApproversList where createdat equals to UPDATED_CREATEDAT
        defaultClaimApproversShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimApproversByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultClaimApproversShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the claimApproversList where createdat equals to UPDATED_CREATEDAT
        defaultClaimApproversShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimApproversByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where createdat is not null
        defaultClaimApproversShouldBeFound("createdat.specified=true");

        // Get all the claimApproversList where createdat is null
        defaultClaimApproversShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimApproversByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where updatedat equals to DEFAULT_UPDATEDAT
        defaultClaimApproversShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the claimApproversList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimApproversShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimApproversByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultClaimApproversShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the claimApproversList where updatedat equals to UPDATED_UPDATEDAT
        defaultClaimApproversShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllClaimApproversByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        // Get all the claimApproversList where updatedat is not null
        defaultClaimApproversShouldBeFound("updatedat.specified=true");

        // Get all the claimApproversList where updatedat is null
        defaultClaimApproversShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimApproversByStatusIsEqualToSomething() throws Exception {
        ClaimStatus status;
        if (TestUtil.findAll(em, ClaimStatus.class).isEmpty()) {
            claimApproversRepository.saveAndFlush(claimApprovers);
            status = ClaimStatusResourceIT.createEntity(em);
        } else {
            status = TestUtil.findAll(em, ClaimStatus.class).get(0);
        }
        em.persist(status);
        em.flush();
        claimApprovers.setStatus(status);
        claimApproversRepository.saveAndFlush(claimApprovers);
        Long statusId = status.getId();

        // Get all the claimApproversList where status equals to statusId
        defaultClaimApproversShouldBeFound("statusId.equals=" + statusId);

        // Get all the claimApproversList where status equals to (statusId + 1)
        defaultClaimApproversShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    @Test
    @Transactional
    void getAllClaimApproversByClaimrequestIsEqualToSomething() throws Exception {
        ClaimRequests claimrequest;
        if (TestUtil.findAll(em, ClaimRequests.class).isEmpty()) {
            claimApproversRepository.saveAndFlush(claimApprovers);
            claimrequest = ClaimRequestsResourceIT.createEntity(em);
        } else {
            claimrequest = TestUtil.findAll(em, ClaimRequests.class).get(0);
        }
        em.persist(claimrequest);
        em.flush();
        claimApprovers.setClaimrequest(claimrequest);
        claimApproversRepository.saveAndFlush(claimApprovers);
        Long claimrequestId = claimrequest.getId();

        // Get all the claimApproversList where claimrequest equals to claimrequestId
        defaultClaimApproversShouldBeFound("claimrequestId.equals=" + claimrequestId);

        // Get all the claimApproversList where claimrequest equals to (claimrequestId + 1)
        defaultClaimApproversShouldNotBeFound("claimrequestId.equals=" + (claimrequestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClaimApproversShouldBeFound(String filter) throws Exception {
        restClaimApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimApprovers.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceid").value(hasItem(DEFAULT_REFERENCEID)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].approveorder").value(hasItem(DEFAULT_APPROVEORDER)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].approvedby").value(hasItem(DEFAULT_APPROVEDBY)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restClaimApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClaimApproversShouldNotBeFound(String filter) throws Exception {
        restClaimApproversMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClaimApproversMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClaimApprovers() throws Exception {
        // Get the claimApprovers
        restClaimApproversMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaimApprovers() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();

        // Update the claimApprovers
        ClaimApprovers updatedClaimApprovers = claimApproversRepository.findById(claimApprovers.getId()).get();
        // Disconnect from session so that the updates on updatedClaimApprovers are not directly saved in db
        em.detach(updatedClaimApprovers);
        updatedClaimApprovers
            .referenceid(UPDATED_REFERENCEID)
            .designation(UPDATED_DESIGNATION)
            .approveorder(UPDATED_APPROVEORDER)
            .reference(UPDATED_REFERENCE)
            .comments(UPDATED_COMMENTS)
            .approvedby(UPDATED_APPROVEDBY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimApprovers))
            )
            .andExpect(status().isOk());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
        ClaimApprovers testClaimApprovers = claimApproversList.get(claimApproversList.size() - 1);
        assertThat(testClaimApprovers.getReferenceid()).isEqualTo(UPDATED_REFERENCEID);
        assertThat(testClaimApprovers.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testClaimApprovers.getApproveorder()).isEqualTo(UPDATED_APPROVEORDER);
        assertThat(testClaimApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testClaimApprovers.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimApprovers.getApprovedby()).isEqualTo(UPDATED_APPROVEDBY);
        assertThat(testClaimApprovers.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimApprovers.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingClaimApprovers() throws Exception {
        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();
        claimApprovers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimApprovers.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimApprovers() throws Exception {
        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();
        claimApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimApproversMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimApprovers() throws Exception {
        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();
        claimApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimApproversMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimApprovers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimApproversWithPatch() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();

        // Update the claimApprovers using partial update
        ClaimApprovers partialUpdatedClaimApprovers = new ClaimApprovers();
        partialUpdatedClaimApprovers.setId(claimApprovers.getId());

        partialUpdatedClaimApprovers
            .designation(UPDATED_DESIGNATION)
            .approveorder(UPDATED_APPROVEORDER)
            .reference(UPDATED_REFERENCE)
            .comments(UPDATED_COMMENTS)
            .approvedby(UPDATED_APPROVEDBY)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimApprovers))
            )
            .andExpect(status().isOk());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
        ClaimApprovers testClaimApprovers = claimApproversList.get(claimApproversList.size() - 1);
        assertThat(testClaimApprovers.getReferenceid()).isEqualTo(DEFAULT_REFERENCEID);
        assertThat(testClaimApprovers.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testClaimApprovers.getApproveorder()).isEqualTo(UPDATED_APPROVEORDER);
        assertThat(testClaimApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testClaimApprovers.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimApprovers.getApprovedby()).isEqualTo(UPDATED_APPROVEDBY);
        assertThat(testClaimApprovers.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testClaimApprovers.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateClaimApproversWithPatch() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();

        // Update the claimApprovers using partial update
        ClaimApprovers partialUpdatedClaimApprovers = new ClaimApprovers();
        partialUpdatedClaimApprovers.setId(claimApprovers.getId());

        partialUpdatedClaimApprovers
            .referenceid(UPDATED_REFERENCEID)
            .designation(UPDATED_DESIGNATION)
            .approveorder(UPDATED_APPROVEORDER)
            .reference(UPDATED_REFERENCE)
            .comments(UPDATED_COMMENTS)
            .approvedby(UPDATED_APPROVEDBY)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restClaimApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimApprovers))
            )
            .andExpect(status().isOk());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
        ClaimApprovers testClaimApprovers = claimApproversList.get(claimApproversList.size() - 1);
        assertThat(testClaimApprovers.getReferenceid()).isEqualTo(UPDATED_REFERENCEID);
        assertThat(testClaimApprovers.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testClaimApprovers.getApproveorder()).isEqualTo(UPDATED_APPROVEORDER);
        assertThat(testClaimApprovers.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testClaimApprovers.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClaimApprovers.getApprovedby()).isEqualTo(UPDATED_APPROVEDBY);
        assertThat(testClaimApprovers.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testClaimApprovers.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingClaimApprovers() throws Exception {
        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();
        claimApprovers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimApprovers.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimApprovers() throws Exception {
        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();
        claimApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimApproversMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimApprovers))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimApprovers() throws Exception {
        int databaseSizeBeforeUpdate = claimApproversRepository.findAll().size();
        claimApprovers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimApproversMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimApprovers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimApprovers in the database
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimApprovers() throws Exception {
        // Initialize the database
        claimApproversRepository.saveAndFlush(claimApprovers);

        int databaseSizeBeforeDelete = claimApproversRepository.findAll().size();

        // Delete the claimApprovers
        restClaimApproversMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimApprovers.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimApprovers> claimApproversList = claimApproversRepository.findAll();
        assertThat(claimApproversList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
