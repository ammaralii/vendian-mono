package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.EmployeeProjectRatings;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.repository.EmployeeProjectRatingsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRatingsCriteria;
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
 * Integration tests for the {@link EmployeeProjectRatingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeProjectRatingsResourceIT {

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

    private static final byte[] DEFAULT_ARCHCODEQUALITY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHCODEQUALITY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHCODEQUALITY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHCODEQUALITY_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHDOCUMENTATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHDOCUMENTATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHDOCUMENTATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHDOCUMENTATION_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHCOLLABORATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHCOLLABORATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHCOLLABORATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHCOLLABORATION_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PMDOCUMENTATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMDOCUMENTATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMDOCUMENTATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMDOCUMENTATION_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PMCOLLABORATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PMCOLLABORATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PMCOLLABORATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PMCOLLABORATION_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/employee-project-ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeProjectRatingsRepository employeeProjectRatingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeProjectRatingsMockMvc;

    private EmployeeProjectRatings employeeProjectRatings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProjectRatings createEntity(EntityManager em) {
        EmployeeProjectRatings employeeProjectRatings = new EmployeeProjectRatings()
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
            .archcodequality(DEFAULT_ARCHCODEQUALITY)
            .archcodequalityContentType(DEFAULT_ARCHCODEQUALITY_CONTENT_TYPE)
            .archdocumentation(DEFAULT_ARCHDOCUMENTATION)
            .archdocumentationContentType(DEFAULT_ARCHDOCUMENTATION_CONTENT_TYPE)
            .archcollaboration(DEFAULT_ARCHCOLLABORATION)
            .archcollaborationContentType(DEFAULT_ARCHCOLLABORATION_CONTENT_TYPE)
            .pmdocumentation(DEFAULT_PMDOCUMENTATION)
            .pmdocumentationContentType(DEFAULT_PMDOCUMENTATION_CONTENT_TYPE)
            .pmcollaboration(DEFAULT_PMCOLLABORATION)
            .pmcollaborationContentType(DEFAULT_PMCOLLABORATION_CONTENT_TYPE);
        return employeeProjectRatings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProjectRatings createUpdatedEntity(EntityManager em) {
        EmployeeProjectRatings employeeProjectRatings = new EmployeeProjectRatings()
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
            .archcodequality(UPDATED_ARCHCODEQUALITY)
            .archcodequalityContentType(UPDATED_ARCHCODEQUALITY_CONTENT_TYPE)
            .archdocumentation(UPDATED_ARCHDOCUMENTATION)
            .archdocumentationContentType(UPDATED_ARCHDOCUMENTATION_CONTENT_TYPE)
            .archcollaboration(UPDATED_ARCHCOLLABORATION)
            .archcollaborationContentType(UPDATED_ARCHCOLLABORATION_CONTENT_TYPE)
            .pmdocumentation(UPDATED_PMDOCUMENTATION)
            .pmdocumentationContentType(UPDATED_PMDOCUMENTATION_CONTENT_TYPE)
            .pmcollaboration(UPDATED_PMCOLLABORATION)
            .pmcollaborationContentType(UPDATED_PMCOLLABORATION_CONTENT_TYPE);
        return employeeProjectRatings;
    }

    @BeforeEach
    public void initTest() {
        employeeProjectRatings = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeProjectRatings() throws Exception {
        int databaseSizeBeforeCreate = employeeProjectRatingsRepository.findAll().size();
        // Create the EmployeeProjectRatings
        restEmployeeProjectRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeProjectRatings testEmployeeProjectRatings = employeeProjectRatingsList.get(employeeProjectRatingsList.size() - 1);
        assertThat(testEmployeeProjectRatings.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testEmployeeProjectRatings.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeProjectRatings.getPmquality()).isEqualTo(DEFAULT_PMQUALITY);
        assertThat(testEmployeeProjectRatings.getPmqualityContentType()).isEqualTo(DEFAULT_PMQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmownership()).isEqualTo(DEFAULT_PMOWNERSHIP);
        assertThat(testEmployeeProjectRatings.getPmownershipContentType()).isEqualTo(DEFAULT_PMOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmskill()).isEqualTo(DEFAULT_PMSKILL);
        assertThat(testEmployeeProjectRatings.getPmskillContentType()).isEqualTo(DEFAULT_PMSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmethics()).isEqualTo(DEFAULT_PMETHICS);
        assertThat(testEmployeeProjectRatings.getPmethicsContentType()).isEqualTo(DEFAULT_PMETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmefficiency()).isEqualTo(DEFAULT_PMEFFICIENCY);
        assertThat(testEmployeeProjectRatings.getPmefficiencyContentType()).isEqualTo(DEFAULT_PMEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmfreeze()).isEqualTo(DEFAULT_PMFREEZE);
        assertThat(testEmployeeProjectRatings.getPmfreezeContentType()).isEqualTo(DEFAULT_PMFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchfreeze()).isEqualTo(DEFAULT_ARCHFREEZE);
        assertThat(testEmployeeProjectRatings.getArchfreezeContentType()).isEqualTo(DEFAULT_ARCHFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmcomment()).isEqualTo(DEFAULT_PMCOMMENT);
        assertThat(testEmployeeProjectRatings.getPmcommentContentType()).isEqualTo(DEFAULT_PMCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchquality()).isEqualTo(DEFAULT_ARCHQUALITY);
        assertThat(testEmployeeProjectRatings.getArchqualityContentType()).isEqualTo(DEFAULT_ARCHQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchownership()).isEqualTo(DEFAULT_ARCHOWNERSHIP);
        assertThat(testEmployeeProjectRatings.getArchownershipContentType()).isEqualTo(DEFAULT_ARCHOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchskill()).isEqualTo(DEFAULT_ARCHSKILL);
        assertThat(testEmployeeProjectRatings.getArchskillContentType()).isEqualTo(DEFAULT_ARCHSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchethics()).isEqualTo(DEFAULT_ARCHETHICS);
        assertThat(testEmployeeProjectRatings.getArchethicsContentType()).isEqualTo(DEFAULT_ARCHETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchefficiency()).isEqualTo(DEFAULT_ARCHEFFICIENCY);
        assertThat(testEmployeeProjectRatings.getArchefficiencyContentType()).isEqualTo(DEFAULT_ARCHEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcomment()).isEqualTo(DEFAULT_ARCHCOMMENT);
        assertThat(testEmployeeProjectRatings.getArchcommentContentType()).isEqualTo(DEFAULT_ARCHCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcodequality()).isEqualTo(DEFAULT_ARCHCODEQUALITY);
        assertThat(testEmployeeProjectRatings.getArchcodequalityContentType()).isEqualTo(DEFAULT_ARCHCODEQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchdocumentation()).isEqualTo(DEFAULT_ARCHDOCUMENTATION);
        assertThat(testEmployeeProjectRatings.getArchdocumentationContentType()).isEqualTo(DEFAULT_ARCHDOCUMENTATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcollaboration()).isEqualTo(DEFAULT_ARCHCOLLABORATION);
        assertThat(testEmployeeProjectRatings.getArchcollaborationContentType()).isEqualTo(DEFAULT_ARCHCOLLABORATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmdocumentation()).isEqualTo(DEFAULT_PMDOCUMENTATION);
        assertThat(testEmployeeProjectRatings.getPmdocumentationContentType()).isEqualTo(DEFAULT_PMDOCUMENTATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmcollaboration()).isEqualTo(DEFAULT_PMCOLLABORATION);
        assertThat(testEmployeeProjectRatings.getPmcollaborationContentType()).isEqualTo(DEFAULT_PMCOLLABORATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createEmployeeProjectRatingsWithExistingId() throws Exception {
        // Create the EmployeeProjectRatings with an existing ID
        employeeProjectRatings.setId(1L);

        int databaseSizeBeforeCreate = employeeProjectRatingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeProjectRatingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatings() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        // Get all the employeeProjectRatingsList
        restEmployeeProjectRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProjectRatings.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].archcodequalityContentType").value(hasItem(DEFAULT_ARCHCODEQUALITY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archcodequality").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHCODEQUALITY))))
            .andExpect(jsonPath("$.[*].archdocumentationContentType").value(hasItem(DEFAULT_ARCHDOCUMENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archdocumentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHDOCUMENTATION))))
            .andExpect(jsonPath("$.[*].archcollaborationContentType").value(hasItem(DEFAULT_ARCHCOLLABORATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archcollaboration").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHCOLLABORATION))))
            .andExpect(jsonPath("$.[*].pmdocumentationContentType").value(hasItem(DEFAULT_PMDOCUMENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmdocumentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMDOCUMENTATION))))
            .andExpect(jsonPath("$.[*].pmcollaborationContentType").value(hasItem(DEFAULT_PMCOLLABORATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmcollaboration").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMCOLLABORATION))));
    }

    @Test
    @Transactional
    void getEmployeeProjectRatings() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        // Get the employeeProjectRatings
        restEmployeeProjectRatingsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeProjectRatings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeProjectRatings.getId().intValue()))
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
            .andExpect(jsonPath("$.archcodequalityContentType").value(DEFAULT_ARCHCODEQUALITY_CONTENT_TYPE))
            .andExpect(jsonPath("$.archcodequality").value(Base64Utils.encodeToString(DEFAULT_ARCHCODEQUALITY)))
            .andExpect(jsonPath("$.archdocumentationContentType").value(DEFAULT_ARCHDOCUMENTATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.archdocumentation").value(Base64Utils.encodeToString(DEFAULT_ARCHDOCUMENTATION)))
            .andExpect(jsonPath("$.archcollaborationContentType").value(DEFAULT_ARCHCOLLABORATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.archcollaboration").value(Base64Utils.encodeToString(DEFAULT_ARCHCOLLABORATION)))
            .andExpect(jsonPath("$.pmdocumentationContentType").value(DEFAULT_PMDOCUMENTATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmdocumentation").value(Base64Utils.encodeToString(DEFAULT_PMDOCUMENTATION)))
            .andExpect(jsonPath("$.pmcollaborationContentType").value(DEFAULT_PMCOLLABORATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.pmcollaboration").value(Base64Utils.encodeToString(DEFAULT_PMCOLLABORATION)));
    }

    @Test
    @Transactional
    void getEmployeeProjectRatingsByIdFiltering() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        Long id = employeeProjectRatings.getId();

        defaultEmployeeProjectRatingsShouldBeFound("id.equals=" + id);
        defaultEmployeeProjectRatingsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeProjectRatingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeProjectRatingsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeProjectRatingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeProjectRatingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        // Get all the employeeProjectRatingsList where createdat equals to DEFAULT_CREATEDAT
        defaultEmployeeProjectRatingsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the employeeProjectRatingsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProjectRatingsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        // Get all the employeeProjectRatingsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultEmployeeProjectRatingsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the employeeProjectRatingsList where createdat equals to UPDATED_CREATEDAT
        defaultEmployeeProjectRatingsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        // Get all the employeeProjectRatingsList where createdat is not null
        defaultEmployeeProjectRatingsShouldBeFound("createdat.specified=true");

        // Get all the employeeProjectRatingsList where createdat is null
        defaultEmployeeProjectRatingsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        // Get all the employeeProjectRatingsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultEmployeeProjectRatingsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the employeeProjectRatingsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProjectRatingsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        // Get all the employeeProjectRatingsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultEmployeeProjectRatingsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the employeeProjectRatingsList where updatedat equals to UPDATED_UPDATEDAT
        defaultEmployeeProjectRatingsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        // Get all the employeeProjectRatingsList where updatedat is not null
        defaultEmployeeProjectRatingsShouldBeFound("updatedat.specified=true");

        // Get all the employeeProjectRatingsList where updatedat is null
        defaultEmployeeProjectRatingsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByProjectarchitectIsEqualToSomething() throws Exception {
        Employees projectarchitect;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);
            projectarchitect = EmployeesResourceIT.createEntity(em);
        } else {
            projectarchitect = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(projectarchitect);
        em.flush();
        employeeProjectRatings.setProjectarchitect(projectarchitect);
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);
        Long projectarchitectId = projectarchitect.getId();

        // Get all the employeeProjectRatingsList where projectarchitect equals to projectarchitectId
        defaultEmployeeProjectRatingsShouldBeFound("projectarchitectId.equals=" + projectarchitectId);

        // Get all the employeeProjectRatingsList where projectarchitect equals to (projectarchitectId + 1)
        defaultEmployeeProjectRatingsShouldNotBeFound("projectarchitectId.equals=" + (projectarchitectId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByProjectmanagerIsEqualToSomething() throws Exception {
        Employees projectmanager;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);
            projectmanager = EmployeesResourceIT.createEntity(em);
        } else {
            projectmanager = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(projectmanager);
        em.flush();
        employeeProjectRatings.setProjectmanager(projectmanager);
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);
        Long projectmanagerId = projectmanager.getId();

        // Get all the employeeProjectRatingsList where projectmanager equals to projectmanagerId
        defaultEmployeeProjectRatingsShouldBeFound("projectmanagerId.equals=" + projectmanagerId);

        // Get all the employeeProjectRatingsList where projectmanager equals to (projectmanagerId + 1)
        defaultEmployeeProjectRatingsShouldNotBeFound("projectmanagerId.equals=" + (projectmanagerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByEmployeeIsEqualToSomething() throws Exception {
        Employees employee;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);
            employee = EmployeesResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeProjectRatings.setEmployee(employee);
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);
        Long employeeId = employee.getId();

        // Get all the employeeProjectRatingsList where employee equals to employeeId
        defaultEmployeeProjectRatingsShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeProjectRatingsList where employee equals to (employeeId + 1)
        defaultEmployeeProjectRatingsShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeProjectRatingsByProjectcycleIsEqualToSomething() throws Exception {
        ProjectCycles projectcycle;
        if (TestUtil.findAll(em, ProjectCycles.class).isEmpty()) {
            employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);
            projectcycle = ProjectCyclesResourceIT.createEntity(em);
        } else {
            projectcycle = TestUtil.findAll(em, ProjectCycles.class).get(0);
        }
        em.persist(projectcycle);
        em.flush();
        employeeProjectRatings.setProjectcycle(projectcycle);
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);
        Long projectcycleId = projectcycle.getId();

        // Get all the employeeProjectRatingsList where projectcycle equals to projectcycleId
        defaultEmployeeProjectRatingsShouldBeFound("projectcycleId.equals=" + projectcycleId);

        // Get all the employeeProjectRatingsList where projectcycle equals to (projectcycleId + 1)
        defaultEmployeeProjectRatingsShouldNotBeFound("projectcycleId.equals=" + (projectcycleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeProjectRatingsShouldBeFound(String filter) throws Exception {
        restEmployeeProjectRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProjectRatings.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].archcodequalityContentType").value(hasItem(DEFAULT_ARCHCODEQUALITY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archcodequality").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHCODEQUALITY))))
            .andExpect(jsonPath("$.[*].archdocumentationContentType").value(hasItem(DEFAULT_ARCHDOCUMENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archdocumentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHDOCUMENTATION))))
            .andExpect(jsonPath("$.[*].archcollaborationContentType").value(hasItem(DEFAULT_ARCHCOLLABORATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archcollaboration").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHCOLLABORATION))))
            .andExpect(jsonPath("$.[*].pmdocumentationContentType").value(hasItem(DEFAULT_PMDOCUMENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmdocumentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMDOCUMENTATION))))
            .andExpect(jsonPath("$.[*].pmcollaborationContentType").value(hasItem(DEFAULT_PMCOLLABORATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pmcollaboration").value(hasItem(Base64Utils.encodeToString(DEFAULT_PMCOLLABORATION))));

        // Check, that the count call also returns 1
        restEmployeeProjectRatingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeProjectRatingsShouldNotBeFound(String filter) throws Exception {
        restEmployeeProjectRatingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeProjectRatingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeProjectRatings() throws Exception {
        // Get the employeeProjectRatings
        restEmployeeProjectRatingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeProjectRatings() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();

        // Update the employeeProjectRatings
        EmployeeProjectRatings updatedEmployeeProjectRatings = employeeProjectRatingsRepository
            .findById(employeeProjectRatings.getId())
            .get();
        // Disconnect from session so that the updates on updatedEmployeeProjectRatings are not directly saved in db
        em.detach(updatedEmployeeProjectRatings);
        updatedEmployeeProjectRatings
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
            .archcodequality(UPDATED_ARCHCODEQUALITY)
            .archcodequalityContentType(UPDATED_ARCHCODEQUALITY_CONTENT_TYPE)
            .archdocumentation(UPDATED_ARCHDOCUMENTATION)
            .archdocumentationContentType(UPDATED_ARCHDOCUMENTATION_CONTENT_TYPE)
            .archcollaboration(UPDATED_ARCHCOLLABORATION)
            .archcollaborationContentType(UPDATED_ARCHCOLLABORATION_CONTENT_TYPE)
            .pmdocumentation(UPDATED_PMDOCUMENTATION)
            .pmdocumentationContentType(UPDATED_PMDOCUMENTATION_CONTENT_TYPE)
            .pmcollaboration(UPDATED_PMCOLLABORATION)
            .pmcollaborationContentType(UPDATED_PMCOLLABORATION_CONTENT_TYPE);

        restEmployeeProjectRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeProjectRatings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeProjectRatings))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRatings testEmployeeProjectRatings = employeeProjectRatingsList.get(employeeProjectRatingsList.size() - 1);
        assertThat(testEmployeeProjectRatings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjectRatings.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProjectRatings.getPmquality()).isEqualTo(UPDATED_PMQUALITY);
        assertThat(testEmployeeProjectRatings.getPmqualityContentType()).isEqualTo(UPDATED_PMQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmownership()).isEqualTo(UPDATED_PMOWNERSHIP);
        assertThat(testEmployeeProjectRatings.getPmownershipContentType()).isEqualTo(UPDATED_PMOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmskill()).isEqualTo(UPDATED_PMSKILL);
        assertThat(testEmployeeProjectRatings.getPmskillContentType()).isEqualTo(UPDATED_PMSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmethics()).isEqualTo(UPDATED_PMETHICS);
        assertThat(testEmployeeProjectRatings.getPmethicsContentType()).isEqualTo(UPDATED_PMETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmefficiency()).isEqualTo(UPDATED_PMEFFICIENCY);
        assertThat(testEmployeeProjectRatings.getPmefficiencyContentType()).isEqualTo(UPDATED_PMEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmfreeze()).isEqualTo(UPDATED_PMFREEZE);
        assertThat(testEmployeeProjectRatings.getPmfreezeContentType()).isEqualTo(UPDATED_PMFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchfreeze()).isEqualTo(UPDATED_ARCHFREEZE);
        assertThat(testEmployeeProjectRatings.getArchfreezeContentType()).isEqualTo(UPDATED_ARCHFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmcomment()).isEqualTo(UPDATED_PMCOMMENT);
        assertThat(testEmployeeProjectRatings.getPmcommentContentType()).isEqualTo(UPDATED_PMCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchquality()).isEqualTo(UPDATED_ARCHQUALITY);
        assertThat(testEmployeeProjectRatings.getArchqualityContentType()).isEqualTo(UPDATED_ARCHQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchownership()).isEqualTo(UPDATED_ARCHOWNERSHIP);
        assertThat(testEmployeeProjectRatings.getArchownershipContentType()).isEqualTo(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchskill()).isEqualTo(UPDATED_ARCHSKILL);
        assertThat(testEmployeeProjectRatings.getArchskillContentType()).isEqualTo(UPDATED_ARCHSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchethics()).isEqualTo(UPDATED_ARCHETHICS);
        assertThat(testEmployeeProjectRatings.getArchethicsContentType()).isEqualTo(UPDATED_ARCHETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchefficiency()).isEqualTo(UPDATED_ARCHEFFICIENCY);
        assertThat(testEmployeeProjectRatings.getArchefficiencyContentType()).isEqualTo(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcomment()).isEqualTo(UPDATED_ARCHCOMMENT);
        assertThat(testEmployeeProjectRatings.getArchcommentContentType()).isEqualTo(UPDATED_ARCHCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcodequality()).isEqualTo(UPDATED_ARCHCODEQUALITY);
        assertThat(testEmployeeProjectRatings.getArchcodequalityContentType()).isEqualTo(UPDATED_ARCHCODEQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchdocumentation()).isEqualTo(UPDATED_ARCHDOCUMENTATION);
        assertThat(testEmployeeProjectRatings.getArchdocumentationContentType()).isEqualTo(UPDATED_ARCHDOCUMENTATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcollaboration()).isEqualTo(UPDATED_ARCHCOLLABORATION);
        assertThat(testEmployeeProjectRatings.getArchcollaborationContentType()).isEqualTo(UPDATED_ARCHCOLLABORATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmdocumentation()).isEqualTo(UPDATED_PMDOCUMENTATION);
        assertThat(testEmployeeProjectRatings.getPmdocumentationContentType()).isEqualTo(UPDATED_PMDOCUMENTATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmcollaboration()).isEqualTo(UPDATED_PMCOLLABORATION);
        assertThat(testEmployeeProjectRatings.getPmcollaborationContentType()).isEqualTo(UPDATED_PMCOLLABORATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeProjectRatings() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();
        employeeProjectRatings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeProjectRatings.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeProjectRatings() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();
        employeeProjectRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRatingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeProjectRatings() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();
        employeeProjectRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRatingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeProjectRatingsWithPatch() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();

        // Update the employeeProjectRatings using partial update
        EmployeeProjectRatings partialUpdatedEmployeeProjectRatings = new EmployeeProjectRatings();
        partialUpdatedEmployeeProjectRatings.setId(employeeProjectRatings.getId());

        partialUpdatedEmployeeProjectRatings
            .createdat(UPDATED_CREATEDAT)
            .pmownership(UPDATED_PMOWNERSHIP)
            .pmownershipContentType(UPDATED_PMOWNERSHIP_CONTENT_TYPE)
            .pmefficiency(UPDATED_PMEFFICIENCY)
            .pmefficiencyContentType(UPDATED_PMEFFICIENCY_CONTENT_TYPE)
            .pmcomment(UPDATED_PMCOMMENT)
            .pmcommentContentType(UPDATED_PMCOMMENT_CONTENT_TYPE)
            .archownership(UPDATED_ARCHOWNERSHIP)
            .archownershipContentType(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE)
            .archethics(UPDATED_ARCHETHICS)
            .archethicsContentType(UPDATED_ARCHETHICS_CONTENT_TYPE)
            .archefficiency(UPDATED_ARCHEFFICIENCY)
            .archefficiencyContentType(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE)
            .archcomment(UPDATED_ARCHCOMMENT)
            .archcommentContentType(UPDATED_ARCHCOMMENT_CONTENT_TYPE)
            .archcollaboration(UPDATED_ARCHCOLLABORATION)
            .archcollaborationContentType(UPDATED_ARCHCOLLABORATION_CONTENT_TYPE)
            .pmdocumentation(UPDATED_PMDOCUMENTATION)
            .pmdocumentationContentType(UPDATED_PMDOCUMENTATION_CONTENT_TYPE);

        restEmployeeProjectRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProjectRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProjectRatings))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRatings testEmployeeProjectRatings = employeeProjectRatingsList.get(employeeProjectRatingsList.size() - 1);
        assertThat(testEmployeeProjectRatings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjectRatings.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testEmployeeProjectRatings.getPmquality()).isEqualTo(DEFAULT_PMQUALITY);
        assertThat(testEmployeeProjectRatings.getPmqualityContentType()).isEqualTo(DEFAULT_PMQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmownership()).isEqualTo(UPDATED_PMOWNERSHIP);
        assertThat(testEmployeeProjectRatings.getPmownershipContentType()).isEqualTo(UPDATED_PMOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmskill()).isEqualTo(DEFAULT_PMSKILL);
        assertThat(testEmployeeProjectRatings.getPmskillContentType()).isEqualTo(DEFAULT_PMSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmethics()).isEqualTo(DEFAULT_PMETHICS);
        assertThat(testEmployeeProjectRatings.getPmethicsContentType()).isEqualTo(DEFAULT_PMETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmefficiency()).isEqualTo(UPDATED_PMEFFICIENCY);
        assertThat(testEmployeeProjectRatings.getPmefficiencyContentType()).isEqualTo(UPDATED_PMEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmfreeze()).isEqualTo(DEFAULT_PMFREEZE);
        assertThat(testEmployeeProjectRatings.getPmfreezeContentType()).isEqualTo(DEFAULT_PMFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchfreeze()).isEqualTo(DEFAULT_ARCHFREEZE);
        assertThat(testEmployeeProjectRatings.getArchfreezeContentType()).isEqualTo(DEFAULT_ARCHFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmcomment()).isEqualTo(UPDATED_PMCOMMENT);
        assertThat(testEmployeeProjectRatings.getPmcommentContentType()).isEqualTo(UPDATED_PMCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchquality()).isEqualTo(DEFAULT_ARCHQUALITY);
        assertThat(testEmployeeProjectRatings.getArchqualityContentType()).isEqualTo(DEFAULT_ARCHQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchownership()).isEqualTo(UPDATED_ARCHOWNERSHIP);
        assertThat(testEmployeeProjectRatings.getArchownershipContentType()).isEqualTo(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchskill()).isEqualTo(DEFAULT_ARCHSKILL);
        assertThat(testEmployeeProjectRatings.getArchskillContentType()).isEqualTo(DEFAULT_ARCHSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchethics()).isEqualTo(UPDATED_ARCHETHICS);
        assertThat(testEmployeeProjectRatings.getArchethicsContentType()).isEqualTo(UPDATED_ARCHETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchefficiency()).isEqualTo(UPDATED_ARCHEFFICIENCY);
        assertThat(testEmployeeProjectRatings.getArchefficiencyContentType()).isEqualTo(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcomment()).isEqualTo(UPDATED_ARCHCOMMENT);
        assertThat(testEmployeeProjectRatings.getArchcommentContentType()).isEqualTo(UPDATED_ARCHCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcodequality()).isEqualTo(DEFAULT_ARCHCODEQUALITY);
        assertThat(testEmployeeProjectRatings.getArchcodequalityContentType()).isEqualTo(DEFAULT_ARCHCODEQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchdocumentation()).isEqualTo(DEFAULT_ARCHDOCUMENTATION);
        assertThat(testEmployeeProjectRatings.getArchdocumentationContentType()).isEqualTo(DEFAULT_ARCHDOCUMENTATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcollaboration()).isEqualTo(UPDATED_ARCHCOLLABORATION);
        assertThat(testEmployeeProjectRatings.getArchcollaborationContentType()).isEqualTo(UPDATED_ARCHCOLLABORATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmdocumentation()).isEqualTo(UPDATED_PMDOCUMENTATION);
        assertThat(testEmployeeProjectRatings.getPmdocumentationContentType()).isEqualTo(UPDATED_PMDOCUMENTATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmcollaboration()).isEqualTo(DEFAULT_PMCOLLABORATION);
        assertThat(testEmployeeProjectRatings.getPmcollaborationContentType()).isEqualTo(DEFAULT_PMCOLLABORATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeProjectRatingsWithPatch() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();

        // Update the employeeProjectRatings using partial update
        EmployeeProjectRatings partialUpdatedEmployeeProjectRatings = new EmployeeProjectRatings();
        partialUpdatedEmployeeProjectRatings.setId(employeeProjectRatings.getId());

        partialUpdatedEmployeeProjectRatings
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
            .archcodequality(UPDATED_ARCHCODEQUALITY)
            .archcodequalityContentType(UPDATED_ARCHCODEQUALITY_CONTENT_TYPE)
            .archdocumentation(UPDATED_ARCHDOCUMENTATION)
            .archdocumentationContentType(UPDATED_ARCHDOCUMENTATION_CONTENT_TYPE)
            .archcollaboration(UPDATED_ARCHCOLLABORATION)
            .archcollaborationContentType(UPDATED_ARCHCOLLABORATION_CONTENT_TYPE)
            .pmdocumentation(UPDATED_PMDOCUMENTATION)
            .pmdocumentationContentType(UPDATED_PMDOCUMENTATION_CONTENT_TYPE)
            .pmcollaboration(UPDATED_PMCOLLABORATION)
            .pmcollaborationContentType(UPDATED_PMCOLLABORATION_CONTENT_TYPE);

        restEmployeeProjectRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeProjectRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeProjectRatings))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProjectRatings testEmployeeProjectRatings = employeeProjectRatingsList.get(employeeProjectRatingsList.size() - 1);
        assertThat(testEmployeeProjectRatings.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testEmployeeProjectRatings.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testEmployeeProjectRatings.getPmquality()).isEqualTo(UPDATED_PMQUALITY);
        assertThat(testEmployeeProjectRatings.getPmqualityContentType()).isEqualTo(UPDATED_PMQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmownership()).isEqualTo(UPDATED_PMOWNERSHIP);
        assertThat(testEmployeeProjectRatings.getPmownershipContentType()).isEqualTo(UPDATED_PMOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmskill()).isEqualTo(UPDATED_PMSKILL);
        assertThat(testEmployeeProjectRatings.getPmskillContentType()).isEqualTo(UPDATED_PMSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmethics()).isEqualTo(UPDATED_PMETHICS);
        assertThat(testEmployeeProjectRatings.getPmethicsContentType()).isEqualTo(UPDATED_PMETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmefficiency()).isEqualTo(UPDATED_PMEFFICIENCY);
        assertThat(testEmployeeProjectRatings.getPmefficiencyContentType()).isEqualTo(UPDATED_PMEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmfreeze()).isEqualTo(UPDATED_PMFREEZE);
        assertThat(testEmployeeProjectRatings.getPmfreezeContentType()).isEqualTo(UPDATED_PMFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchfreeze()).isEqualTo(UPDATED_ARCHFREEZE);
        assertThat(testEmployeeProjectRatings.getArchfreezeContentType()).isEqualTo(UPDATED_ARCHFREEZE_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmcomment()).isEqualTo(UPDATED_PMCOMMENT);
        assertThat(testEmployeeProjectRatings.getPmcommentContentType()).isEqualTo(UPDATED_PMCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchquality()).isEqualTo(UPDATED_ARCHQUALITY);
        assertThat(testEmployeeProjectRatings.getArchqualityContentType()).isEqualTo(UPDATED_ARCHQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchownership()).isEqualTo(UPDATED_ARCHOWNERSHIP);
        assertThat(testEmployeeProjectRatings.getArchownershipContentType()).isEqualTo(UPDATED_ARCHOWNERSHIP_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchskill()).isEqualTo(UPDATED_ARCHSKILL);
        assertThat(testEmployeeProjectRatings.getArchskillContentType()).isEqualTo(UPDATED_ARCHSKILL_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchethics()).isEqualTo(UPDATED_ARCHETHICS);
        assertThat(testEmployeeProjectRatings.getArchethicsContentType()).isEqualTo(UPDATED_ARCHETHICS_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchefficiency()).isEqualTo(UPDATED_ARCHEFFICIENCY);
        assertThat(testEmployeeProjectRatings.getArchefficiencyContentType()).isEqualTo(UPDATED_ARCHEFFICIENCY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcomment()).isEqualTo(UPDATED_ARCHCOMMENT);
        assertThat(testEmployeeProjectRatings.getArchcommentContentType()).isEqualTo(UPDATED_ARCHCOMMENT_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcodequality()).isEqualTo(UPDATED_ARCHCODEQUALITY);
        assertThat(testEmployeeProjectRatings.getArchcodequalityContentType()).isEqualTo(UPDATED_ARCHCODEQUALITY_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchdocumentation()).isEqualTo(UPDATED_ARCHDOCUMENTATION);
        assertThat(testEmployeeProjectRatings.getArchdocumentationContentType()).isEqualTo(UPDATED_ARCHDOCUMENTATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getArchcollaboration()).isEqualTo(UPDATED_ARCHCOLLABORATION);
        assertThat(testEmployeeProjectRatings.getArchcollaborationContentType()).isEqualTo(UPDATED_ARCHCOLLABORATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmdocumentation()).isEqualTo(UPDATED_PMDOCUMENTATION);
        assertThat(testEmployeeProjectRatings.getPmdocumentationContentType()).isEqualTo(UPDATED_PMDOCUMENTATION_CONTENT_TYPE);
        assertThat(testEmployeeProjectRatings.getPmcollaboration()).isEqualTo(UPDATED_PMCOLLABORATION);
        assertThat(testEmployeeProjectRatings.getPmcollaborationContentType()).isEqualTo(UPDATED_PMCOLLABORATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeProjectRatings() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();
        employeeProjectRatings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeProjectRatings.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeProjectRatings() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();
        employeeProjectRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeProjectRatings() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRatingsRepository.findAll().size();
        employeeProjectRatings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeProjectRatingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeProjectRatings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeProjectRatings in the database
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeProjectRatings() throws Exception {
        // Initialize the database
        employeeProjectRatingsRepository.saveAndFlush(employeeProjectRatings);

        int databaseSizeBeforeDelete = employeeProjectRatingsRepository.findAll().size();

        // Delete the employeeProjectRatings
        restEmployeeProjectRatingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeProjectRatings.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeProjectRatings> employeeProjectRatingsList = employeeProjectRatingsRepository.findAll();
        assertThat(employeeProjectRatingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
