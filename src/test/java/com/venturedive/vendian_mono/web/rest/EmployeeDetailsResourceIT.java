package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeDetails;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.repository.EmployeeDetailsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeDetailsCriteria;
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
 * Integration tests for the {@link EmployeeDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeDetailsResourceIT {

    private static final String DEFAULT_RELIGION = "AAAAAAAAA";
    private static final String UPDATED_RELIGION = "BBBBBBBBB";

    private static final String DEFAULT_MARITALSTATUS = "AAAAAAAA";
    private static final String UPDATED_MARITALSTATUS = "BBBBBBBB";

    private static final byte[] DEFAULT_CNIC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CNIC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CNIC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CNIC_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CNICEXPIRY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CNICEXPIRY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CNICEXPIRY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CNICEXPIRY_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_BLOODGROUP = "AAA";
    private static final String UPDATED_BLOODGROUP = "BBB";

    private static final byte[] DEFAULT_TAXRETURNFILER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TAXRETURNFILER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TAXRETURNFILER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TAXRETURNFILER_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PASSPORTNO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PASSPORTNO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PASSPORTNO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PASSPORTNO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PASSPORTEXPIRY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PASSPORTEXPIRY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PASSPORTEXPIRY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PASSPORTEXPIRY_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TOTALTENURE = "AAAAAAAAAA";
    private static final String UPDATED_TOTALTENURE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeDetailsMockMvc;

    private EmployeeDetails employeeDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDetails createEntity(EntityManager em) {
        EmployeeDetails employeeDetails = new EmployeeDetails()
            .religion(DEFAULT_RELIGION)
            .maritalstatus(DEFAULT_MARITALSTATUS)
            .cnic(DEFAULT_CNIC)
            .cnicContentType(DEFAULT_CNIC_CONTENT_TYPE)
            .cnicexpiry(DEFAULT_CNICEXPIRY)
            .cnicexpiryContentType(DEFAULT_CNICEXPIRY_CONTENT_TYPE)
            .bloodgroup(DEFAULT_BLOODGROUP)
            .taxreturnfiler(DEFAULT_TAXRETURNFILER)
            .taxreturnfilerContentType(DEFAULT_TAXRETURNFILER_CONTENT_TYPE)
            .passportno(DEFAULT_PASSPORTNO)
            .passportnoContentType(DEFAULT_PASSPORTNO_CONTENT_TYPE)
            .passportexpiry(DEFAULT_PASSPORTEXPIRY)
            .passportexpiryContentType(DEFAULT_PASSPORTEXPIRY_CONTENT_TYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .totaltenure(DEFAULT_TOTALTENURE);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeDetails.setEmployee(employees);
        return employeeDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDetails createUpdatedEntity(EntityManager em) {
        EmployeeDetails employeeDetails = new EmployeeDetails()
            .religion(UPDATED_RELIGION)
            .maritalstatus(UPDATED_MARITALSTATUS)
            .cnic(UPDATED_CNIC)
            .cnicContentType(UPDATED_CNIC_CONTENT_TYPE)
            .cnicexpiry(UPDATED_CNICEXPIRY)
            .cnicexpiryContentType(UPDATED_CNICEXPIRY_CONTENT_TYPE)
            .bloodgroup(UPDATED_BLOODGROUP)
            .taxreturnfiler(UPDATED_TAXRETURNFILER)
            .taxreturnfilerContentType(UPDATED_TAXRETURNFILER_CONTENT_TYPE)
            .passportno(UPDATED_PASSPORTNO)
            .passportnoContentType(UPDATED_PASSPORTNO_CONTENT_TYPE)
            .passportexpiry(UPDATED_PASSPORTEXPIRY)
            .passportexpiryContentType(UPDATED_PASSPORTEXPIRY_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .totaltenure(UPDATED_TOTALTENURE);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeDetails.setEmployee(employees);
        return employeeDetails;
    }

    @BeforeEach
    public void initTest() {
        employeeDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeDetails() throws Exception {
        int databaseSizeBeforeCreate = employeeDetailsRepository.findAll().size();
        // Create the EmployeeDetails
        restEmployeeDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetails))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testEmployeeDetails.getMaritalstatus()).isEqualTo(DEFAULT_MARITALSTATUS);
        assertThat(testEmployeeDetails.getCnic()).isEqualTo(DEFAULT_CNIC);
        assertThat(testEmployeeDetails.getCnicContentType()).isEqualTo(DEFAULT_CNIC_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getCnicexpiry()).isEqualTo(DEFAULT_CNICEXPIRY);
        assertThat(testEmployeeDetails.getCnicexpiryContentType()).isEqualTo(DEFAULT_CNICEXPIRY_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getBloodgroup()).isEqualTo(DEFAULT_BLOODGROUP);
        assertThat(testEmployeeDetails.getTaxreturnfiler()).isEqualTo(DEFAULT_TAXRETURNFILER);
        assertThat(testEmployeeDetails.getTaxreturnfilerContentType()).isEqualTo(DEFAULT_TAXRETURNFILER_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getPassportno()).isEqualTo(DEFAULT_PASSPORTNO);
        assertThat(testEmployeeDetails.getPassportnoContentType()).isEqualTo(DEFAULT_PASSPORTNO_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getPassportexpiry()).isEqualTo(DEFAULT_PASSPORTEXPIRY);
        assertThat(testEmployeeDetails.getPassportexpiryContentType()).isEqualTo(DEFAULT_PASSPORTEXPIRY_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeDetails.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeDetails.getTotaltenure()).isEqualTo(DEFAULT_TOTALTENURE);
    }

    @Test
    @Transactional
    void createEmployeeDetailsWithExistingId() throws Exception {
        // Create the EmployeeDetails with an existing ID
        employeeDetails.setId(1L);

        int databaseSizeBeforeCreate = employeeDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].maritalstatus").value(hasItem(DEFAULT_MARITALSTATUS)))
            .andExpect(jsonPath("$.[*].cnicContentType").value(hasItem(DEFAULT_CNIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cnic").value(hasItem(Base64Utils.encodeToString(DEFAULT_CNIC))))
            .andExpect(jsonPath("$.[*].cnicexpiryContentType").value(hasItem(DEFAULT_CNICEXPIRY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cnicexpiry").value(hasItem(Base64Utils.encodeToString(DEFAULT_CNICEXPIRY))))
            .andExpect(jsonPath("$.[*].bloodgroup").value(hasItem(DEFAULT_BLOODGROUP)))
            .andExpect(jsonPath("$.[*].taxreturnfilerContentType").value(hasItem(DEFAULT_TAXRETURNFILER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].taxreturnfiler").value(hasItem(Base64Utils.encodeToString(DEFAULT_TAXRETURNFILER))))
            .andExpect(jsonPath("$.[*].passportnoContentType").value(hasItem(DEFAULT_PASSPORTNO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].passportno").value(hasItem(Base64Utils.encodeToString(DEFAULT_PASSPORTNO))))
            .andExpect(jsonPath("$.[*].passportexpiryContentType").value(hasItem(DEFAULT_PASSPORTEXPIRY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].passportexpiry").value(hasItem(Base64Utils.encodeToString(DEFAULT_PASSPORTEXPIRY))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].totaltenure").value(hasItem(DEFAULT_TOTALTENURE)));
    }

    @Test
    @Transactional
    void getEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get the employeeDetails
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDetails.getId().intValue()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.maritalstatus").value(DEFAULT_MARITALSTATUS))
            .andExpect(jsonPath("$.cnicContentType").value(DEFAULT_CNIC_CONTENT_TYPE))
            .andExpect(jsonPath("$.cnic").value(Base64Utils.encodeToString(DEFAULT_CNIC)))
            .andExpect(jsonPath("$.cnicexpiryContentType").value(DEFAULT_CNICEXPIRY_CONTENT_TYPE))
            .andExpect(jsonPath("$.cnicexpiry").value(Base64Utils.encodeToString(DEFAULT_CNICEXPIRY)))
            .andExpect(jsonPath("$.bloodgroup").value(DEFAULT_BLOODGROUP))
            .andExpect(jsonPath("$.taxreturnfilerContentType").value(DEFAULT_TAXRETURNFILER_CONTENT_TYPE))
            .andExpect(jsonPath("$.taxreturnfiler").value(Base64Utils.encodeToString(DEFAULT_TAXRETURNFILER)))
            .andExpect(jsonPath("$.passportnoContentType").value(DEFAULT_PASSPORTNO_CONTENT_TYPE))
            .andExpect(jsonPath("$.passportno").value(Base64Utils.encodeToString(DEFAULT_PASSPORTNO)))
            .andExpect(jsonPath("$.passportexpiryContentType").value(DEFAULT_PASSPORTEXPIRY_CONTENT_TYPE))
            .andExpect(jsonPath("$.passportexpiry").value(Base64Utils.encodeToString(DEFAULT_PASSPORTEXPIRY)))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.totaltenure").value(DEFAULT_TOTALTENURE));
    }

    @Test
    @Transactional
    void getEmployeeDetailsByIdFiltering() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        Long id = employeeDetails.getId();

        defaultEmployeeDetailsShouldBeFound("id.equals=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion equals to DEFAULT_RELIGION
        defaultEmployeeDetailsShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the employeeDetailsList where religion equals to UPDATED_RELIGION
        defaultEmployeeDetailsShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultEmployeeDetailsShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the employeeDetailsList where religion equals to UPDATED_RELIGION
        defaultEmployeeDetailsShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion is not null
        defaultEmployeeDetailsShouldBeFound("religion.specified=true");

        // Get all the employeeDetailsList where religion is null
        defaultEmployeeDetailsShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion contains DEFAULT_RELIGION
        defaultEmployeeDetailsShouldBeFound("religion.contains=" + DEFAULT_RELIGION);

        // Get all the employeeDetailsList where religion contains UPDATED_RELIGION
        defaultEmployeeDetailsShouldNotBeFound("religion.contains=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByReligionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where religion does not contain DEFAULT_RELIGION
        defaultEmployeeDetailsShouldNotBeFound("religion.doesNotContain=" + DEFAULT_RELIGION);

        // Get all the employeeDetailsList where religion does not contain UPDATED_RELIGION
        defaultEmployeeDetailsShouldBeFound("religion.doesNotContain=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalstatus equals to DEFAULT_MARITALSTATUS
        defaultEmployeeDetailsShouldBeFound("maritalstatus.equals=" + DEFAULT_MARITALSTATUS);

        // Get all the employeeDetailsList where maritalstatus equals to UPDATED_MARITALSTATUS
        defaultEmployeeDetailsShouldNotBeFound("maritalstatus.equals=" + UPDATED_MARITALSTATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalstatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalstatus in DEFAULT_MARITALSTATUS or UPDATED_MARITALSTATUS
        defaultEmployeeDetailsShouldBeFound("maritalstatus.in=" + DEFAULT_MARITALSTATUS + "," + UPDATED_MARITALSTATUS);

        // Get all the employeeDetailsList where maritalstatus equals to UPDATED_MARITALSTATUS
        defaultEmployeeDetailsShouldNotBeFound("maritalstatus.in=" + UPDATED_MARITALSTATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalstatus is not null
        defaultEmployeeDetailsShouldBeFound("maritalstatus.specified=true");

        // Get all the employeeDetailsList where maritalstatus is null
        defaultEmployeeDetailsShouldNotBeFound("maritalstatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalstatusContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalstatus contains DEFAULT_MARITALSTATUS
        defaultEmployeeDetailsShouldBeFound("maritalstatus.contains=" + DEFAULT_MARITALSTATUS);

        // Get all the employeeDetailsList where maritalstatus contains UPDATED_MARITALSTATUS
        defaultEmployeeDetailsShouldNotBeFound("maritalstatus.contains=" + UPDATED_MARITALSTATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalstatusNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalstatus does not contain DEFAULT_MARITALSTATUS
        defaultEmployeeDetailsShouldNotBeFound("maritalstatus.doesNotContain=" + DEFAULT_MARITALSTATUS);

        // Get all the employeeDetailsList where maritalstatus does not contain UPDATED_MARITALSTATUS
        defaultEmployeeDetailsShouldBeFound("maritalstatus.doesNotContain=" + UPDATED_MARITALSTATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodgroupIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodgroup equals to DEFAULT_BLOODGROUP
        defaultEmployeeDetailsShouldBeFound("bloodgroup.equals=" + DEFAULT_BLOODGROUP);

        // Get all the employeeDetailsList where bloodgroup equals to UPDATED_BLOODGROUP
        defaultEmployeeDetailsShouldNotBeFound("bloodgroup.equals=" + UPDATED_BLOODGROUP);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodgroupIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodgroup in DEFAULT_BLOODGROUP or UPDATED_BLOODGROUP
        defaultEmployeeDetailsShouldBeFound("bloodgroup.in=" + DEFAULT_BLOODGROUP + "," + UPDATED_BLOODGROUP);

        // Get all the employeeDetailsList where bloodgroup equals to UPDATED_BLOODGROUP
        defaultEmployeeDetailsShouldNotBeFound("bloodgroup.in=" + UPDATED_BLOODGROUP);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodgroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodgroup is not null
        defaultEmployeeDetailsShouldBeFound("bloodgroup.specified=true");

        // Get all the employeeDetailsList where bloodgroup is null
        defaultEmployeeDetailsShouldNotBeFound("bloodgroup.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodgroupContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodgroup contains DEFAULT_BLOODGROUP
        defaultEmployeeDetailsShouldBeFound("bloodgroup.contains=" + DEFAULT_BLOODGROUP);

        // Get all the employeeDetailsList where bloodgroup contains UPDATED_BLOODGROUP
        defaultEmployeeDetailsShouldNotBeFound("bloodgroup.contains=" + UPDATED_BLOODGROUP);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodgroupNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodgroup does not contain DEFAULT_BLOODGROUP
        defaultEmployeeDetailsShouldNotBeFound("bloodgroup.doesNotContain=" + DEFAULT_BLOODGROUP);

        // Get all the employeeDetailsList where bloodgroup does not contain UPDATED_BLOODGROUP
        defaultEmployeeDetailsShouldBeFound("bloodgroup.doesNotContain=" + UPDATED_BLOODGROUP);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeDetailsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeDetailsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeDetailsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeDetailsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeDetailsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeDetailsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdat is not null
        defaultEmployeeDetailsShouldBeFound("createdat.specified=true");

        // Get all the employeeDetailsList where createdat is null
        defaultEmployeeDetailsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeDetailsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeDetailsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeDetailsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeDetailsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeDetailsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeDetailsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where updatedat is not null
        defaultEmployeeDetailsShouldBeFound("updatedat.specified=true");

        // Get all the employeeDetailsList where updatedat is null
        defaultEmployeeDetailsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTotaltenureIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where totaltenure equals to DEFAULT_TOTALTENURE
        defaultEmployeeDetailsShouldBeFound("totaltenure.equals=" + DEFAULT_TOTALTENURE);

        // Get all the employeeDetailsList where totaltenure equals to UPDATED_TOTALTENURE
        defaultEmployeeDetailsShouldNotBeFound("totaltenure.equals=" + UPDATED_TOTALTENURE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTotaltenureIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where totaltenure in DEFAULT_TOTALTENURE or UPDATED_TOTALTENURE
        defaultEmployeeDetailsShouldBeFound("totaltenure.in=" + DEFAULT_TOTALTENURE + "," + UPDATED_TOTALTENURE);

        // Get all the employeeDetailsList where totaltenure equals to UPDATED_TOTALTENURE
        defaultEmployeeDetailsShouldNotBeFound("totaltenure.in=" + UPDATED_TOTALTENURE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTotaltenureIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where totaltenure is not null
        defaultEmployeeDetailsShouldBeFound("totaltenure.specified=true");

        // Get all the employeeDetailsList where totaltenure is null
        defaultEmployeeDetailsShouldNotBeFound("totaltenure.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTotaltenureContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where totaltenure contains DEFAULT_TOTALTENURE
        defaultEmployeeDetailsShouldBeFound("totaltenure.contains=" + DEFAULT_TOTALTENURE);

        // Get all the employeeDetailsList where totaltenure contains UPDATED_TOTALTENURE
        defaultEmployeeDetailsShouldNotBeFound("totaltenure.contains=" + UPDATED_TOTALTENURE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByTotaltenureNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where totaltenure does not contain DEFAULT_TOTALTENURE
        defaultEmployeeDetailsShouldNotBeFound("totaltenure.doesNotContain=" + DEFAULT_TOTALTENURE);

        // Get all the employeeDetailsList where totaltenure does not contain UPDATED_TOTALTENURE
        defaultEmployeeDetailsShouldBeFound("totaltenure.doesNotContain=" + UPDATED_TOTALTENURE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeDetailsRepository.saveAndFlush(employeeDetails);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeDetails.setEmployee(employee);
        employeeDetailsRepository.saveAndFlush(employeeDetails);
        Long employeeId = employee.getId();

        // Get all the employeeDetailsList where employee equals to employeeId
        defaultEmployeeDetailsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeDetailsList where employee equals to (employeeId + 1)
        defaultEmployeeDetailsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeDetailsShouldBeFound(String filter) throws Exception {
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].maritalstatus").value(hasItem(DEFAULT_MARITALSTATUS)))
            .andExpect(jsonPath("$.[*].cnicContentType").value(hasItem(DEFAULT_CNIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cnic").value(hasItem(Base64Utils.encodeToString(DEFAULT_CNIC))))
            .andExpect(jsonPath("$.[*].cnicexpiryContentType").value(hasItem(DEFAULT_CNICEXPIRY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cnicexpiry").value(hasItem(Base64Utils.encodeToString(DEFAULT_CNICEXPIRY))))
            .andExpect(jsonPath("$.[*].bloodgroup").value(hasItem(DEFAULT_BLOODGROUP)))
            .andExpect(jsonPath("$.[*].taxreturnfilerContentType").value(hasItem(DEFAULT_TAXRETURNFILER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].taxreturnfiler").value(hasItem(Base64Utils.encodeToString(DEFAULT_TAXRETURNFILER))))
            .andExpect(jsonPath("$.[*].passportnoContentType").value(hasItem(DEFAULT_PASSPORTNO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].passportno").value(hasItem(Base64Utils.encodeToString(DEFAULT_PASSPORTNO))))
            .andExpect(jsonPath("$.[*].passportexpiryContentType").value(hasItem(DEFAULT_PASSPORTEXPIRY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].passportexpiry").value(hasItem(Base64Utils.encodeToString(DEFAULT_PASSPORTEXPIRY))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].totaltenure").value(hasItem(DEFAULT_TOTALTENURE)));

        // Check, that the count call also returns 1
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeDetailsShouldNotBeFound(String filter) throws Exception {
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeDetails() throws Exception {
        // Get the employeeDetails
        restEmployeeDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails
        EmployeeDetails updatedEmployeeDetails = employeeDetailsRepository.findById(employeeDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDetails are not directly saved in db
        em.detach(updatedEmployeeDetails);
        updatedEmployeeDetails
            .religion(UPDATED_RELIGION)
            .maritalstatus(UPDATED_MARITALSTATUS)
            .cnic(UPDATED_CNIC)
            .cnicContentType(UPDATED_CNIC_CONTENT_TYPE)
            .cnicexpiry(UPDATED_CNICEXPIRY)
            .cnicexpiryContentType(UPDATED_CNICEXPIRY_CONTENT_TYPE)
            .bloodgroup(UPDATED_BLOODGROUP)
            .taxreturnfiler(UPDATED_TAXRETURNFILER)
            .taxreturnfilerContentType(UPDATED_TAXRETURNFILER_CONTENT_TYPE)
            .passportno(UPDATED_PASSPORTNO)
            .passportnoContentType(UPDATED_PASSPORTNO_CONTENT_TYPE)
            .passportexpiry(UPDATED_PASSPORTEXPIRY)
            .passportexpiryContentType(UPDATED_PASSPORTEXPIRY_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .totaltenure(UPDATED_TOTALTENURE);

        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testEmployeeDetails.getMaritalstatus()).isEqualTo(UPDATED_MARITALSTATUS);
        assertThat(testEmployeeDetails.getCnic()).isEqualTo(UPDATED_CNIC);
        assertThat(testEmployeeDetails.getCnicContentType()).isEqualTo(UPDATED_CNIC_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getCnicexpiry()).isEqualTo(UPDATED_CNICEXPIRY);
        assertThat(testEmployeeDetails.getCnicexpiryContentType()).isEqualTo(UPDATED_CNICEXPIRY_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getBloodgroup()).isEqualTo(UPDATED_BLOODGROUP);
        assertThat(testEmployeeDetails.getTaxreturnfiler()).isEqualTo(UPDATED_TAXRETURNFILER);
        assertThat(testEmployeeDetails.getTaxreturnfilerContentType()).isEqualTo(UPDATED_TAXRETURNFILER_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getPassportno()).isEqualTo(UPDATED_PASSPORTNO);
        assertThat(testEmployeeDetails.getPassportnoContentType()).isEqualTo(UPDATED_PASSPORTNO_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getPassportexpiry()).isEqualTo(UPDATED_PASSPORTEXPIRY);
        assertThat(testEmployeeDetails.getPassportexpiryContentType()).isEqualTo(UPDATED_PASSPORTEXPIRY_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeDetails.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeDetails.getTotaltenure()).isEqualTo(UPDATED_TOTALTENURE);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeDetailsWithPatch() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails using partial update
        EmployeeDetails partialUpdatedEmployeeDetails = new EmployeeDetails();
        partialUpdatedEmployeeDetails.setId(employeeDetails.getId());

        partialUpdatedEmployeeDetails
            .cnic(UPDATED_CNIC)
            .cnicContentType(UPDATED_CNIC_CONTENT_TYPE)
            .bloodgroup(UPDATED_BLOODGROUP)
            .createdat(UPDATED_CREATEDAT)
            .totaltenure(UPDATED_TOTALTENURE);

        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testEmployeeDetails.getMaritalstatus()).isEqualTo(DEFAULT_MARITALSTATUS);
        assertThat(testEmployeeDetails.getCnic()).isEqualTo(UPDATED_CNIC);
        assertThat(testEmployeeDetails.getCnicContentType()).isEqualTo(UPDATED_CNIC_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getCnicexpiry()).isEqualTo(DEFAULT_CNICEXPIRY);
        assertThat(testEmployeeDetails.getCnicexpiryContentType()).isEqualTo(DEFAULT_CNICEXPIRY_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getBloodgroup()).isEqualTo(UPDATED_BLOODGROUP);
        assertThat(testEmployeeDetails.getTaxreturnfiler()).isEqualTo(DEFAULT_TAXRETURNFILER);
        assertThat(testEmployeeDetails.getTaxreturnfilerContentType()).isEqualTo(DEFAULT_TAXRETURNFILER_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getPassportno()).isEqualTo(DEFAULT_PASSPORTNO);
        assertThat(testEmployeeDetails.getPassportnoContentType()).isEqualTo(DEFAULT_PASSPORTNO_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getPassportexpiry()).isEqualTo(DEFAULT_PASSPORTEXPIRY);
        assertThat(testEmployeeDetails.getPassportexpiryContentType()).isEqualTo(DEFAULT_PASSPORTEXPIRY_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeDetails.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeDetails.getTotaltenure()).isEqualTo(UPDATED_TOTALTENURE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeDetailsWithPatch() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails using partial update
        EmployeeDetails partialUpdatedEmployeeDetails = new EmployeeDetails();
        partialUpdatedEmployeeDetails.setId(employeeDetails.getId());

        partialUpdatedEmployeeDetails
            .religion(UPDATED_RELIGION)
            .maritalstatus(UPDATED_MARITALSTATUS)
            .cnic(UPDATED_CNIC)
            .cnicContentType(UPDATED_CNIC_CONTENT_TYPE)
            .cnicexpiry(UPDATED_CNICEXPIRY)
            .cnicexpiryContentType(UPDATED_CNICEXPIRY_CONTENT_TYPE)
            .bloodgroup(UPDATED_BLOODGROUP)
            .taxreturnfiler(UPDATED_TAXRETURNFILER)
            .taxreturnfilerContentType(UPDATED_TAXRETURNFILER_CONTENT_TYPE)
            .passportno(UPDATED_PASSPORTNO)
            .passportnoContentType(UPDATED_PASSPORTNO_CONTENT_TYPE)
            .passportexpiry(UPDATED_PASSPORTEXPIRY)
            .passportexpiryContentType(UPDATED_PASSPORTEXPIRY_CONTENT_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .totaltenure(UPDATED_TOTALTENURE);

        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testEmployeeDetails.getMaritalstatus()).isEqualTo(UPDATED_MARITALSTATUS);
        assertThat(testEmployeeDetails.getCnic()).isEqualTo(UPDATED_CNIC);
        assertThat(testEmployeeDetails.getCnicContentType()).isEqualTo(UPDATED_CNIC_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getCnicexpiry()).isEqualTo(UPDATED_CNICEXPIRY);
        assertThat(testEmployeeDetails.getCnicexpiryContentType()).isEqualTo(UPDATED_CNICEXPIRY_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getBloodgroup()).isEqualTo(UPDATED_BLOODGROUP);
        assertThat(testEmployeeDetails.getTaxreturnfiler()).isEqualTo(UPDATED_TAXRETURNFILER);
        assertThat(testEmployeeDetails.getTaxreturnfilerContentType()).isEqualTo(UPDATED_TAXRETURNFILER_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getPassportno()).isEqualTo(UPDATED_PASSPORTNO);
        assertThat(testEmployeeDetails.getPassportnoContentType()).isEqualTo(UPDATED_PASSPORTNO_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getPassportexpiry()).isEqualTo(UPDATED_PASSPORTEXPIRY);
        assertThat(testEmployeeDetails.getPassportexpiryContentType()).isEqualTo(UPDATED_PASSPORTEXPIRY_CONTENT_TYPE);
        assertThat(testEmployeeDetails.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeDetails.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeDetails.getTotaltenure()).isEqualTo(UPDATED_TOTALTENURE);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeDelete = employeeDetailsRepository.findAll().size();

        // Delete the employeeDetails
        restEmployeeDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeDetails.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
