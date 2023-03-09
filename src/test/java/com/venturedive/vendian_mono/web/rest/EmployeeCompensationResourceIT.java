package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.CompensationBenefits;
import com.venturedive.vendian_mono.domain.EmployeeCompensation;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Reasons;
import com.venturedive.vendian_mono.repository.EmployeeCompensationRepository;
import com.venturedive.vendian_mono.service.EmployeeCompensationService;
import com.venturedive.vendian_mono.service.criteria.EmployeeCompensationCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EmployeeCompensationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmployeeCompensationResourceIT {

    private static final byte[] DEFAULT_AMOUNT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AMOUNT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AMOUNT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AMOUNT_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_EC_REASON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EC_REASON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EC_REASON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EC_REASON_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    private static final Instant DEFAULT_COMMITMENTUNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMMITMENTUNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASONCOMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_REASONCOMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-compensations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeCompensationRepository employeeCompensationRepository;

    @Mock
    private EmployeeCompensationRepository employeeCompensationRepositoryMock;

    @Mock
    private EmployeeCompensationService employeeCompensationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeCompensationMockMvc;

    private EmployeeCompensation employeeCompensation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCompensation createEntity(EntityManager em) {
        EmployeeCompensation employeeCompensation = new EmployeeCompensation()
            .amount(DEFAULT_AMOUNT)
            .amountContentType(DEFAULT_AMOUNT_CONTENT_TYPE)
            .date(DEFAULT_DATE)
            .ecReason(DEFAULT_EC_REASON)
            .ecReasonContentType(DEFAULT_EC_REASON_CONTENT_TYPE)
            .type(DEFAULT_TYPE)
            .commitmentuntil(DEFAULT_COMMITMENTUNTIL)
            .comments(DEFAULT_COMMENTS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .reasoncomments(DEFAULT_REASONCOMMENTS);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeCompensation.setEmployee(employees);
        return employeeCompensation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCompensation createUpdatedEntity(EntityManager em) {
        EmployeeCompensation employeeCompensation = new EmployeeCompensation()
            .amount(UPDATED_AMOUNT)
            .amountContentType(UPDATED_AMOUNT_CONTENT_TYPE)
            .date(UPDATED_DATE)
            .ecReason(UPDATED_EC_REASON)
            .ecReasonContentType(UPDATED_EC_REASON_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .commitmentuntil(UPDATED_COMMITMENTUNTIL)
            .comments(UPDATED_COMMENTS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .reasoncomments(UPDATED_REASONCOMMENTS);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeCompensation.setEmployee(employees);
        return employeeCompensation;
    }

    @BeforeEach
    public void initTest() {
        employeeCompensation = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeCompensation() throws Exception {
        int databaseSizeBeforeCreate = employeeCompensationRepository.findAll().size();
        // Create the EmployeeCompensation
        restEmployeeCompensationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeCompensation testEmployeeCompensation = employeeCompensationList.get(employeeCompensationList.size() - 1);
        assertThat(testEmployeeCompensation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEmployeeCompensation.getAmountContentType()).isEqualTo(DEFAULT_AMOUNT_CONTENT_TYPE);
        assertThat(testEmployeeCompensation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEmployeeCompensation.getEcReason()).isEqualTo(DEFAULT_EC_REASON);
        assertThat(testEmployeeCompensation.getEcReasonContentType()).isEqualTo(DEFAULT_EC_REASON_CONTENT_TYPE);
        assertThat(testEmployeeCompensation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployeeCompensation.getCommitmentuntil()).isEqualTo(DEFAULT_COMMITMENTUNTIL);
        assertThat(testEmployeeCompensation.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testEmployeeCompensation.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeCompensation.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeCompensation.getReasoncomments()).isEqualTo(DEFAULT_REASONCOMMENTS);
    }

    @Test
    @Transactional
    void createEmployeeCompensationWithExistingId() throws Exception {
        // Create the EmployeeCompensation with an existing ID
        employeeCompensation.setId(1L);

        int databaseSizeBeforeCreate = employeeCompensationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeCompensationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeCompensationRepository.findAll().size();
        // set the field null
        employeeCompensation.setDate(null);

        // Create the EmployeeCompensation, which fails.

        restEmployeeCompensationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeCompensationRepository.findAll().size();
        // set the field null
        employeeCompensation.setType(null);

        // Create the EmployeeCompensation, which fails.

        restEmployeeCompensationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensations() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList
        restEmployeeCompensationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeCompensation.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountContentType").value(hasItem(DEFAULT_AMOUNT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(Base64Utils.encodeToString(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].ecReasonContentType").value(hasItem(DEFAULT_EC_REASON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ecReason").value(hasItem(Base64Utils.encodeToString(DEFAULT_EC_REASON))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].commitmentuntil").value(hasItem(DEFAULT_COMMITMENTUNTIL.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].reasoncomments").value(hasItem(DEFAULT_REASONCOMMENTS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeeCompensationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(employeeCompensationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeeCompensationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(employeeCompensationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeeCompensationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(employeeCompensationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeeCompensationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(employeeCompensationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmployeeCompensation() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get the employeeCompensation
        restEmployeeCompensationMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeCompensation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeCompensation.getId().intValue()))
            .andExpect(jsonPath("$.amountContentType").value(DEFAULT_AMOUNT_CONTENT_TYPE))
            .andExpect(jsonPath("$.amount").value(Base64Utils.encodeToString(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.ecReasonContentType").value(DEFAULT_EC_REASON_CONTENT_TYPE))
            .andExpect(jsonPath("$.ecReason").value(Base64Utils.encodeToString(DEFAULT_EC_REASON)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.commitmentuntil").value(DEFAULT_COMMITMENTUNTIL.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.reasoncomments").value(DEFAULT_REASONCOMMENTS));
    }

    @Test
    @Transactional
    void getEmployeeCompensationsByIdFiltering() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        Long id = employeeCompensation.getId();

        defaultEmployeeCompensationShouldBeFound("id.equals=" + id);
        defaultEmployeeCompensationShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeCompensationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeCompensationShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeCompensationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeCompensationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where date equals to DEFAULT_DATE
        defaultEmployeeCompensationShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the employeeCompensationList where date equals to UPDATED_DATE
        defaultEmployeeCompensationShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEmployeeCompensationShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the employeeCompensationList where date equals to UPDATED_DATE
        defaultEmployeeCompensationShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where date is not null
        defaultEmployeeCompensationShouldBeFound("date.specified=true");

        // Get all the employeeCompensationList where date is null
        defaultEmployeeCompensationShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where type equals to DEFAULT_TYPE
        defaultEmployeeCompensationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the employeeCompensationList where type equals to UPDATED_TYPE
        defaultEmployeeCompensationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmployeeCompensationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the employeeCompensationList where type equals to UPDATED_TYPE
        defaultEmployeeCompensationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where type is not null
        defaultEmployeeCompensationShouldBeFound("type.specified=true");

        // Get all the employeeCompensationList where type is null
        defaultEmployeeCompensationShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByTypeContainsSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where type contains DEFAULT_TYPE
        defaultEmployeeCompensationShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the employeeCompensationList where type contains UPDATED_TYPE
        defaultEmployeeCompensationShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where type does not contain DEFAULT_TYPE
        defaultEmployeeCompensationShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the employeeCompensationList where type does not contain UPDATED_TYPE
        defaultEmployeeCompensationShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCommitmentuntilIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where commitmentuntil equals to DEFAULT_COMMITMENTUNTIL
        defaultEmployeeCompensationShouldBeFound("commitmentuntil.equals=" + DEFAULT_COMMITMENTUNTIL);

        // Get all the employeeCompensationList where commitmentuntil equals to UPDATED_COMMITMENTUNTIL
        defaultEmployeeCompensationShouldNotBeFound("commitmentuntil.equals=" + UPDATED_COMMITMENTUNTIL);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCommitmentuntilIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where commitmentuntil in DEFAULT_COMMITMENTUNTIL or UPDATED_COMMITMENTUNTIL
        defaultEmployeeCompensationShouldBeFound("commitmentuntil.in=" + DEFAULT_COMMITMENTUNTIL + "," + UPDATED_COMMITMENTUNTIL);

        // Get all the employeeCompensationList where commitmentuntil equals to UPDATED_COMMITMENTUNTIL
        defaultEmployeeCompensationShouldNotBeFound("commitmentuntil.in=" + UPDATED_COMMITMENTUNTIL);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCommitmentuntilIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where commitmentuntil is not null
        defaultEmployeeCompensationShouldBeFound("commitmentuntil.specified=true");

        // Get all the employeeCompensationList where commitmentuntil is null
        defaultEmployeeCompensationShouldNotBeFound("commitmentuntil.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where comments equals to DEFAULT_COMMENTS
        defaultEmployeeCompensationShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the employeeCompensationList where comments equals to UPDATED_COMMENTS
        defaultEmployeeCompensationShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultEmployeeCompensationShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the employeeCompensationList where comments equals to UPDATED_COMMENTS
        defaultEmployeeCompensationShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where comments is not null
        defaultEmployeeCompensationShouldBeFound("comments.specified=true");

        // Get all the employeeCompensationList where comments is null
        defaultEmployeeCompensationShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where comments contains DEFAULT_COMMENTS
        defaultEmployeeCompensationShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the employeeCompensationList where comments contains UPDATED_COMMENTS
        defaultEmployeeCompensationShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where comments does not contain DEFAULT_COMMENTS
        defaultEmployeeCompensationShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the employeeCompensationList where comments does not contain UPDATED_COMMENTS
        defaultEmployeeCompensationShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeCompensationShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeCompensationList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeCompensationShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeCompensationShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeCompensationList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeCompensationShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where createdat is not null
        defaultEmployeeCompensationShouldBeFound("createdat.specified=true");

        // Get all the employeeCompensationList where createdat is null
        defaultEmployeeCompensationShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeCompensationShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeCompensationList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeCompensationShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeCompensationShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeCompensationList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeCompensationShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where updatedat is not null
        defaultEmployeeCompensationShouldBeFound("updatedat.specified=true");

        // Get all the employeeCompensationList where updatedat is null
        defaultEmployeeCompensationShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByReasoncommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where reasoncomments equals to DEFAULT_REASONCOMMENTS
        defaultEmployeeCompensationShouldBeFound("reasoncomments.equals=" + DEFAULT_REASONCOMMENTS);

        // Get all the employeeCompensationList where reasoncomments equals to UPDATED_REASONCOMMENTS
        defaultEmployeeCompensationShouldNotBeFound("reasoncomments.equals=" + UPDATED_REASONCOMMENTS);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByReasoncommentsIsInShouldWork() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where reasoncomments in DEFAULT_REASONCOMMENTS or UPDATED_REASONCOMMENTS
        defaultEmployeeCompensationShouldBeFound("reasoncomments.in=" + DEFAULT_REASONCOMMENTS + "," + UPDATED_REASONCOMMENTS);

        // Get all the employeeCompensationList where reasoncomments equals to UPDATED_REASONCOMMENTS
        defaultEmployeeCompensationShouldNotBeFound("reasoncomments.in=" + UPDATED_REASONCOMMENTS);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByReasoncommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where reasoncomments is not null
        defaultEmployeeCompensationShouldBeFound("reasoncomments.specified=true");

        // Get all the employeeCompensationList where reasoncomments is null
        defaultEmployeeCompensationShouldNotBeFound("reasoncomments.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByReasoncommentsContainsSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where reasoncomments contains DEFAULT_REASONCOMMENTS
        defaultEmployeeCompensationShouldBeFound("reasoncomments.contains=" + DEFAULT_REASONCOMMENTS);

        // Get all the employeeCompensationList where reasoncomments contains UPDATED_REASONCOMMENTS
        defaultEmployeeCompensationShouldNotBeFound("reasoncomments.contains=" + UPDATED_REASONCOMMENTS);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByReasoncommentsNotContainsSomething() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        // Get all the employeeCompensationList where reasoncomments does not contain DEFAULT_REASONCOMMENTS
        defaultEmployeeCompensationShouldNotBeFound("reasoncomments.doesNotContain=" + DEFAULT_REASONCOMMENTS);

        // Get all the employeeCompensationList where reasoncomments does not contain UPDATED_REASONCOMMENTS
        defaultEmployeeCompensationShouldBeFound("reasoncomments.doesNotContain=" + UPDATED_REASONCOMMENTS);
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeCompensationRepository.saveAndFlush(employeeCompensation);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeCompensation.setEmployee(employee);
        employeeCompensationRepository.saveAndFlush(employeeCompensation);
        Long employeeId = employee.getId();

        // Get all the employeeCompensationList where employee equals to employeeId
        defaultEmployeeCompensationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeCompensationList where employee equals to (employeeId + 1)
        defaultEmployeeCompensationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByReasonIsEqualToSomething() throws Exception {
        Reasons reason;
        if (TestUtil.findAll(em, Reasons.class).isEmpty()) {
            employeeCompensationRepository.saveAndFlush(employeeCompensation);
            reason = ReasonsResourceIT.createEntity(em);
        } else {
            reason = TestUtil.findAll(em, Reasons.class).get(0);
        }
        em.persist(reason);
        em.flush();
        employeeCompensation.setReason(reason);
        employeeCompensationRepository.saveAndFlush(employeeCompensation);
        Long reasonId = reason.getId();

        // Get all the employeeCompensationList where reason equals to reasonId
        defaultEmployeeCompensationShouldBeFound("reasonId.equals=" + reasonId);

        // Get all the employeeCompensationList where reason equals to (reasonId + 1)
        defaultEmployeeCompensationShouldNotBeFound("reasonId.equals=" + (reasonId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeCompensationsByCompensationbenefitsEmployeecompensationIsEqualToSomething() throws Exception {
        CompensationBenefits compensationbenefitsEmployeecompensation;
        if (TestUtil.findAll(em, CompensationBenefits.class).isEmpty()) {
            employeeCompensationRepository.saveAndFlush(employeeCompensation);
            compensationbenefitsEmployeecompensation = CompensationBenefitsResourceIT.createEntity(em);
        } else {
            compensationbenefitsEmployeecompensation = TestUtil.findAll(em, CompensationBenefits.class).get(0);
        }
        em.persist(compensationbenefitsEmployeecompensation);
        em.flush();
        employeeCompensation.addCompensationbenefitsEmployeecompensation(compensationbenefitsEmployeecompensation);
        employeeCompensationRepository.saveAndFlush(employeeCompensation);
        Long compensationbenefitsEmployeecompensationId = compensationbenefitsEmployeecompensation.getId();

        // Get all the employeeCompensationList where compensationbenefitsEmployeecompensation equals to compensationbenefitsEmployeecompensationId
        defaultEmployeeCompensationShouldBeFound(
            "compensationbenefitsEmployeecompensationId.equals=" + compensationbenefitsEmployeecompensationId
        );

        // Get all the employeeCompensationList where compensationbenefitsEmployeecompensation equals to (compensationbenefitsEmployeecompensationId + 1)
        defaultEmployeeCompensationShouldNotBeFound(
            "compensationbenefitsEmployeecompensationId.equals=" + (compensationbenefitsEmployeecompensationId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeCompensationShouldBeFound(String filter) throws Exception {
        restEmployeeCompensationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeCompensation.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountContentType").value(hasItem(DEFAULT_AMOUNT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(Base64Utils.encodeToString(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].ecReasonContentType").value(hasItem(DEFAULT_EC_REASON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ecReason").value(hasItem(Base64Utils.encodeToString(DEFAULT_EC_REASON))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].commitmentuntil").value(hasItem(DEFAULT_COMMITMENTUNTIL.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].reasoncomments").value(hasItem(DEFAULT_REASONCOMMENTS)));

        // Check, that the count call also returns 1
        restEmployeeCompensationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeCompensationShouldNotBeFound(String filter) throws Exception {
        restEmployeeCompensationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeCompensationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeCompensation() throws Exception {
        // Get the employeeCompensation
        restEmployeeCompensationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeCompensation() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();

        // Update the employeeCompensation
        EmployeeCompensation updatedEmployeeCompensation = employeeCompensationRepository.findById(employeeCompensation.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeCompensation are not directly saved in db
        em.detach(updatedEmployeeCompensation);
        updatedEmployeeCompensation
            .amount(UPDATED_AMOUNT)
            .amountContentType(UPDATED_AMOUNT_CONTENT_TYPE)
            .date(UPDATED_DATE)
            .ecReason(UPDATED_EC_REASON)
            .ecReasonContentType(UPDATED_EC_REASON_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .commitmentuntil(UPDATED_COMMITMENTUNTIL)
            .comments(UPDATED_COMMENTS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .reasoncomments(UPDATED_REASONCOMMENTS);

        restEmployeeCompensationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeCompensation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeCompensation))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeCompensation testEmployeeCompensation = employeeCompensationList.get(employeeCompensationList.size() - 1);
        assertThat(testEmployeeCompensation.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEmployeeCompensation.getAmountContentType()).isEqualTo(UPDATED_AMOUNT_CONTENT_TYPE);
        assertThat(testEmployeeCompensation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmployeeCompensation.getEcReason()).isEqualTo(UPDATED_EC_REASON);
        assertThat(testEmployeeCompensation.getEcReasonContentType()).isEqualTo(UPDATED_EC_REASON_CONTENT_TYPE);
        assertThat(testEmployeeCompensation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployeeCompensation.getCommitmentuntil()).isEqualTo(UPDATED_COMMITMENTUNTIL);
        assertThat(testEmployeeCompensation.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEmployeeCompensation.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeCompensation.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeCompensation.getReasoncomments()).isEqualTo(UPDATED_REASONCOMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeCompensation() throws Exception {
        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();
        employeeCompensation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCompensationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeCompensation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeCompensation() throws Exception {
        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();
        employeeCompensation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCompensationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeCompensation() throws Exception {
        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();
        employeeCompensation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCompensationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeCompensationWithPatch() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();

        // Update the employeeCompensation using partial update
        EmployeeCompensation partialUpdatedEmployeeCompensation = new EmployeeCompensation();
        partialUpdatedEmployeeCompensation.setId(employeeCompensation.getId());

        partialUpdatedEmployeeCompensation.commitmentuntil(UPDATED_COMMITMENTUNTIL).updatedat(UPDATED_UPDATEDAT);

        restEmployeeCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeCompensation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeCompensation))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeCompensation testEmployeeCompensation = employeeCompensationList.get(employeeCompensationList.size() - 1);
        assertThat(testEmployeeCompensation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEmployeeCompensation.getAmountContentType()).isEqualTo(DEFAULT_AMOUNT_CONTENT_TYPE);
        assertThat(testEmployeeCompensation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEmployeeCompensation.getEcReason()).isEqualTo(DEFAULT_EC_REASON);
        assertThat(testEmployeeCompensation.getEcReasonContentType()).isEqualTo(DEFAULT_EC_REASON_CONTENT_TYPE);
        assertThat(testEmployeeCompensation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployeeCompensation.getCommitmentuntil()).isEqualTo(UPDATED_COMMITMENTUNTIL);
        assertThat(testEmployeeCompensation.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testEmployeeCompensation.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeCompensation.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeCompensation.getReasoncomments()).isEqualTo(DEFAULT_REASONCOMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeCompensationWithPatch() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();

        // Update the employeeCompensation using partial update
        EmployeeCompensation partialUpdatedEmployeeCompensation = new EmployeeCompensation();
        partialUpdatedEmployeeCompensation.setId(employeeCompensation.getId());

        partialUpdatedEmployeeCompensation
            .amount(UPDATED_AMOUNT)
            .amountContentType(UPDATED_AMOUNT_CONTENT_TYPE)
            .date(UPDATED_DATE)
            .ecReason(UPDATED_EC_REASON)
            .ecReasonContentType(UPDATED_EC_REASON_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .commitmentuntil(UPDATED_COMMITMENTUNTIL)
            .comments(UPDATED_COMMENTS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .reasoncomments(UPDATED_REASONCOMMENTS);

        restEmployeeCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeCompensation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeCompensation))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeCompensation testEmployeeCompensation = employeeCompensationList.get(employeeCompensationList.size() - 1);
        assertThat(testEmployeeCompensation.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEmployeeCompensation.getAmountContentType()).isEqualTo(UPDATED_AMOUNT_CONTENT_TYPE);
        assertThat(testEmployeeCompensation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmployeeCompensation.getEcReason()).isEqualTo(UPDATED_EC_REASON);
        assertThat(testEmployeeCompensation.getEcReasonContentType()).isEqualTo(UPDATED_EC_REASON_CONTENT_TYPE);
        assertThat(testEmployeeCompensation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployeeCompensation.getCommitmentuntil()).isEqualTo(UPDATED_COMMITMENTUNTIL);
        assertThat(testEmployeeCompensation.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEmployeeCompensation.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeCompensation.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeCompensation.getReasoncomments()).isEqualTo(UPDATED_REASONCOMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeCompensation() throws Exception {
        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();
        employeeCompensation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeCompensation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeCompensation() throws Exception {
        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();
        employeeCompensation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeCompensation() throws Exception {
        int databaseSizeBeforeUpdate = employeeCompensationRepository.findAll().size();
        employeeCompensation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCompensationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeCompensation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeCompensation in the database
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeCompensation() throws Exception {
        // Initialize the database
        employeeCompensationRepository.saveAndFlush(employeeCompensation);

        int databaseSizeBeforeDelete = employeeCompensationRepository.findAll().size();

        // Delete the employeeCompensation
        restEmployeeCompensationMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeCompensation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeCompensation> employeeCompensationList = employeeCompensationRepository.findAll();
        assertThat(employeeCompensationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
