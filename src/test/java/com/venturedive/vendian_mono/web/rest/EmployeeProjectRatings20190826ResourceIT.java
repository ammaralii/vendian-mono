package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeProjectRatings20190826;
import com.venturedive.vendian_mono.repository.EmployeeProjectRatings20190826Repository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRatings20190826Criteria;
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
 * Integration tests for the {@link EmployeeProjectRatings20190826Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeProjectRatings20190826ResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_PMQUALITY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMQUALITY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMQUALITY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMQUALITY_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PMOWNERSHIP = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMOWNERSHIP = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMOWNERSHIP_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMOWNERSHIP_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PMSKILL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMSKILL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMSKILL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMSKILL_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PMETHICS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMETHICS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMETHICS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMETHICS_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PMEFFICIENCY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMEFFICIENCY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMEFFICIENCY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMEFFICIENCY_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PMFREEZE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMFREEZE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMFREEZE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMFREEZE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHFREEZE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHFREEZE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHFREEZE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHFREEZE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PMCOMMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMCOMMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMCOMMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMCOMMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHQUALITY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHQUALITY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHQUALITY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHQUALITY_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHOWNERSHIP = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHOWNERSHIP = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHOWNERSHIP_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHOWNERSHIP_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHSKILL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHSKILL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHSKILL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHSKILL_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHETHICS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHETHICS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHETHICS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHETHICS_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHEFFICIENCY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHEFFICIENCY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHEFFICIENCY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHEFFICIENCY_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHCOMMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHCOMMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHCOMMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHCOMMENT_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_PROJECTARCHITECTID = 1;
    private static final Integer UPDATED_PROJECTARCHITECTID = 2;
    private static final Integer SMALLER_PROJECTARCHITECTID = 1 - 1;

    private static final Integer DEFAULT_PROJECTMANAGERID = 1;
    private static final Integer UPDATED_PROJECTMANAGERID = 2;
    private static final Integer SMALLER_PROJECTMANAGERID = 1 - 1;

    private static final Integer DEFAULT_EMPLOYEEID = 1;
    private static final Integer UPDATED_EMPLOYEEID = 2;
    private static final Integer SMALLER_EMPLOYEEID = 1 - 1;

    private static final Integer DEFAULT_PROJECTCYCLEID = 1;
    private static final Integer UPDATED_PROJECTCYCLEID = 2;
    private static final Integer SMALLER_PROJECTCYCLEID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/employee-project-ratings-20190826-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeProjectRatings20190826Repository employeeProjectRatings20190826Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeProjectRatings20190826MockMvc;

    private EmployeeProjectRatings20190826 employeeProjectRatings20190826;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProjectRatings20190826 createEntity(EntityManager em) {
        EmployeeProjectRatings20190826 employeeProjectRatings20190826 = new EmployeeProjectRatings20190826()
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .pmquality(DEFAULT_PMQUALITY)
            .pmqualityContentType(DEFAULT_PMQUALITY_CONTENT_TYPE)
            .pmownership(DEFAULT_PMOWNERSHIP)
            .pmownershipContentType(DEFAULT_PMOWNERSHIP_CONTENT_TYPE)
            .pmskill(DEFAULT_PMSKILL)
            .pmskillContentType(DEFAULT_PMSKILL_CONTENT_TYPE)
            .pmethics(DEFAULT_PMETHICS)
            .pmethicsContentType(DEFAULT_PMETHICS_CONTENT_TYPE)
            .pmefficiency(DEFAULT_PMEFFICIENCY)
            .pmefficiencyContentType(DEFAULT_PMEFFICIENCY_CONTENT_TYPE)
            .pmfreeze(DEFAULT_PMFREEZE)
            .pmfreezeContentType(DEFAULT_PMFREEZE_CONTENT_TYPE)
            .archfreeze(DEFAULT_ARCHFREEZE)
            .archfreezeContentType(DEFAULT_ARCHFREEZE_CONTENT_TYPE)
            .pmcomment(DEFAULT_PMCOMMENT)
            .pmcommentContentType(DEFAULT_PMCOMMENT_CONTENT_TYPE)
            .archquality(DEFAULT_ARCHQUALITY)
            .archqualityContentType(DEFAULT_ARCHQUALITY_CONTENT_TYPE)
            .archownership(DEFAULT_ARCHOWNERSHIP)
            .archownershipContentType(DEFAULT_ARCHOWNERSHIP_CONTENT_TYPE)
            .archskill(DEFAULT_ARCHSKILL)
            .archskillContentType(DEFAULT_ARCHSKILL_CONTENT_TYPE)
            .archethics(DEFAULT_ARCHETHICS)
            .archethicsContentType(DEFAULT_ARCHETHICS_CONTENT_TYPE)
            .archefficiency(DEFAULT_ARCHEFFICIENCY)
            .archefficiencyContentType(DEFAULT_ARCHEFFICIENCY_CONTENT_TYPE)
            .archcomment(DEFAULT_ARCHCOMMENT)
            .archcommentContentType(DEFAULT_ARCHCOMMENT_CONTENT_TYPE)
            .projectarchitectid(DEFAULT_PROJECTARCHITECTID)
            .projectmanagerid(DEFAULT_PROJECTMANAGERID)
            .employeeid(DEFAULT_EMPLOYEEID)
            .projectcycleid(DEFAULT_PROJECTCYCLEID);
        return employeeProjectRatings20190826;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProjectRatings20190826 createUpdatedEntity(EntityManager em) {
        EmployeeProjectRatings20190826 employeeProjectRatings20190826 = new EmployeeProjectRatings20190826()
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .pmquality(UPDATED_PMQUALITY)
            .pmqualityContentType(UPDATED_PMQUALITY_CONTENT_TYPE)
            .pmownership(UPDATED_PMOWNERSHIP)
            .pmownershipContentType(UPDATED_PMOWNERSHIP_CONTENT_TYPE)
            .pmskill(UPDATED_PMSKILL)
            .pmskillContentType(UPDATED_PMSKILL_CONTENT_TYPE)
            .pmethics(UPDATED_PMETHICS)
            .pmethicsContentType(UPDATED_PMETHICS_CONTENT_TYPE)
            .pmefficiency(UPDATED_PMEFFICIENCY)
            .pmefficiencyContentType(UPDATED_PMEFFICIENCY_CONTENT_TYPE)
            .pmfreeze(UPDATED_PMFREEZE)
            .pmfreezeContentType(UPDATED_PMFREEZE_CONTENT_TYPE)
            .archfreeze(UPDATED_ARCHFREEZE)
            .archfreezeContentType(UPDATED_ARCHFREEZE_CONTENT_TYPE)
            .pmcomment(UPDATED_PMCOMMENT)
            .pmcommentContentType(UPDATED_PMCOMMENT_CONTENT_TYPE)
            .archquality(UPDATED_ARCHQUALITY)
            .archqualityContentType(UPDATED_ARCHQUALITY_CONTENT_TYPE)
            .archownership(UPDATED_ARCHOWNERSHIP)
            .archownershipContentType(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE)
            .archskill(UPDATED_ARCHSKILL)
            .archskillContentType(UPDATED_ARCHSKILL_CONTENT_TYPE)
            .archethics(UPDATED_ARCHETHICS)
            .archethicsContentType(UPDATED_ARCHETHICS_CONTENT_TYPE)
            .archefficiency(UPDATED_ARCHEFFICIENCY)
            .archefficiencyContentType(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE)
            .archcomment(UPDATED_ARCHCOMMENT)
            .archcommentContentType(UPDATED_ARCHCOMMENT_CONTENT_TYPE)
            .projectarchitectid(UPDATED_PROJECTARCHITECTID)
            .projectmanagerid(UPDATED_PROJECTMANAGERID)
            .employeeid(UPDATED_EMPLOYEEID)
            .projectcycleid(UPDATED_PROJECTCYCLEID);
        return employeeProjectRatings20190826;
    }

    @BeforeEach
    public void initTest() {
        employeeProjectRatings20190826 = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeProjectRatings20190826() throws Exception {
        int databaseSizeBeforeCreate = employeeProjectRatings20190826Repository.findAll().size();
        // Create the EmployeeProjectRatings20190826
        restEmployeeProjectRatings20190826MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings20190826))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeProjectRatings20190826 testEmployeeProjectRatings20190826 = employeeProjectRatings20190826List.get(
            employeeProjectRatings20190826List.size() - 1
        );
        assertThat(testEmployeeProjectRatings20190826.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeProjectRatings20190826.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeProjectRatings20190826.getPmquality()).isEqualTo(DEFAULT_PMQUALITY);
        assertThat(testEmployeeProjectRatings20190826.getPmqualityContentType()).isEqualTo(DEFAULT_PMQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmownership()).isEqualTo(DEFAULT_PMOWNERSHIP);
        assertThat(testEmployeeProjectRatings20190826.getPmownershipContentType()).isEqualTo(DEFAULT_PMOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmskill()).isEqualTo(DEFAULT_PMSKILL);
        assertThat(testEmployeeProjectRatings20190826.getPmskillContentType()).isEqualTo(DEFAULT_PMSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmethics()).isEqualTo(DEFAULT_PMETHICS);
        assertThat(testEmployeeProjectRatings20190826.getPmethicsContentType()).isEqualTo(DEFAULT_PMETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmefficiency()).isEqualTo(DEFAULT_PMEFFICIENCY);
        assertThat(testEmployeeProjectRatings20190826.getPmefficiencyContentType()).isEqualTo(DEFAULT_PMEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmfreeze()).isEqualTo(DEFAULT_PMFREEZE);
        assertThat(testEmployeeProjectRatings20190826.getPmfreezeContentType()).isEqualTo(DEFAULT_PMFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchfreeze()).isEqualTo(DEFAULT_ARCHFREEZE);
        assertThat(testEmployeeProjectRatings20190826.getArchfreezeContentType()).isEqualTo(DEFAULT_ARCHFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmcomment()).isEqualTo(DEFAULT_PMCOMMENT);
        assertThat(testEmployeeProjectRatings20190826.getPmcommentContentType()).isEqualTo(DEFAULT_PMCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchquality()).isEqualTo(DEFAULT_ARCHQUALITY);
        assertThat(testEmployeeProjectRatings20190826.getArchqualityContentType()).isEqualTo(DEFAULT_ARCHQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchownership()).isEqualTo(DEFAULT_ARCHOWNERSHIP);
        assertThat(testEmployeeProjectRatings20190826.getArchownershipContentType()).isEqualTo(DEFAULT_ARCHOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchskill()).isEqualTo(DEFAULT_ARCHSKILL);
        assertThat(testEmployeeProjectRatings20190826.getArchskillContentType()).isEqualTo(DEFAULT_ARCHSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchethics()).isEqualTo(DEFAULT_ARCHETHICS);
        assertThat(testEmployeeProjectRatings20190826.getArchethicsContentType()).isEqualTo(DEFAULT_ARCHETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchefficiency()).isEqualTo(DEFAULT_ARCHEFFICIENCY);
        assertThat(testEmployeeProjectRatings20190826.getArchefficiencyContentType()).isEqualTo(DEFAULT_ARCHEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchcomment()).isEqualTo(DEFAULT_ARCHCOMMENT);
        assertThat(testEmployeeProjectRatings20190826.getArchcommentContentType()).isEqualTo(DEFAULT_ARCHCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getProjectarchitectid()).isEqualTo(DEFAULT_PROJECTARCHITECTID);
        assertThat(testEmployeeProjectRatings20190826.getProjectmanagerid()).isEqualTo(DEFAULT_PROJECTMANAGERID);
        assertThat(testEmployeeProjectRatings20190826.getEmployeeid()).isEqualTo(DEFAULT_EMPLOYEEID);
        assertThat(testEmployeeProjectRatings20190826.getProjectcycleid()).isEqualTo(DEFAULT_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void createEmployeeProjectRatings20190826WithExistingId() throws Exception {
        // Create the EmployeeProjectRatings20190826 with an existing ID
        employeeProjectRatings20190826.setId(1L);

        int databaseSizeBeforeCreate = employeeProjectRatings20190826Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeProjectRatings20190826MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826s() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List
        restEmployeeProjectRatings20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProjectRatings20190826.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].pmqualityContentType").value(hasItem(DEFAULT_PMQUALITY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmquality").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMQUALITY))))
            .andExpect(jsonPath("$.[*].pmownershipContentType").value(hasItem(DEFAULT_PMOWNERSHIP_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmownership").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMOWNERSHIP))))
            .andExpect(jsonPath("$.[*].pmskillContentType").value(hasItem(DEFAULT_PMSKILL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmskill").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMSKILL))))
            .andExpect(jsonPath("$.[*].pmethicsContentType").value(hasItem(DEFAULT_PMETHICS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmethics").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMETHICS))))
            .andExpect(jsonPath("$.[*].pmefficiencyContentType").value(hasItem(DEFAULT_PMEFFICIENCY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmefficiency").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMEFFICIENCY))))
            .andExpect(jsonPath("$.[*].pmfreezeContentType").value(hasItem(DEFAULT_PMFREEZE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmfreeze").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMFREEZE))))
            .andExpect(jsonPath("$.[*].archfreezeContentType").value(hasItem(DEFAULT_ARCHFREEZE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archfreeze").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHFREEZE))))
            .andExpect(jsonPath("$.[*].pmcommentContentType").value(hasItem(DEFAULT_PMCOMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmcomment").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMCOMMENT))))
            .andExpect(jsonPath("$.[*].archqualityContentType").value(hasItem(DEFAULT_ARCHQUALITY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archquality").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHQUALITY))))
            .andExpect(jsonPath("$.[*].archownershipContentType").value(hasItem(DEFAULT_ARCHOWNERSHIP_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archownership").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHOWNERSHIP))))
            .andExpect(jsonPath("$.[*].archskillContentType").value(hasItem(DEFAULT_ARCHSKILL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archskill").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHSKILL))))
            .andExpect(jsonPath("$.[*].archethicsContentType").value(hasItem(DEFAULT_ARCHETHICS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archethics").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHETHICS))))
            .andExpect(jsonPath("$.[*].archefficiencyContentType").value(hasItem(DEFAULT_ARCHEFFICIENCY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archefficiency").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHEFFICIENCY))))
            .andExpect(jsonPath("$.[*].archcommentContentType").value(hasItem(DEFAULT_ARCHCOMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archcomment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHCOMMENT))))
            .andExpect(jsonPath("$.[*].projectarchitectid").value(hasItem(DEFAULT_PROJECTARCHITECTID)))
            .andExpect(jsonPath("$.[*].projectmanagerid").value(hasItem(DEFAULT_PROJECTMANAGERID)))
            .andExpect(jsonPath("$.[*].employeeid").value(hasItem(DEFAULT_EMPLOYEEID)))
            .andExpect(jsonPath("$.[*].projectcycleid").value(hasItem(DEFAULT_PROJECTCYCLEID)));
    }

    @Test
    @Transactional
    void getEmployeeProjectRatings20190826() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get the employeeProjectRatings20190826
        restEmployeeProjectRatings20190826MockMvc
            .perform(get(ENTITY_API_URL_ID, employeeProjectRatings20190826.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeProjectRatings20190826.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.pmqualityContentType").value(DEFAULT_PMQUALITY_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmquality").value(Base64Utils.encodeToString(DEFAULT_PMQUALITY)))
            .andExpect(jsonPath("$.pmownershipContentType").value(DEFAULT_PMOWNERSHIP_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmownership").value(Base64Utils.encodeToString(DEFAULT_PMOWNERSHIP)))
            .andExpect(jsonPath("$.pmskillContentType").value(DEFAULT_PMSKILL_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmskill").value(Base64Utils.encodeToString(DEFAULT_PMSKILL)))
            .andExpect(jsonPath("$.pmethicsContentType").value(DEFAULT_PMETHICS_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmethics").value(Base64Utils.encodeToString(DEFAULT_PMETHICS)))
            .andExpect(jsonPath("$.pmefficiencyContentType").value(DEFAULT_PMEFFICIENCY_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmefficiency").value(Base64Utils.encodeToString(DEFAULT_PMEFFICIENCY)))
            .andExpect(jsonPath("$.pmfreezeContentType").value(DEFAULT_PMFREEZE_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmfreeze").value(Base64Utils.encodeToString(DEFAULT_PMFREEZE)))
            .andExpect(jsonPath("$.archfreezeContentType").value(DEFAULT_ARCHFREEZE_CONTENT_TYPE))
            .andExpect(jsonPath("$.archfreeze").value(Base64Utils.encodeToString(DEFAULT_ARCHFREEZE)))
            .andExpect(jsonPath("$.pmcommentContentType").value(DEFAULT_PMCOMMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmcomment").value(Base64Utils.encodeToString(DEFAULT_PMCOMMENT)))
            .andExpect(jsonPath("$.archqualityContentType").value(DEFAULT_ARCHQUALITY_CONTENT_TYPE))
            .andExpect(jsonPath("$.archquality").value(Base64Utils.encodeToString(DEFAULT_ARCHQUALITY)))
            .andExpect(jsonPath("$.archownershipContentType").value(DEFAULT_ARCHOWNERSHIP_CONTENT_TYPE))
            .andExpect(jsonPath("$.archownership").value(Base64Utils.encodeToString(DEFAULT_ARCHOWNERSHIP)))
            .andExpect(jsonPath("$.archskillContentType").value(DEFAULT_ARCHSKILL_CONTENT_TYPE))
            .andExpect(jsonPath("$.archskill").value(Base64Utils.encodeToString(DEFAULT_ARCHSKILL)))
            .andExpect(jsonPath("$.archethicsContentType").value(DEFAULT_ARCHETHICS_CONTENT_TYPE))
            .andExpect(jsonPath("$.archethics").value(Base64Utils.encodeToString(DEFAULT_ARCHETHICS)))
            .andExpect(jsonPath("$.archefficiencyContentType").value(DEFAULT_ARCHEFFICIENCY_CONTENT_TYPE))
            .andExpect(jsonPath("$.archefficiency").value(Base64Utils.encodeToString(DEFAULT_ARCHEFFICIENCY)))
            .andExpect(jsonPath("$.archcommentContentType").value(DEFAULT_ARCHCOMMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.archcomment").value(Base64Utils.encodeToString(DEFAULT_ARCHCOMMENT)))
            .andExpect(jsonPath("$.projectarchitectid").value(DEFAULT_PROJECTARCHITECTID))
            .andExpect(jsonPath("$.projectmanagerid").value(DEFAULT_PROJECTMANAGERID))
            .andExpect(jsonPath("$.employeeid").value(DEFAULT_EMPLOYEEID))
            .andExpect(jsonPath("$.projectcycleid").value(DEFAULT_PROJECTCYCLEID));
    }

    @Test
    @Transactional
    void getEmployeeProjectRatings20190826sByIdFiltering() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        Long id = employeeProjectRatings20190826.getId();

        defaultEmployeeProjectRatings20190826ShouldBeFound("id.equals=" + id);
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeProjectRatings20190826ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeProjectRatings20190826ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeProjectRatings20190826ShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeProjectRatings20190826List where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeProjectRatings20190826ShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeProjectRatings20190826List where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where createdat is not null
        defaultEmployeeProjectRatings20190826ShouldBeFound("createdat.specified=true");

        // Get all the employeeProjectRatings20190826List where createdat is null
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeProjectRatings20190826ShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeProjectRatings20190826List where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeProjectRatings20190826ShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeProjectRatings20190826List where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where updatedat is not null
        defaultEmployeeProjectRatings20190826ShouldBeFound("updatedat.specified=true");

        // Get all the employeeProjectRatings20190826List where updatedat is null
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectarchitectidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectarchitectid equals to DEFAULT_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectarchitectid.equals=" + DEFAULT_PROJECTARCHITECTID);

        // Get all the employeeProjectRatings20190826List where projectarchitectid equals to UPDATED_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectarchitectid.equals=" + UPDATED_PROJECTARCHITECTID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectarchitectidIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectarchitectid in DEFAULT_PROJECTARCHITECTID or UPDATED_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldBeFound(
            "projectarchitectid.in=" + DEFAULT_PROJECTARCHITECTID + "," + UPDATED_PROJECTARCHITECTID
        );

        // Get all the employeeProjectRatings20190826List where projectarchitectid equals to UPDATED_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectarchitectid.in=" + UPDATED_PROJECTARCHITECTID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectarchitectidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is not null
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectarchitectid.specified=true");

        // Get all the employeeProjectRatings20190826List where projectarchitectid is null
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectarchitectid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectarchitectidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is greater than or equal to DEFAULT_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectarchitectid.greaterThanOrEqual=" + DEFAULT_PROJECTARCHITECTID);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is greater than or equal to UPDATED_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectarchitectid.greaterThanOrEqual=" + UPDATED_PROJECTARCHITECTID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectarchitectidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is less than or equal to DEFAULT_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectarchitectid.lessThanOrEqual=" + DEFAULT_PROJECTARCHITECTID);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is less than or equal to SMALLER_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectarchitectid.lessThanOrEqual=" + SMALLER_PROJECTARCHITECTID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectarchitectidIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is less than DEFAULT_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectarchitectid.lessThan=" + DEFAULT_PROJECTARCHITECTID);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is less than UPDATED_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectarchitectid.lessThan=" + UPDATED_PROJECTARCHITECTID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectarchitectidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is greater than DEFAULT_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectarchitectid.greaterThan=" + DEFAULT_PROJECTARCHITECTID);

        // Get all the employeeProjectRatings20190826List where projectarchitectid is greater than SMALLER_PROJECTARCHITECTID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectarchitectid.greaterThan=" + SMALLER_PROJECTARCHITECTID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectmanageridIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectmanagerid equals to DEFAULT_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectmanagerid.equals=" + DEFAULT_PROJECTMANAGERID);

        // Get all the employeeProjectRatings20190826List where projectmanagerid equals to UPDATED_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectmanagerid.equals=" + UPDATED_PROJECTMANAGERID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectmanageridIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectmanagerid in DEFAULT_PROJECTMANAGERID or UPDATED_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldBeFound(
            "projectmanagerid.in=" + DEFAULT_PROJECTMANAGERID + "," + UPDATED_PROJECTMANAGERID
        );

        // Get all the employeeProjectRatings20190826List where projectmanagerid equals to UPDATED_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectmanagerid.in=" + UPDATED_PROJECTMANAGERID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectmanageridIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is not null
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectmanagerid.specified=true");

        // Get all the employeeProjectRatings20190826List where projectmanagerid is null
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectmanagerid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectmanageridIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is greater than or equal to DEFAULT_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectmanagerid.greaterThanOrEqual=" + DEFAULT_PROJECTMANAGERID);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is greater than or equal to UPDATED_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectmanagerid.greaterThanOrEqual=" + UPDATED_PROJECTMANAGERID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectmanageridIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is less than or equal to DEFAULT_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectmanagerid.lessThanOrEqual=" + DEFAULT_PROJECTMANAGERID);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is less than or equal to SMALLER_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectmanagerid.lessThanOrEqual=" + SMALLER_PROJECTMANAGERID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectmanageridIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is less than DEFAULT_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectmanagerid.lessThan=" + DEFAULT_PROJECTMANAGERID);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is less than UPDATED_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectmanagerid.lessThan=" + UPDATED_PROJECTMANAGERID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectmanageridIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is greater than DEFAULT_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectmanagerid.greaterThan=" + DEFAULT_PROJECTMANAGERID);

        // Get all the employeeProjectRatings20190826List where projectmanagerid is greater than SMALLER_PROJECTMANAGERID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectmanagerid.greaterThan=" + SMALLER_PROJECTMANAGERID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByEmployeeidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where employeeid equals to DEFAULT_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("employeeid.equals=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeProjectRatings20190826List where employeeid equals to UPDATED_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("employeeid.equals=" + UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByEmployeeidIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where employeeid in DEFAULT_EMPLOYEEID or UPDATED_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("employeeid.in=" + DEFAULT_EMPLOYEEID + "," + UPDATED_EMPLOYEEID);

        // Get all the employeeProjectRatings20190826List where employeeid equals to UPDATED_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("employeeid.in=" + UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByEmployeeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where employeeid is not null
        defaultEmployeeProjectRatings20190826ShouldBeFound("employeeid.specified=true");

        // Get all the employeeProjectRatings20190826List where employeeid is null
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("employeeid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByEmployeeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where employeeid is greater than or equal to DEFAULT_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("employeeid.greaterThanOrEqual=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeProjectRatings20190826List where employeeid is greater than or equal to UPDATED_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("employeeid.greaterThanOrEqual=" + UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByEmployeeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where employeeid is less than or equal to DEFAULT_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("employeeid.lessThanOrEqual=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeProjectRatings20190826List where employeeid is less than or equal to SMALLER_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("employeeid.lessThanOrEqual=" + SMALLER_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByEmployeeidIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where employeeid is less than DEFAULT_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("employeeid.lessThan=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeProjectRatings20190826List where employeeid is less than UPDATED_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("employeeid.lessThan=" + UPDATED_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByEmployeeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where employeeid is greater than DEFAULT_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("employeeid.greaterThan=" + DEFAULT_EMPLOYEEID);

        // Get all the employeeProjectRatings20190826List where employeeid is greater than SMALLER_EMPLOYEEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("employeeid.greaterThan=" + SMALLER_EMPLOYEEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectcycleidIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectcycleid equals to DEFAULT_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectcycleid.equals=" + DEFAULT_PROJECTCYCLEID);

        // Get all the employeeProjectRatings20190826List where projectcycleid equals to UPDATED_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectcycleid.equals=" + UPDATED_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectcycleidIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectcycleid in DEFAULT_PROJECTCYCLEID or UPDATED_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectcycleid.in=" + DEFAULT_PROJECTCYCLEID + "," + UPDATED_PROJECTCYCLEID);

        // Get all the employeeProjectRatings20190826List where projectcycleid equals to UPDATED_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectcycleid.in=" + UPDATED_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectcycleidIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectcycleid is not null
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectcycleid.specified=true");

        // Get all the employeeProjectRatings20190826List where projectcycleid is null
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectcycleid.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectcycleidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectcycleid is greater than or equal to DEFAULT_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectcycleid.greaterThanOrEqual=" + DEFAULT_PROJECTCYCLEID);

        // Get all the employeeProjectRatings20190826List where projectcycleid is greater than or equal to UPDATED_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectcycleid.greaterThanOrEqual=" + UPDATED_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectcycleidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectcycleid is less than or equal to DEFAULT_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectcycleid.lessThanOrEqual=" + DEFAULT_PROJECTCYCLEID);

        // Get all the employeeProjectRatings20190826List where projectcycleid is less than or equal to SMALLER_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectcycleid.lessThanOrEqual=" + SMALLER_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectcycleidIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectcycleid is less than DEFAULT_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectcycleid.lessThan=" + DEFAULT_PROJECTCYCLEID);

        // Get all the employeeProjectRatings20190826List where projectcycleid is less than UPDATED_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectcycleid.lessThan=" + UPDATED_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings20190826sByProjectcycleidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        // Get all the employeeProjectRatings20190826List where projectcycleid is greater than DEFAULT_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldNotBeFound("projectcycleid.greaterThan=" + DEFAULT_PROJECTCYCLEID);

        // Get all the employeeProjectRatings20190826List where projectcycleid is greater than SMALLER_PROJECTCYCLEID
        defaultEmployeeProjectRatings20190826ShouldBeFound("projectcycleid.greaterThan=" + SMALLER_PROJECTCYCLEID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeProjectRatings20190826ShouldBeFound(String filter) throws Exception {
        restEmployeeProjectRatings20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProjectRatings20190826.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].pmqualityContentType").value(hasItem(DEFAULT_PMQUALITY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmquality").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMQUALITY))))
            .andExpect(jsonPath("$.[*].pmownershipContentType").value(hasItem(DEFAULT_PMOWNERSHIP_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmownership").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMOWNERSHIP))))
            .andExpect(jsonPath("$.[*].pmskillContentType").value(hasItem(DEFAULT_PMSKILL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmskill").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMSKILL))))
            .andExpect(jsonPath("$.[*].pmethicsContentType").value(hasItem(DEFAULT_PMETHICS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmethics").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMETHICS))))
            .andExpect(jsonPath("$.[*].pmefficiencyContentType").value(hasItem(DEFAULT_PMEFFICIENCY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmefficiency").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMEFFICIENCY))))
            .andExpect(jsonPath("$.[*].pmfreezeContentType").value(hasItem(DEFAULT_PMFREEZE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmfreeze").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMFREEZE))))
            .andExpect(jsonPath("$.[*].archfreezeContentType").value(hasItem(DEFAULT_ARCHFREEZE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archfreeze").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHFREEZE))))
            .andExpect(jsonPath("$.[*].pmcommentContentType").value(hasItem(DEFAULT_PMCOMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmcomment").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMCOMMENT))))
            .andExpect(jsonPath("$.[*].archqualityContentType").value(hasItem(DEFAULT_ARCHQUALITY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archquality").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHQUALITY))))
            .andExpect(jsonPath("$.[*].archownershipContentType").value(hasItem(DEFAULT_ARCHOWNERSHIP_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archownership").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHOWNERSHIP))))
            .andExpect(jsonPath("$.[*].archskillContentType").value(hasItem(DEFAULT_ARCHSKILL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archskill").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHSKILL))))
            .andExpect(jsonPath("$.[*].archethicsContentType").value(hasItem(DEFAULT_ARCHETHICS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archethics").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHETHICS))))
            .andExpect(jsonPath("$.[*].archefficiencyContentType").value(hasItem(DEFAULT_ARCHEFFICIENCY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archefficiency").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHEFFICIENCY))))
            .andExpect(jsonPath("$.[*].archcommentContentType").value(hasItem(DEFAULT_ARCHCOMMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archcomment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHCOMMENT))))
            .andExpect(jsonPath("$.[*].projectarchitectid").value(hasItem(DEFAULT_PROJECTARCHITECTID)))
            .andExpect(jsonPath("$.[*].projectmanagerid").value(hasItem(DEFAULT_PROJECTMANAGERID)))
            .andExpect(jsonPath("$.[*].employeeid").value(hasItem(DEFAULT_EMPLOYEEID)))
            .andExpect(jsonPath("$.[*].projectcycleid").value(hasItem(DEFAULT_PROJECTCYCLEID)));

        // Check, that the count call also returns 1
        restEmployeeProjectRatings20190826MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeProjectRatings20190826ShouldNotBeFound(String filter) throws Exception {
        restEmployeeProjectRatings20190826MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeProjectRatings20190826MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeProjectRatings20190826() throws Exception {
        // Get the employeeProjectRatings20190826
        restEmployeeProjectRatings20190826MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeProjectRatings20190826() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();

        // Update the employeeProjectRatings20190826
        EmployeeProjectRatings20190826 updatedEmployeeProjectRatings20190826 = employeeProjectRatings20190826Repository
            .findById(employeeProjectRatings20190826.getId())
            .get();
        // Disconnect from session so that the updates on updatedEmployeeProjectRatings20190826 are not directly saved in db
        em.detach(updatedEmployeeProjectRatings20190826);
        updatedEmployeeProjectRatings20190826
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .pmquality(UPDATED_PMQUALITY)
            .pmqualityContentType(UPDATED_PMQUALITY_CONTENT_TYPE)
            .pmownership(UPDATED_PMOWNERSHIP)
            .pmownershipContentType(UPDATED_PMOWNERSHIP_CONTENT_TYPE)
            .pmskill(UPDATED_PMSKILL)
            .pmskillContentType(UPDATED_PMSKILL_CONTENT_TYPE)
            .pmethics(UPDATED_PMETHICS)
            .pmethicsContentType(UPDATED_PMETHICS_CONTENT_TYPE)
            .pmefficiency(UPDATED_PMEFFICIENCY)
            .pmefficiencyContentType(UPDATED_PMEFFICIENCY_CONTENT_TYPE)
            .pmfreeze(UPDATED_PMFREEZE)
            .pmfreezeContentType(UPDATED_PMFREEZE_CONTENT_TYPE)
            .archfreeze(UPDATED_ARCHFREEZE)
            .archfreezeContentType(UPDATED_ARCHFREEZE_CONTENT_TYPE)
            .pmcomment(UPDATED_PMCOMMENT)
            .pmcommentContentType(UPDATED_PMCOMMENT_CONTENT_TYPE)
            .archquality(UPDATED_ARCHQUALITY)
            .archqualityContentType(UPDATED_ARCHQUALITY_CONTENT_TYPE)
            .archownership(UPDATED_ARCHOWNERSHIP)
            .archownershipContentType(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE)
            .archskill(UPDATED_ARCHSKILL)
            .archskillContentType(UPDATED_ARCHSKILL_CONTENT_TYPE)
            .archethics(UPDATED_ARCHETHICS)
            .archethicsContentType(UPDATED_ARCHETHICS_CONTENT_TYPE)
            .archefficiency(UPDATED_ARCHEFFICIENCY)
            .archefficiencyContentType(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE)
            .archcomment(UPDATED_ARCHCOMMENT)
            .archcommentContentType(UPDATED_ARCHCOMMENT_CONTENT_TYPE)
            .projectarchitectid(UPDATED_PROJECTARCHITECTID)
            .projectmanagerid(UPDATED_PROJECTMANAGERID)
            .employeeid(UPDATED_EMPLOYEEID)
            .projectcycleid(UPDATED_PROJECTCYCLEID);

        restEmployeeProjectRatings20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeProjectRatings20190826.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeProjectRatings20190826))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRatings20190826 testEmployeeProjectRatings20190826 = employeeProjectRatings20190826List.get(
            employeeProjectRatings20190826List.size() - 1
        );
        assertThat(testEmployeeProjectRatings20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjectRatings20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProjectRatings20190826.getPmquality()).isEqualTo(UPDATED_PMQUALITY);
        assertThat(testEmployeeProjectRatings20190826.getPmqualityContentType()).isEqualTo(UPDATED_PMQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmownership()).isEqualTo(UPDATED_PMOWNERSHIP);
        assertThat(testEmployeeProjectRatings20190826.getPmownershipContentType()).isEqualTo(UPDATED_PMOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmskill()).isEqualTo(UPDATED_PMSKILL);
        assertThat(testEmployeeProjectRatings20190826.getPmskillContentType()).isEqualTo(UPDATED_PMSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmethics()).isEqualTo(UPDATED_PMETHICS);
        assertThat(testEmployeeProjectRatings20190826.getPmethicsContentType()).isEqualTo(UPDATED_PMETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmefficiency()).isEqualTo(UPDATED_PMEFFICIENCY);
        assertThat(testEmployeeProjectRatings20190826.getPmefficiencyContentType()).isEqualTo(UPDATED_PMEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmfreeze()).isEqualTo(UPDATED_PMFREEZE);
        assertThat(testEmployeeProjectRatings20190826.getPmfreezeContentType()).isEqualTo(UPDATED_PMFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchfreeze()).isEqualTo(UPDATED_ARCHFREEZE);
        assertThat(testEmployeeProjectRatings20190826.getArchfreezeContentType()).isEqualTo(UPDATED_ARCHFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmcomment()).isEqualTo(UPDATED_PMCOMMENT);
        assertThat(testEmployeeProjectRatings20190826.getPmcommentContentType()).isEqualTo(UPDATED_PMCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchquality()).isEqualTo(UPDATED_ARCHQUALITY);
        assertThat(testEmployeeProjectRatings20190826.getArchqualityContentType()).isEqualTo(UPDATED_ARCHQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchownership()).isEqualTo(UPDATED_ARCHOWNERSHIP);
        assertThat(testEmployeeProjectRatings20190826.getArchownershipContentType()).isEqualTo(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchskill()).isEqualTo(UPDATED_ARCHSKILL);
        assertThat(testEmployeeProjectRatings20190826.getArchskillContentType()).isEqualTo(UPDATED_ARCHSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchethics()).isEqualTo(UPDATED_ARCHETHICS);
        assertThat(testEmployeeProjectRatings20190826.getArchethicsContentType()).isEqualTo(UPDATED_ARCHETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchefficiency()).isEqualTo(UPDATED_ARCHEFFICIENCY);
        assertThat(testEmployeeProjectRatings20190826.getArchefficiencyContentType()).isEqualTo(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchcomment()).isEqualTo(UPDATED_ARCHCOMMENT);
        assertThat(testEmployeeProjectRatings20190826.getArchcommentContentType()).isEqualTo(UPDATED_ARCHCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getProjectarchitectid()).isEqualTo(UPDATED_PROJECTARCHITECTID);
        assertThat(testEmployeeProjectRatings20190826.getProjectmanagerid()).isEqualTo(UPDATED_PROJECTMANAGERID);
        assertThat(testEmployeeProjectRatings20190826.getEmployeeid()).isEqualTo(UPDATED_EMPLOYEEID);
        assertThat(testEmployeeProjectRatings20190826.getProjectcycleid()).isEqualTo(UPDATED_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeProjectRatings20190826() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();
        employeeProjectRatings20190826.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectRatings20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeProjectRatings20190826.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeProjectRatings20190826() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();
        employeeProjectRatings20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRatings20190826MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeProjectRatings20190826() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();
        employeeProjectRatings20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRatings20190826MockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings20190826))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeProjectRatings20190826WithPatch() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();

        // Update the employeeProjectRatings20190826 using partial update
        EmployeeProjectRatings20190826 partialUpdatedEmployeeProjectRatings20190826 = new EmployeeProjectRatings20190826();
        partialUpdatedEmployeeProjectRatings20190826.setId(employeeProjectRatings20190826.getId());

        partialUpdatedEmployeeProjectRatings20190826
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .pmquality(UPDATED_PMQUALITY)
            .pmqualityContentType(UPDATED_PMQUALITY_CONTENT_TYPE)
            .pmethics(UPDATED_PMETHICS)
            .pmethicsContentType(UPDATED_PMETHICS_CONTENT_TYPE)
            .pmfreeze(UPDATED_PMFREEZE)
            .pmfreezeContentType(UPDATED_PMFREEZE_CONTENT_TYPE)
            .archfreeze(UPDATED_ARCHFREEZE)
            .archfreezeContentType(UPDATED_ARCHFREEZE_CONTENT_TYPE)
            .pmcomment(UPDATED_PMCOMMENT)
            .pmcommentContentType(UPDATED_PMCOMMENT_CONTENT_TYPE)
            .archquality(UPDATED_ARCHQUALITY)
            .archqualityContentType(UPDATED_ARCHQUALITY_CONTENT_TYPE)
            .archownership(UPDATED_ARCHOWNERSHIP)
            .archownershipContentType(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE)
            .archethics(UPDATED_ARCHETHICS)
            .archethicsContentType(UPDATED_ARCHETHICS_CONTENT_TYPE)
            .archefficiency(UPDATED_ARCHEFFICIENCY)
            .archefficiencyContentType(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE)
            .projectarchitectid(UPDATED_PROJECTARCHITECTID)
            .projectmanagerid(UPDATED_PROJECTMANAGERID);

        restEmployeeProjectRatings20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProjectRatings20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProjectRatings20190826))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRatings20190826 testEmployeeProjectRatings20190826 = employeeProjectRatings20190826List.get(
            employeeProjectRatings20190826List.size() - 1
        );
        assertThat(testEmployeeProjectRatings20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjectRatings20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProjectRatings20190826.getPmquality()).isEqualTo(UPDATED_PMQUALITY);
        assertThat(testEmployeeProjectRatings20190826.getPmqualityContentType()).isEqualTo(UPDATED_PMQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmownership()).isEqualTo(DEFAULT_PMOWNERSHIP);
        assertThat(testEmployeeProjectRatings20190826.getPmownershipContentType()).isEqualTo(DEFAULT_PMOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmskill()).isEqualTo(DEFAULT_PMSKILL);
        assertThat(testEmployeeProjectRatings20190826.getPmskillContentType()).isEqualTo(DEFAULT_PMSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmethics()).isEqualTo(UPDATED_PMETHICS);
        assertThat(testEmployeeProjectRatings20190826.getPmethicsContentType()).isEqualTo(UPDATED_PMETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmefficiency()).isEqualTo(DEFAULT_PMEFFICIENCY);
        assertThat(testEmployeeProjectRatings20190826.getPmefficiencyContentType()).isEqualTo(DEFAULT_PMEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmfreeze()).isEqualTo(UPDATED_PMFREEZE);
        assertThat(testEmployeeProjectRatings20190826.getPmfreezeContentType()).isEqualTo(UPDATED_PMFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchfreeze()).isEqualTo(UPDATED_ARCHFREEZE);
        assertThat(testEmployeeProjectRatings20190826.getArchfreezeContentType()).isEqualTo(UPDATED_ARCHFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmcomment()).isEqualTo(UPDATED_PMCOMMENT);
        assertThat(testEmployeeProjectRatings20190826.getPmcommentContentType()).isEqualTo(UPDATED_PMCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchquality()).isEqualTo(UPDATED_ARCHQUALITY);
        assertThat(testEmployeeProjectRatings20190826.getArchqualityContentType()).isEqualTo(UPDATED_ARCHQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchownership()).isEqualTo(UPDATED_ARCHOWNERSHIP);
        assertThat(testEmployeeProjectRatings20190826.getArchownershipContentType()).isEqualTo(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchskill()).isEqualTo(DEFAULT_ARCHSKILL);
        assertThat(testEmployeeProjectRatings20190826.getArchskillContentType()).isEqualTo(DEFAULT_ARCHSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchethics()).isEqualTo(UPDATED_ARCHETHICS);
        assertThat(testEmployeeProjectRatings20190826.getArchethicsContentType()).isEqualTo(UPDATED_ARCHETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchefficiency()).isEqualTo(UPDATED_ARCHEFFICIENCY);
        assertThat(testEmployeeProjectRatings20190826.getArchefficiencyContentType()).isEqualTo(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchcomment()).isEqualTo(DEFAULT_ARCHCOMMENT);
        assertThat(testEmployeeProjectRatings20190826.getArchcommentContentType()).isEqualTo(DEFAULT_ARCHCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getProjectarchitectid()).isEqualTo(UPDATED_PROJECTARCHITECTID);
        assertThat(testEmployeeProjectRatings20190826.getProjectmanagerid()).isEqualTo(UPDATED_PROJECTMANAGERID);
        assertThat(testEmployeeProjectRatings20190826.getEmployeeid()).isEqualTo(DEFAULT_EMPLOYEEID);
        assertThat(testEmployeeProjectRatings20190826.getProjectcycleid()).isEqualTo(DEFAULT_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeProjectRatings20190826WithPatch() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();

        // Update the employeeProjectRatings20190826 using partial update
        EmployeeProjectRatings20190826 partialUpdatedEmployeeProjectRatings20190826 = new EmployeeProjectRatings20190826();
        partialUpdatedEmployeeProjectRatings20190826.setId(employeeProjectRatings20190826.getId());

        partialUpdatedEmployeeProjectRatings20190826
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .pmquality(UPDATED_PMQUALITY)
            .pmqualityContentType(UPDATED_PMQUALITY_CONTENT_TYPE)
            .pmownership(UPDATED_PMOWNERSHIP)
            .pmownershipContentType(UPDATED_PMOWNERSHIP_CONTENT_TYPE)
            .pmskill(UPDATED_PMSKILL)
            .pmskillContentType(UPDATED_PMSKILL_CONTENT_TYPE)
            .pmethics(UPDATED_PMETHICS)
            .pmethicsContentType(UPDATED_PMETHICS_CONTENT_TYPE)
            .pmefficiency(UPDATED_PMEFFICIENCY)
            .pmefficiencyContentType(UPDATED_PMEFFICIENCY_CONTENT_TYPE)
            .pmfreeze(UPDATED_PMFREEZE)
            .pmfreezeContentType(UPDATED_PMFREEZE_CONTENT_TYPE)
            .archfreeze(UPDATED_ARCHFREEZE)
            .archfreezeContentType(UPDATED_ARCHFREEZE_CONTENT_TYPE)
            .pmcomment(UPDATED_PMCOMMENT)
            .pmcommentContentType(UPDATED_PMCOMMENT_CONTENT_TYPE)
            .archquality(UPDATED_ARCHQUALITY)
            .archqualityContentType(UPDATED_ARCHQUALITY_CONTENT_TYPE)
            .archownership(UPDATED_ARCHOWNERSHIP)
            .archownershipContentType(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE)
            .archskill(UPDATED_ARCHSKILL)
            .archskillContentType(UPDATED_ARCHSKILL_CONTENT_TYPE)
            .archethics(UPDATED_ARCHETHICS)
            .archethicsContentType(UPDATED_ARCHETHICS_CONTENT_TYPE)
            .archefficiency(UPDATED_ARCHEFFICIENCY)
            .archefficiencyContentType(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE)
            .archcomment(UPDATED_ARCHCOMMENT)
            .archcommentContentType(UPDATED_ARCHCOMMENT_CONTENT_TYPE)
            .projectarchitectid(UPDATED_PROJECTARCHITECTID)
            .projectmanagerid(UPDATED_PROJECTMANAGERID)
            .employeeid(UPDATED_EMPLOYEEID)
            .projectcycleid(UPDATED_PROJECTCYCLEID);

        restEmployeeProjectRatings20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProjectRatings20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProjectRatings20190826))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRatings20190826 testEmployeeProjectRatings20190826 = employeeProjectRatings20190826List.get(
            employeeProjectRatings20190826List.size() - 1
        );
        assertThat(testEmployeeProjectRatings20190826.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjectRatings20190826.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProjectRatings20190826.getPmquality()).isEqualTo(UPDATED_PMQUALITY);
        assertThat(testEmployeeProjectRatings20190826.getPmqualityContentType()).isEqualTo(UPDATED_PMQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmownership()).isEqualTo(UPDATED_PMOWNERSHIP);
        assertThat(testEmployeeProjectRatings20190826.getPmownershipContentType()).isEqualTo(UPDATED_PMOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmskill()).isEqualTo(UPDATED_PMSKILL);
        assertThat(testEmployeeProjectRatings20190826.getPmskillContentType()).isEqualTo(UPDATED_PMSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmethics()).isEqualTo(UPDATED_PMETHICS);
        assertThat(testEmployeeProjectRatings20190826.getPmethicsContentType()).isEqualTo(UPDATED_PMETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmefficiency()).isEqualTo(UPDATED_PMEFFICIENCY);
        assertThat(testEmployeeProjectRatings20190826.getPmefficiencyContentType()).isEqualTo(UPDATED_PMEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmfreeze()).isEqualTo(UPDATED_PMFREEZE);
        assertThat(testEmployeeProjectRatings20190826.getPmfreezeContentType()).isEqualTo(UPDATED_PMFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchfreeze()).isEqualTo(UPDATED_ARCHFREEZE);
        assertThat(testEmployeeProjectRatings20190826.getArchfreezeContentType()).isEqualTo(UPDATED_ARCHFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getPmcomment()).isEqualTo(UPDATED_PMCOMMENT);
        assertThat(testEmployeeProjectRatings20190826.getPmcommentContentType()).isEqualTo(UPDATED_PMCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchquality()).isEqualTo(UPDATED_ARCHQUALITY);
        assertThat(testEmployeeProjectRatings20190826.getArchqualityContentType()).isEqualTo(UPDATED_ARCHQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchownership()).isEqualTo(UPDATED_ARCHOWNERSHIP);
        assertThat(testEmployeeProjectRatings20190826.getArchownershipContentType()).isEqualTo(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchskill()).isEqualTo(UPDATED_ARCHSKILL);
        assertThat(testEmployeeProjectRatings20190826.getArchskillContentType()).isEqualTo(UPDATED_ARCHSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchethics()).isEqualTo(UPDATED_ARCHETHICS);
        assertThat(testEmployeeProjectRatings20190826.getArchethicsContentType()).isEqualTo(UPDATED_ARCHETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchefficiency()).isEqualTo(UPDATED_ARCHEFFICIENCY);
        assertThat(testEmployeeProjectRatings20190826.getArchefficiencyContentType()).isEqualTo(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getArchcomment()).isEqualTo(UPDATED_ARCHCOMMENT);
        assertThat(testEmployeeProjectRatings20190826.getArchcommentContentType()).isEqualTo(UPDATED_ARCHCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings20190826.getProjectarchitectid()).isEqualTo(UPDATED_PROJECTARCHITECTID);
        assertThat(testEmployeeProjectRatings20190826.getProjectmanagerid()).isEqualTo(UPDATED_PROJECTMANAGERID);
        assertThat(testEmployeeProjectRatings20190826.getEmployeeid()).isEqualTo(UPDATED_EMPLOYEEID);
        assertThat(testEmployeeProjectRatings20190826.getProjectcycleid()).isEqualTo(UPDATED_PROJECTCYCLEID);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeProjectRatings20190826() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();
        employeeProjectRatings20190826.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectRatings20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeProjectRatings20190826.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeProjectRatings20190826() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();
        employeeProjectRatings20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRatings20190826MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings20190826))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeProjectRatings20190826() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatings20190826Repository.findAll().size();
        employeeProjectRatings20190826.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRatings20190826MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings20190826))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProjectRatings20190826 in the database
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeProjectRatings20190826() throws Exception {
        // Initialize the database
        employeeProjectRatings20190826Repository.saveAndFlush(employeeProjectRatings20190826);

        int databaseSizeBeforeDelete = employeeProjectRatings20190826Repository.findAll().size();

        // Delete the employeeProjectRatings20190826
        restEmployeeProjectRatings20190826MockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeProjectRatings20190826.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeProjectRatings20190826> employeeProjectRatings20190826List = employeeProjectRatings20190826Repository.findAll();
        assertThat(employeeProjectRatings20190826List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
