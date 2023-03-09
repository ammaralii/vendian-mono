package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeFamilyInfo;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeFamilyInfoRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeFamilyInfoCriteria;
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
 * Integration tests for the {@link EmployeeFamilyInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeFamilyInfoResourceIT {

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIONSHIP = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTNO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTNO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DOB = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOB = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_REGISTEREDINMEDICAL = false;
    private static final Boolean UPDATED_REGISTEREDINMEDICAL = true;

    private static final byte[] DEFAULT_CNIC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CNIC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CNIC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CNIC_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MEDICALPOLICYNO = "AAAAAAAAAA";
    private static final String UPDATED_MEDICALPOLICYNO = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAA";
    private static final String UPDATED_GENDER = "BBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-family-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeFamilyInfoRepository employeeFamilyInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeFamilyInfoMockMvc;

    private EmployeeFamilyInfo employeeFamilyInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeFamilyInfo createEntity(EntityManager em) {
        EmployeeFamilyInfo employeeFamilyInfo = new EmployeeFamilyInfo()
            .fullname(DEFAULT_FULLNAME)
            .relationship(DEFAULT_RELATIONSHIP)
            .contactno(DEFAULT_CONTACTNO)
            .email(DEFAULT_EMAIL)
            .dob(DEFAULT_DOB)
            .registeredinmedical(DEFAULT_REGISTEREDINMEDICAL)
            .cnic(DEFAULT_CNIC)
            .cnicContentType(DEFAULT_CNIC_CONTENT_TYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .medicalpolicyno(DEFAULT_MEDICALPOLICYNO)
            .gender(DEFAULT_GENDER);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeFamilyInfo.setEmployee(employees);
        return employeeFamilyInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeFamilyInfo createUpdatedEntity(EntityManager em) {
        EmployeeFamilyInfo employeeFamilyInfo = new EmployeeFamilyInfo()
            .fullname(UPDATED_FULLNAME)
            .relationship(UPDATED_RELATIONSHIP)
            .contactno(UPDATED_CONTACTNO)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .registeredinmedical(UPDATED_REGISTEREDINMEDICAL)
            .cnic(UPDATED_CNIC)
            .cnicContentType(UPDATED_CNIC_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .medicalpolicyno(UPDATED_MEDICALPOLICYNO)
            .gender(UPDATED_GENDER);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeFamilyInfo.setEmployee(employees);
        return employeeFamilyInfo;
    }

    @BeforeEach
    public void initTest() {
        employeeFamilyInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeFamilyInfo() throws Exception {
        int databaseSizeBeforeCreate = employeeFamilyInfoRepository.findAll().size();
        // Create the EmployeeFamilyInfo
        restEmployeeFamilyInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeFamilyInfo testEmployeeFamilyInfo = employeeFamilyInfoList.get(employeeFamilyInfoList.size() - 1);
        assertThat(testEmployeeFamilyInfo.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testEmployeeFamilyInfo.getRelationship()).isEqualTo(DEFAULT_RELATIONSHIP);
        assertThat(testEmployeeFamilyInfo.getContactno()).isEqualTo(DEFAULT_CONTACTNO);
        assertThat(testEmployeeFamilyInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployeeFamilyInfo.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testEmployeeFamilyInfo.getRegisteredinmedical()).isEqualTo(DEFAULT_REGISTEREDINMEDICAL);
        assertThat(testEmployeeFamilyInfo.getCnic()).isEqualTo(DEFAULT_CNIC);
        assertThat(testEmployeeFamilyInfo.getCnicContentType()).isEqualTo(DEFAULT_CNIC_CONTENT_TYPE);
        assertThat(testEmployeeFamilyInfo.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeFamilyInfo.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeFamilyInfo.getMedicalpolicyno()).isEqualTo(DEFAULT_MEDICALPOLICYNO);
        assertThat(testEmployeeFamilyInfo.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void createEmployeeFamilyInfoWithExistingId() throws Exception {
        // Create the EmployeeFamilyInfo with an existing ID
        employeeFamilyInfo.setId(1L);

        int databaseSizeBeforeCreate = employeeFamilyInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeFamilyInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFullnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeFamilyInfoRepository.findAll().size();
        // set the field null
        employeeFamilyInfo.setFullname(null);

        // Create the EmployeeFamilyInfo, which fails.

        restEmployeeFamilyInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelationshipIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeFamilyInfoRepository.findAll().size();
        // set the field null
        employeeFamilyInfo.setRelationship(null);

        // Create the EmployeeFamilyInfo, which fails.

        restEmployeeFamilyInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeFamilyInfoRepository.findAll().size();
        // set the field null
        employeeFamilyInfo.setGender(null);

        // Create the EmployeeFamilyInfo, which fails.

        restEmployeeFamilyInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfos() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList
        restEmployeeFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeFamilyInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].relationship").value(hasItem(DEFAULT_RELATIONSHIP)))
            .andExpect(jsonPath("$.[*].contactno").value(hasItem(DEFAULT_CONTACTNO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].registeredinmedical").value(hasItem(DEFAULT_REGISTEREDINMEDICAL.booleanValue())))
            .andExpect(jsonPath("$.[*].cnicContentType").value(hasItem(DEFAULT_CNIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cnic").value(hasItem(Base64Utils.encodeToString(DEFAULT_CNIC))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].medicalpolicyno").value(hasItem(DEFAULT_MEDICALPOLICYNO)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)));
    }

    @Test
    @Transactional
    void getEmployeeFamilyInfo() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get the employeeFamilyInfo
        restEmployeeFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeFamilyInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeFamilyInfo.getId().intValue()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.relationship").value(DEFAULT_RELATIONSHIP))
            .andExpect(jsonPath("$.contactno").value(DEFAULT_CONTACTNO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.registeredinmedical").value(DEFAULT_REGISTEREDINMEDICAL.booleanValue()))
            .andExpect(jsonPath("$.cnicContentType").value(DEFAULT_CNIC_CONTENT_TYPE))
            .andExpect(jsonPath("$.cnic").value(Base64Utils.encodeToString(DEFAULT_CNIC)))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.medicalpolicyno").value(DEFAULT_MEDICALPOLICYNO))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER));
    }

    @Test
    @Transactional
    void getEmployeeFamilyInfosByIdFiltering() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        Long id = employeeFamilyInfo.getId();

        defaultEmployeeFamilyInfoShouldBeFound("id.equals=" + id);
        defaultEmployeeFamilyInfoShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeFamilyInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeFamilyInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeFamilyInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeFamilyInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByFullnameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where fullname equals to DEFAULT_FULLNAME
        defaultEmployeeFamilyInfoShouldBeFound("fullname.equals=" + DEFAULT_FULLNAME);

        // Get all the employeeFamilyInfoList where fullname equals to UPDATED_FULLNAME
        defaultEmployeeFamilyInfoShouldNotBeFound("fullname.equals=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByFullnameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where fullname in DEFAULT_FULLNAME or UPDATED_FULLNAME
        defaultEmployeeFamilyInfoShouldBeFound("fullname.in=" + DEFAULT_FULLNAME + "," + UPDATED_FULLNAME);

        // Get all the employeeFamilyInfoList where fullname equals to UPDATED_FULLNAME
        defaultEmployeeFamilyInfoShouldNotBeFound("fullname.in=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByFullnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where fullname is not null
        defaultEmployeeFamilyInfoShouldBeFound("fullname.specified=true");

        // Get all the employeeFamilyInfoList where fullname is null
        defaultEmployeeFamilyInfoShouldNotBeFound("fullname.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByFullnameContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where fullname contains DEFAULT_FULLNAME
        defaultEmployeeFamilyInfoShouldBeFound("fullname.contains=" + DEFAULT_FULLNAME);

        // Get all the employeeFamilyInfoList where fullname contains UPDATED_FULLNAME
        defaultEmployeeFamilyInfoShouldNotBeFound("fullname.contains=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByFullnameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where fullname does not contain DEFAULT_FULLNAME
        defaultEmployeeFamilyInfoShouldNotBeFound("fullname.doesNotContain=" + DEFAULT_FULLNAME);

        // Get all the employeeFamilyInfoList where fullname does not contain UPDATED_FULLNAME
        defaultEmployeeFamilyInfoShouldBeFound("fullname.doesNotContain=" + UPDATED_FULLNAME);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByRelationshipIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where relationship equals to DEFAULT_RELATIONSHIP
        defaultEmployeeFamilyInfoShouldBeFound("relationship.equals=" + DEFAULT_RELATIONSHIP);

        // Get all the employeeFamilyInfoList where relationship equals to UPDATED_RELATIONSHIP
        defaultEmployeeFamilyInfoShouldNotBeFound("relationship.equals=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByRelationshipIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where relationship in DEFAULT_RELATIONSHIP or UPDATED_RELATIONSHIP
        defaultEmployeeFamilyInfoShouldBeFound("relationship.in=" + DEFAULT_RELATIONSHIP + "," + UPDATED_RELATIONSHIP);

        // Get all the employeeFamilyInfoList where relationship equals to UPDATED_RELATIONSHIP
        defaultEmployeeFamilyInfoShouldNotBeFound("relationship.in=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByRelationshipIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where relationship is not null
        defaultEmployeeFamilyInfoShouldBeFound("relationship.specified=true");

        // Get all the employeeFamilyInfoList where relationship is null
        defaultEmployeeFamilyInfoShouldNotBeFound("relationship.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByRelationshipContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where relationship contains DEFAULT_RELATIONSHIP
        defaultEmployeeFamilyInfoShouldBeFound("relationship.contains=" + DEFAULT_RELATIONSHIP);

        // Get all the employeeFamilyInfoList where relationship contains UPDATED_RELATIONSHIP
        defaultEmployeeFamilyInfoShouldNotBeFound("relationship.contains=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByRelationshipNotContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where relationship does not contain DEFAULT_RELATIONSHIP
        defaultEmployeeFamilyInfoShouldNotBeFound("relationship.doesNotContain=" + DEFAULT_RELATIONSHIP);

        // Get all the employeeFamilyInfoList where relationship does not contain UPDATED_RELATIONSHIP
        defaultEmployeeFamilyInfoShouldBeFound("relationship.doesNotContain=" + UPDATED_RELATIONSHIP);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByContactnoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where contactno equals to DEFAULT_CONTACTNO
        defaultEmployeeFamilyInfoShouldBeFound("contactno.equals=" + DEFAULT_CONTACTNO);

        // Get all the employeeFamilyInfoList where contactno equals to UPDATED_CONTACTNO
        defaultEmployeeFamilyInfoShouldNotBeFound("contactno.equals=" + UPDATED_CONTACTNO);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByContactnoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where contactno in DEFAULT_CONTACTNO or UPDATED_CONTACTNO
        defaultEmployeeFamilyInfoShouldBeFound("contactno.in=" + DEFAULT_CONTACTNO + "," + UPDATED_CONTACTNO);

        // Get all the employeeFamilyInfoList where contactno equals to UPDATED_CONTACTNO
        defaultEmployeeFamilyInfoShouldNotBeFound("contactno.in=" + UPDATED_CONTACTNO);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByContactnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where contactno is not null
        defaultEmployeeFamilyInfoShouldBeFound("contactno.specified=true");

        // Get all the employeeFamilyInfoList where contactno is null
        defaultEmployeeFamilyInfoShouldNotBeFound("contactno.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByContactnoContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where contactno contains DEFAULT_CONTACTNO
        defaultEmployeeFamilyInfoShouldBeFound("contactno.contains=" + DEFAULT_CONTACTNO);

        // Get all the employeeFamilyInfoList where contactno contains UPDATED_CONTACTNO
        defaultEmployeeFamilyInfoShouldNotBeFound("contactno.contains=" + UPDATED_CONTACTNO);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByContactnoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where contactno does not contain DEFAULT_CONTACTNO
        defaultEmployeeFamilyInfoShouldNotBeFound("contactno.doesNotContain=" + DEFAULT_CONTACTNO);

        // Get all the employeeFamilyInfoList where contactno does not contain UPDATED_CONTACTNO
        defaultEmployeeFamilyInfoShouldBeFound("contactno.doesNotContain=" + UPDATED_CONTACTNO);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where email equals to DEFAULT_EMAIL
        defaultEmployeeFamilyInfoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeFamilyInfoList where email equals to UPDATED_EMAIL
        defaultEmployeeFamilyInfoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeFamilyInfoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeFamilyInfoList where email equals to UPDATED_EMAIL
        defaultEmployeeFamilyInfoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where email is not null
        defaultEmployeeFamilyInfoShouldBeFound("email.specified=true");

        // Get all the employeeFamilyInfoList where email is null
        defaultEmployeeFamilyInfoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where email contains DEFAULT_EMAIL
        defaultEmployeeFamilyInfoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeeFamilyInfoList where email contains UPDATED_EMAIL
        defaultEmployeeFamilyInfoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where email does not contain DEFAULT_EMAIL
        defaultEmployeeFamilyInfoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeeFamilyInfoList where email does not contain UPDATED_EMAIL
        defaultEmployeeFamilyInfoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where dob equals to DEFAULT_DOB
        defaultEmployeeFamilyInfoShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the employeeFamilyInfoList where dob equals to UPDATED_DOB
        defaultEmployeeFamilyInfoShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByDobIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultEmployeeFamilyInfoShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the employeeFamilyInfoList where dob equals to UPDATED_DOB
        defaultEmployeeFamilyInfoShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where dob is not null
        defaultEmployeeFamilyInfoShouldBeFound("dob.specified=true");

        // Get all the employeeFamilyInfoList where dob is null
        defaultEmployeeFamilyInfoShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByRegisteredinmedicalIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where registeredinmedical equals to DEFAULT_REGISTEREDINMEDICAL
        defaultEmployeeFamilyInfoShouldBeFound("registeredinmedical.equals=" + DEFAULT_REGISTEREDINMEDICAL);

        // Get all the employeeFamilyInfoList where registeredinmedical equals to UPDATED_REGISTEREDINMEDICAL
        defaultEmployeeFamilyInfoShouldNotBeFound("registeredinmedical.equals=" + UPDATED_REGISTEREDINMEDICAL);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByRegisteredinmedicalIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where registeredinmedical in DEFAULT_REGISTEREDINMEDICAL or UPDATED_REGISTEREDINMEDICAL
        defaultEmployeeFamilyInfoShouldBeFound("registeredinmedical.in=" + DEFAULT_REGISTEREDINMEDICAL + "," + UPDATED_REGISTEREDINMEDICAL);

        // Get all the employeeFamilyInfoList where registeredinmedical equals to UPDATED_REGISTEREDINMEDICAL
        defaultEmployeeFamilyInfoShouldNotBeFound("registeredinmedical.in=" + UPDATED_REGISTEREDINMEDICAL);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByRegisteredinmedicalIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where registeredinmedical is not null
        defaultEmployeeFamilyInfoShouldBeFound("registeredinmedical.specified=true");

        // Get all the employeeFamilyInfoList where registeredinmedical is null
        defaultEmployeeFamilyInfoShouldNotBeFound("registeredinmedical.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeFamilyInfoShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeFamilyInfoList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeFamilyInfoShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeFamilyInfoShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeFamilyInfoList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeFamilyInfoShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where createdat is not null
        defaultEmployeeFamilyInfoShouldBeFound("createdat.specified=true");

        // Get all the employeeFamilyInfoList where createdat is null
        defaultEmployeeFamilyInfoShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeFamilyInfoShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeFamilyInfoList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeFamilyInfoShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeFamilyInfoShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeFamilyInfoList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeFamilyInfoShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where updatedat is not null
        defaultEmployeeFamilyInfoShouldBeFound("updatedat.specified=true");

        // Get all the employeeFamilyInfoList where updatedat is null
        defaultEmployeeFamilyInfoShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByMedicalpolicynoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where medicalpolicyno equals to DEFAULT_MEDICALPOLICYNO
        defaultEmployeeFamilyInfoShouldBeFound("medicalpolicyno.equals=" + DEFAULT_MEDICALPOLICYNO);

        // Get all the employeeFamilyInfoList where medicalpolicyno equals to UPDATED_MEDICALPOLICYNO
        defaultEmployeeFamilyInfoShouldNotBeFound("medicalpolicyno.equals=" + UPDATED_MEDICALPOLICYNO);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByMedicalpolicynoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where medicalpolicyno in DEFAULT_MEDICALPOLICYNO or UPDATED_MEDICALPOLICYNO
        defaultEmployeeFamilyInfoShouldBeFound("medicalpolicyno.in=" + DEFAULT_MEDICALPOLICYNO + "," + UPDATED_MEDICALPOLICYNO);

        // Get all the employeeFamilyInfoList where medicalpolicyno equals to UPDATED_MEDICALPOLICYNO
        defaultEmployeeFamilyInfoShouldNotBeFound("medicalpolicyno.in=" + UPDATED_MEDICALPOLICYNO);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByMedicalpolicynoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where medicalpolicyno is not null
        defaultEmployeeFamilyInfoShouldBeFound("medicalpolicyno.specified=true");

        // Get all the employeeFamilyInfoList where medicalpolicyno is null
        defaultEmployeeFamilyInfoShouldNotBeFound("medicalpolicyno.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByMedicalpolicynoContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where medicalpolicyno contains DEFAULT_MEDICALPOLICYNO
        defaultEmployeeFamilyInfoShouldBeFound("medicalpolicyno.contains=" + DEFAULT_MEDICALPOLICYNO);

        // Get all the employeeFamilyInfoList where medicalpolicyno contains UPDATED_MEDICALPOLICYNO
        defaultEmployeeFamilyInfoShouldNotBeFound("medicalpolicyno.contains=" + UPDATED_MEDICALPOLICYNO);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByMedicalpolicynoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where medicalpolicyno does not contain DEFAULT_MEDICALPOLICYNO
        defaultEmployeeFamilyInfoShouldNotBeFound("medicalpolicyno.doesNotContain=" + DEFAULT_MEDICALPOLICYNO);

        // Get all the employeeFamilyInfoList where medicalpolicyno does not contain UPDATED_MEDICALPOLICYNO
        defaultEmployeeFamilyInfoShouldBeFound("medicalpolicyno.doesNotContain=" + UPDATED_MEDICALPOLICYNO);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where gender equals to DEFAULT_GENDER
        defaultEmployeeFamilyInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the employeeFamilyInfoList where gender equals to UPDATED_GENDER
        defaultEmployeeFamilyInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultEmployeeFamilyInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the employeeFamilyInfoList where gender equals to UPDATED_GENDER
        defaultEmployeeFamilyInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where gender is not null
        defaultEmployeeFamilyInfoShouldBeFound("gender.specified=true");

        // Get all the employeeFamilyInfoList where gender is null
        defaultEmployeeFamilyInfoShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByGenderContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where gender contains DEFAULT_GENDER
        defaultEmployeeFamilyInfoShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the employeeFamilyInfoList where gender contains UPDATED_GENDER
        defaultEmployeeFamilyInfoShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        // Get all the employeeFamilyInfoList where gender does not contain DEFAULT_GENDER
        defaultEmployeeFamilyInfoShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the employeeFamilyInfoList where gender does not contain UPDATED_GENDER
        defaultEmployeeFamilyInfoShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeeFamilyInfosByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeFamilyInfo.setEmployee(employee);
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);
        Long employeeId = employee.getId();

        // Get all the employeeFamilyInfoList where employee equals to employeeId
        defaultEmployeeFamilyInfoShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeFamilyInfoList where employee equals to (employeeId + 1)
        defaultEmployeeFamilyInfoShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeFamilyInfoShouldBeFound(String filter) throws Exception {
        restEmployeeFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeFamilyInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].relationship").value(hasItem(DEFAULT_RELATIONSHIP)))
            .andExpect(jsonPath("$.[*].contactno").value(hasItem(DEFAULT_CONTACTNO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].registeredinmedical").value(hasItem(DEFAULT_REGISTEREDINMEDICAL.booleanValue())))
            .andExpect(jsonPath("$.[*].cnicContentType").value(hasItem(DEFAULT_CNIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cnic").value(hasItem(Base64Utils.encodeToString(DEFAULT_CNIC))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].medicalpolicyno").value(hasItem(DEFAULT_MEDICALPOLICYNO)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)));

        // Check, that the count call also returns 1
        restEmployeeFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeFamilyInfoShouldNotBeFound(String filter) throws Exception {
        restEmployeeFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeFamilyInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeFamilyInfo() throws Exception {
        // Get the employeeFamilyInfo
        restEmployeeFamilyInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeFamilyInfo() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();

        // Update the employeeFamilyInfo
        EmployeeFamilyInfo updatedEmployeeFamilyInfo = employeeFamilyInfoRepository.findById(employeeFamilyInfo.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeFamilyInfo are not directly saved in db
        em.detach(updatedEmployeeFamilyInfo);
        updatedEmployeeFamilyInfo
            .fullname(UPDATED_FULLNAME)
            .relationship(UPDATED_RELATIONSHIP)
            .contactno(UPDATED_CONTACTNO)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .registeredinmedical(UPDATED_REGISTEREDINMEDICAL)
            .cnic(UPDATED_CNIC)
            .cnicContentType(UPDATED_CNIC_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .medicalpolicyno(UPDATED_MEDICALPOLICYNO)
            .gender(UPDATED_GENDER);

        restEmployeeFamilyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeFamilyInfo.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeFamilyInfo))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
        EmployeeFamilyInfo testEmployeeFamilyInfo = employeeFamilyInfoList.get(employeeFamilyInfoList.size() - 1);
        assertThat(testEmployeeFamilyInfo.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testEmployeeFamilyInfo.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
        assertThat(testEmployeeFamilyInfo.getContactno()).isEqualTo(UPDATED_CONTACTNO);
        assertThat(testEmployeeFamilyInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployeeFamilyInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testEmployeeFamilyInfo.getRegisteredinmedical()).isEqualTo(UPDATED_REGISTEREDINMEDICAL);
        assertThat(testEmployeeFamilyInfo.getCnic()).isEqualTo(UPDATED_CNIC);
        assertThat(testEmployeeFamilyInfo.getCnicContentType()).isEqualTo(UPDATED_CNIC_CONTENT_TYPE);
        assertThat(testEmployeeFamilyInfo.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeFamilyInfo.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeFamilyInfo.getMedicalpolicyno()).isEqualTo(UPDATED_MEDICALPOLICYNO);
        assertThat(testEmployeeFamilyInfo.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();
        employeeFamilyInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeFamilyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeFamilyInfo.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();
        employeeFamilyInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeFamilyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();
        employeeFamilyInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeFamilyInfoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeFamilyInfoWithPatch() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();

        // Update the employeeFamilyInfo using partial update
        EmployeeFamilyInfo partialUpdatedEmployeeFamilyInfo = new EmployeeFamilyInfo();
        partialUpdatedEmployeeFamilyInfo.setId(employeeFamilyInfo.getId());

        partialUpdatedEmployeeFamilyInfo
            .fullname(UPDATED_FULLNAME)
            .relationship(UPDATED_RELATIONSHIP)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .registeredinmedical(UPDATED_REGISTEREDINMEDICAL)
            .createdat(UPDATED_CREATEDAT)
            .medicalpolicyno(UPDATED_MEDICALPOLICYNO);

        restEmployeeFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeFamilyInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeFamilyInfo))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
        EmployeeFamilyInfo testEmployeeFamilyInfo = employeeFamilyInfoList.get(employeeFamilyInfoList.size() - 1);
        assertThat(testEmployeeFamilyInfo.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testEmployeeFamilyInfo.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
        assertThat(testEmployeeFamilyInfo.getContactno()).isEqualTo(DEFAULT_CONTACTNO);
        assertThat(testEmployeeFamilyInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployeeFamilyInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testEmployeeFamilyInfo.getRegisteredinmedical()).isEqualTo(UPDATED_REGISTEREDINMEDICAL);
        assertThat(testEmployeeFamilyInfo.getCnic()).isEqualTo(DEFAULT_CNIC);
        assertThat(testEmployeeFamilyInfo.getCnicContentType()).isEqualTo(DEFAULT_CNIC_CONTENT_TYPE);
        assertThat(testEmployeeFamilyInfo.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeFamilyInfo.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeFamilyInfo.getMedicalpolicyno()).isEqualTo(UPDATED_MEDICALPOLICYNO);
        assertThat(testEmployeeFamilyInfo.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeFamilyInfoWithPatch() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();

        // Update the employeeFamilyInfo using partial update
        EmployeeFamilyInfo partialUpdatedEmployeeFamilyInfo = new EmployeeFamilyInfo();
        partialUpdatedEmployeeFamilyInfo.setId(employeeFamilyInfo.getId());

        partialUpdatedEmployeeFamilyInfo
            .fullname(UPDATED_FULLNAME)
            .relationship(UPDATED_RELATIONSHIP)
            .contactno(UPDATED_CONTACTNO)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .registeredinmedical(UPDATED_REGISTEREDINMEDICAL)
            .cnic(UPDATED_CNIC)
            .cnicContentType(UPDATED_CNIC_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .medicalpolicyno(UPDATED_MEDICALPOLICYNO)
            .gender(UPDATED_GENDER);

        restEmployeeFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeFamilyInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeFamilyInfo))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
        EmployeeFamilyInfo testEmployeeFamilyInfo = employeeFamilyInfoList.get(employeeFamilyInfoList.size() - 1);
        assertThat(testEmployeeFamilyInfo.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testEmployeeFamilyInfo.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
        assertThat(testEmployeeFamilyInfo.getContactno()).isEqualTo(UPDATED_CONTACTNO);
        assertThat(testEmployeeFamilyInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployeeFamilyInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testEmployeeFamilyInfo.getRegisteredinmedical()).isEqualTo(UPDATED_REGISTEREDINMEDICAL);
        assertThat(testEmployeeFamilyInfo.getCnic()).isEqualTo(UPDATED_CNIC);
        assertThat(testEmployeeFamilyInfo.getCnicContentType()).isEqualTo(UPDATED_CNIC_CONTENT_TYPE);
        assertThat(testEmployeeFamilyInfo.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeFamilyInfo.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeFamilyInfo.getMedicalpolicyno()).isEqualTo(UPDATED_MEDICALPOLICYNO);
        assertThat(testEmployeeFamilyInfo.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();
        employeeFamilyInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeFamilyInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();
        employeeFamilyInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeFamilyInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeFamilyInfoRepository.findAll().size();
        employeeFamilyInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeFamilyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeFamilyInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeFamilyInfo in the database
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeFamilyInfo() throws Exception {
        // Initialize the database
        employeeFamilyInfoRepository.saveAndFlush(employeeFamilyInfo);

        int databaseSizeBeforeDelete = employeeFamilyInfoRepository.findAll().size();

        // Delete the employeeFamilyInfo
        restEmployeeFamilyInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeFamilyInfo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeFamilyInfo> employeeFamilyInfoList = employeeFamilyInfoRepository.findAll();
        assertThat(employeeFamilyInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
