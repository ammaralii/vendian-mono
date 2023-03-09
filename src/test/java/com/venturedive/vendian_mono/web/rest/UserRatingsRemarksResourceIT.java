package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Designations;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import com.venturedive.vendian_mono.domain.UserRatingsRemarks;
import com.venturedive.vendian_mono.repository.UserRatingsRemarksRepository;
import com.venturedive.vendian_mono.service.criteria.UserRatingsRemarksCriteria;
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
 * Integration tests for the {@link UserRatingsRemarksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserRatingsRemarksResourceIT {

    private static final Boolean DEFAULT_IS_PROMOTION = false;
    private static final Boolean UPDATED_IS_PROMOTION = true;

    private static final String DEFAULT_STRENGTH = "AAAAAAAAAA";
    private static final String UPDATED_STRENGTH = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_OF_IMPROVEMENT = "AAAAAAAAAA";
    private static final String UPDATED_AREA_OF_IMPROVEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PROMOTION_JUSTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTION_JUSTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_GRADE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_REDESIGNATION = false;
    private static final Boolean UPDATED_IS_REDESIGNATION = true;

    private static final Integer DEFAULT_RECOMMENDED_SALARY = 1;
    private static final Integer UPDATED_RECOMMENDED_SALARY = 2;
    private static final Integer SMALLER_RECOMMENDED_SALARY = 1 - 1;

    private static final String DEFAULT_STATUS = "AAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String DEFAULT_OTHER_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-ratings-remarks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserRatingsRemarksRepository userRatingsRemarksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserRatingsRemarksMockMvc;

    private UserRatingsRemarks userRatingsRemarks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRatingsRemarks createEntity(EntityManager em) {
        UserRatingsRemarks userRatingsRemarks = new UserRatingsRemarks()
            .isPromotion(DEFAULT_IS_PROMOTION)
            .strength(DEFAULT_STRENGTH)
            .areaOfImprovement(DEFAULT_AREA_OF_IMPROVEMENT)
            .promotionJustification(DEFAULT_PROMOTION_JUSTIFICATION)
            .newGrade(DEFAULT_NEW_GRADE)
            .isRedesignation(DEFAULT_IS_REDESIGNATION)
            .recommendedSalary(DEFAULT_RECOMMENDED_SALARY)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION)
            .otherComments(DEFAULT_OTHER_COMMENTS);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        userRatingsRemarks.setRater(employees);
        // Add required entity
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            performanceCycleEmployeeRating = PerformanceCycleEmployeeRatingResourceIT.createEntity(em);
            em.persist(performanceCycleEmployeeRating);
            em.flush();
        } else {
            performanceCycleEmployeeRating = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        userRatingsRemarks.setPcEmployeeRating(performanceCycleEmployeeRating);
        return userRatingsRemarks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRatingsRemarks createUpdatedEntity(EntityManager em) {
        UserRatingsRemarks userRatingsRemarks = new UserRatingsRemarks()
            .isPromotion(UPDATED_IS_PROMOTION)
            .strength(UPDATED_STRENGTH)
            .areaOfImprovement(UPDATED_AREA_OF_IMPROVEMENT)
            .promotionJustification(UPDATED_PROMOTION_JUSTIFICATION)
            .newGrade(UPDATED_NEW_GRADE)
            .isRedesignation(UPDATED_IS_REDESIGNATION)
            .recommendedSalary(UPDATED_RECOMMENDED_SALARY)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION)
            .otherComments(UPDATED_OTHER_COMMENTS);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        userRatingsRemarks.setRater(employees);
        // Add required entity
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            performanceCycleEmployeeRating = PerformanceCycleEmployeeRatingResourceIT.createUpdatedEntity(em);
            em.persist(performanceCycleEmployeeRating);
            em.flush();
        } else {
            performanceCycleEmployeeRating = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        userRatingsRemarks.setPcEmployeeRating(performanceCycleEmployeeRating);
        return userRatingsRemarks;
    }

    @BeforeEach
    public void initTest() {
        userRatingsRemarks = createEntity(em);
    }

    @Test
    @Transactional
    void createUserRatingsRemarks() throws Exception {
        int databaseSizeBeforeCreate = userRatingsRemarksRepository.findAll().size();
        // Create the UserRatingsRemarks
        restUserRatingsRemarksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isCreated());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeCreate + 1);
        UserRatingsRemarks testUserRatingsRemarks = userRatingsRemarksList.get(userRatingsRemarksList.size() - 1);
        assertThat(testUserRatingsRemarks.getIsPromotion()).isEqualTo(DEFAULT_IS_PROMOTION);
        assertThat(testUserRatingsRemarks.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testUserRatingsRemarks.getAreaOfImprovement()).isEqualTo(DEFAULT_AREA_OF_IMPROVEMENT);
        assertThat(testUserRatingsRemarks.getPromotionJustification()).isEqualTo(DEFAULT_PROMOTION_JUSTIFICATION);
        assertThat(testUserRatingsRemarks.getNewGrade()).isEqualTo(DEFAULT_NEW_GRADE);
        assertThat(testUserRatingsRemarks.getIsRedesignation()).isEqualTo(DEFAULT_IS_REDESIGNATION);
        assertThat(testUserRatingsRemarks.getRecommendedSalary()).isEqualTo(DEFAULT_RECOMMENDED_SALARY);
        assertThat(testUserRatingsRemarks.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserRatingsRemarks.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserRatingsRemarks.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserRatingsRemarks.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testUserRatingsRemarks.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUserRatingsRemarks.getOtherComments()).isEqualTo(DEFAULT_OTHER_COMMENTS);
    }

    @Test
    @Transactional
    void createUserRatingsRemarksWithExistingId() throws Exception {
        // Create the UserRatingsRemarks with an existing ID
        userRatingsRemarks.setId(1L);

        int databaseSizeBeforeCreate = userRatingsRemarksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRatingsRemarksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRatingsRemarksRepository.findAll().size();
        // set the field null
        userRatingsRemarks.setCreatedAt(null);

        // Create the UserRatingsRemarks, which fails.

        restUserRatingsRemarksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isBadRequest());

        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRatingsRemarksRepository.findAll().size();
        // set the field null
        userRatingsRemarks.setUpdatedAt(null);

        // Create the UserRatingsRemarks, which fails.

        restUserRatingsRemarksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isBadRequest());

        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRatingsRemarksRepository.findAll().size();
        // set the field null
        userRatingsRemarks.setVersion(null);

        // Create the UserRatingsRemarks, which fails.

        restUserRatingsRemarksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isBadRequest());

        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarks() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList
        restUserRatingsRemarksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRatingsRemarks.getId().intValue())))
            .andExpect(jsonPath("$.[*].isPromotion").value(hasItem(DEFAULT_IS_PROMOTION.booleanValue())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].areaOfImprovement").value(hasItem(DEFAULT_AREA_OF_IMPROVEMENT)))
            .andExpect(jsonPath("$.[*].promotionJustification").value(hasItem(DEFAULT_PROMOTION_JUSTIFICATION)))
            .andExpect(jsonPath("$.[*].newGrade").value(hasItem(DEFAULT_NEW_GRADE)))
            .andExpect(jsonPath("$.[*].isRedesignation").value(hasItem(DEFAULT_IS_REDESIGNATION.booleanValue())))
            .andExpect(jsonPath("$.[*].recommendedSalary").value(hasItem(DEFAULT_RECOMMENDED_SALARY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].otherComments").value(hasItem(DEFAULT_OTHER_COMMENTS)));
    }

    @Test
    @Transactional
    void getUserRatingsRemarks() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get the userRatingsRemarks
        restUserRatingsRemarksMockMvc
            .perform(get(ENTITY_API_URL_ID, userRatingsRemarks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRatingsRemarks.getId().intValue()))
            .andExpect(jsonPath("$.isPromotion").value(DEFAULT_IS_PROMOTION.booleanValue()))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH))
            .andExpect(jsonPath("$.areaOfImprovement").value(DEFAULT_AREA_OF_IMPROVEMENT))
            .andExpect(jsonPath("$.promotionJustification").value(DEFAULT_PROMOTION_JUSTIFICATION))
            .andExpect(jsonPath("$.newGrade").value(DEFAULT_NEW_GRADE))
            .andExpect(jsonPath("$.isRedesignation").value(DEFAULT_IS_REDESIGNATION.booleanValue()))
            .andExpect(jsonPath("$.recommendedSalary").value(DEFAULT_RECOMMENDED_SALARY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.otherComments").value(DEFAULT_OTHER_COMMENTS));
    }

    @Test
    @Transactional
    void getUserRatingsRemarksByIdFiltering() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        Long id = userRatingsRemarks.getId();

        defaultUserRatingsRemarksShouldBeFound("id.equals=" + id);
        defaultUserRatingsRemarksShouldNotBeFound("id.notEquals=" + id);

        defaultUserRatingsRemarksShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserRatingsRemarksShouldNotBeFound("id.greaterThan=" + id);

        defaultUserRatingsRemarksShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserRatingsRemarksShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByIsPromotionIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where isPromotion equals to DEFAULT_IS_PROMOTION
        defaultUserRatingsRemarksShouldBeFound("isPromotion.equals=" + DEFAULT_IS_PROMOTION);

        // Get all the userRatingsRemarksList where isPromotion equals to UPDATED_IS_PROMOTION
        defaultUserRatingsRemarksShouldNotBeFound("isPromotion.equals=" + UPDATED_IS_PROMOTION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByIsPromotionIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where isPromotion in DEFAULT_IS_PROMOTION or UPDATED_IS_PROMOTION
        defaultUserRatingsRemarksShouldBeFound("isPromotion.in=" + DEFAULT_IS_PROMOTION + "," + UPDATED_IS_PROMOTION);

        // Get all the userRatingsRemarksList where isPromotion equals to UPDATED_IS_PROMOTION
        defaultUserRatingsRemarksShouldNotBeFound("isPromotion.in=" + UPDATED_IS_PROMOTION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByIsPromotionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where isPromotion is not null
        defaultUserRatingsRemarksShouldBeFound("isPromotion.specified=true");

        // Get all the userRatingsRemarksList where isPromotion is null
        defaultUserRatingsRemarksShouldNotBeFound("isPromotion.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStrengthIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where strength equals to DEFAULT_STRENGTH
        defaultUserRatingsRemarksShouldBeFound("strength.equals=" + DEFAULT_STRENGTH);

        // Get all the userRatingsRemarksList where strength equals to UPDATED_STRENGTH
        defaultUserRatingsRemarksShouldNotBeFound("strength.equals=" + UPDATED_STRENGTH);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStrengthIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where strength in DEFAULT_STRENGTH or UPDATED_STRENGTH
        defaultUserRatingsRemarksShouldBeFound("strength.in=" + DEFAULT_STRENGTH + "," + UPDATED_STRENGTH);

        // Get all the userRatingsRemarksList where strength equals to UPDATED_STRENGTH
        defaultUserRatingsRemarksShouldNotBeFound("strength.in=" + UPDATED_STRENGTH);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStrengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where strength is not null
        defaultUserRatingsRemarksShouldBeFound("strength.specified=true");

        // Get all the userRatingsRemarksList where strength is null
        defaultUserRatingsRemarksShouldNotBeFound("strength.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStrengthContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where strength contains DEFAULT_STRENGTH
        defaultUserRatingsRemarksShouldBeFound("strength.contains=" + DEFAULT_STRENGTH);

        // Get all the userRatingsRemarksList where strength contains UPDATED_STRENGTH
        defaultUserRatingsRemarksShouldNotBeFound("strength.contains=" + UPDATED_STRENGTH);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStrengthNotContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where strength does not contain DEFAULT_STRENGTH
        defaultUserRatingsRemarksShouldNotBeFound("strength.doesNotContain=" + DEFAULT_STRENGTH);

        // Get all the userRatingsRemarksList where strength does not contain UPDATED_STRENGTH
        defaultUserRatingsRemarksShouldBeFound("strength.doesNotContain=" + UPDATED_STRENGTH);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByAreaOfImprovementIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where areaOfImprovement equals to DEFAULT_AREA_OF_IMPROVEMENT
        defaultUserRatingsRemarksShouldBeFound("areaOfImprovement.equals=" + DEFAULT_AREA_OF_IMPROVEMENT);

        // Get all the userRatingsRemarksList where areaOfImprovement equals to UPDATED_AREA_OF_IMPROVEMENT
        defaultUserRatingsRemarksShouldNotBeFound("areaOfImprovement.equals=" + UPDATED_AREA_OF_IMPROVEMENT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByAreaOfImprovementIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where areaOfImprovement in DEFAULT_AREA_OF_IMPROVEMENT or UPDATED_AREA_OF_IMPROVEMENT
        defaultUserRatingsRemarksShouldBeFound("areaOfImprovement.in=" + DEFAULT_AREA_OF_IMPROVEMENT + "," + UPDATED_AREA_OF_IMPROVEMENT);

        // Get all the userRatingsRemarksList where areaOfImprovement equals to UPDATED_AREA_OF_IMPROVEMENT
        defaultUserRatingsRemarksShouldNotBeFound("areaOfImprovement.in=" + UPDATED_AREA_OF_IMPROVEMENT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByAreaOfImprovementIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where areaOfImprovement is not null
        defaultUserRatingsRemarksShouldBeFound("areaOfImprovement.specified=true");

        // Get all the userRatingsRemarksList where areaOfImprovement is null
        defaultUserRatingsRemarksShouldNotBeFound("areaOfImprovement.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByAreaOfImprovementContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where areaOfImprovement contains DEFAULT_AREA_OF_IMPROVEMENT
        defaultUserRatingsRemarksShouldBeFound("areaOfImprovement.contains=" + DEFAULT_AREA_OF_IMPROVEMENT);

        // Get all the userRatingsRemarksList where areaOfImprovement contains UPDATED_AREA_OF_IMPROVEMENT
        defaultUserRatingsRemarksShouldNotBeFound("areaOfImprovement.contains=" + UPDATED_AREA_OF_IMPROVEMENT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByAreaOfImprovementNotContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where areaOfImprovement does not contain DEFAULT_AREA_OF_IMPROVEMENT
        defaultUserRatingsRemarksShouldNotBeFound("areaOfImprovement.doesNotContain=" + DEFAULT_AREA_OF_IMPROVEMENT);

        // Get all the userRatingsRemarksList where areaOfImprovement does not contain UPDATED_AREA_OF_IMPROVEMENT
        defaultUserRatingsRemarksShouldBeFound("areaOfImprovement.doesNotContain=" + UPDATED_AREA_OF_IMPROVEMENT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByPromotionJustificationIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where promotionJustification equals to DEFAULT_PROMOTION_JUSTIFICATION
        defaultUserRatingsRemarksShouldBeFound("promotionJustification.equals=" + DEFAULT_PROMOTION_JUSTIFICATION);

        // Get all the userRatingsRemarksList where promotionJustification equals to UPDATED_PROMOTION_JUSTIFICATION
        defaultUserRatingsRemarksShouldNotBeFound("promotionJustification.equals=" + UPDATED_PROMOTION_JUSTIFICATION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByPromotionJustificationIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where promotionJustification in DEFAULT_PROMOTION_JUSTIFICATION or UPDATED_PROMOTION_JUSTIFICATION
        defaultUserRatingsRemarksShouldBeFound(
            "promotionJustification.in=" + DEFAULT_PROMOTION_JUSTIFICATION + "," + UPDATED_PROMOTION_JUSTIFICATION
        );

        // Get all the userRatingsRemarksList where promotionJustification equals to UPDATED_PROMOTION_JUSTIFICATION
        defaultUserRatingsRemarksShouldNotBeFound("promotionJustification.in=" + UPDATED_PROMOTION_JUSTIFICATION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByPromotionJustificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where promotionJustification is not null
        defaultUserRatingsRemarksShouldBeFound("promotionJustification.specified=true");

        // Get all the userRatingsRemarksList where promotionJustification is null
        defaultUserRatingsRemarksShouldNotBeFound("promotionJustification.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByPromotionJustificationContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where promotionJustification contains DEFAULT_PROMOTION_JUSTIFICATION
        defaultUserRatingsRemarksShouldBeFound("promotionJustification.contains=" + DEFAULT_PROMOTION_JUSTIFICATION);

        // Get all the userRatingsRemarksList where promotionJustification contains UPDATED_PROMOTION_JUSTIFICATION
        defaultUserRatingsRemarksShouldNotBeFound("promotionJustification.contains=" + UPDATED_PROMOTION_JUSTIFICATION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByPromotionJustificationNotContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where promotionJustification does not contain DEFAULT_PROMOTION_JUSTIFICATION
        defaultUserRatingsRemarksShouldNotBeFound("promotionJustification.doesNotContain=" + DEFAULT_PROMOTION_JUSTIFICATION);

        // Get all the userRatingsRemarksList where promotionJustification does not contain UPDATED_PROMOTION_JUSTIFICATION
        defaultUserRatingsRemarksShouldBeFound("promotionJustification.doesNotContain=" + UPDATED_PROMOTION_JUSTIFICATION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByNewGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where newGrade equals to DEFAULT_NEW_GRADE
        defaultUserRatingsRemarksShouldBeFound("newGrade.equals=" + DEFAULT_NEW_GRADE);

        // Get all the userRatingsRemarksList where newGrade equals to UPDATED_NEW_GRADE
        defaultUserRatingsRemarksShouldNotBeFound("newGrade.equals=" + UPDATED_NEW_GRADE);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByNewGradeIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where newGrade in DEFAULT_NEW_GRADE or UPDATED_NEW_GRADE
        defaultUserRatingsRemarksShouldBeFound("newGrade.in=" + DEFAULT_NEW_GRADE + "," + UPDATED_NEW_GRADE);

        // Get all the userRatingsRemarksList where newGrade equals to UPDATED_NEW_GRADE
        defaultUserRatingsRemarksShouldNotBeFound("newGrade.in=" + UPDATED_NEW_GRADE);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByNewGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where newGrade is not null
        defaultUserRatingsRemarksShouldBeFound("newGrade.specified=true");

        // Get all the userRatingsRemarksList where newGrade is null
        defaultUserRatingsRemarksShouldNotBeFound("newGrade.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByNewGradeContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where newGrade contains DEFAULT_NEW_GRADE
        defaultUserRatingsRemarksShouldBeFound("newGrade.contains=" + DEFAULT_NEW_GRADE);

        // Get all the userRatingsRemarksList where newGrade contains UPDATED_NEW_GRADE
        defaultUserRatingsRemarksShouldNotBeFound("newGrade.contains=" + UPDATED_NEW_GRADE);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByNewGradeNotContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where newGrade does not contain DEFAULT_NEW_GRADE
        defaultUserRatingsRemarksShouldNotBeFound("newGrade.doesNotContain=" + DEFAULT_NEW_GRADE);

        // Get all the userRatingsRemarksList where newGrade does not contain UPDATED_NEW_GRADE
        defaultUserRatingsRemarksShouldBeFound("newGrade.doesNotContain=" + UPDATED_NEW_GRADE);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByIsRedesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where isRedesignation equals to DEFAULT_IS_REDESIGNATION
        defaultUserRatingsRemarksShouldBeFound("isRedesignation.equals=" + DEFAULT_IS_REDESIGNATION);

        // Get all the userRatingsRemarksList where isRedesignation equals to UPDATED_IS_REDESIGNATION
        defaultUserRatingsRemarksShouldNotBeFound("isRedesignation.equals=" + UPDATED_IS_REDESIGNATION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByIsRedesignationIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where isRedesignation in DEFAULT_IS_REDESIGNATION or UPDATED_IS_REDESIGNATION
        defaultUserRatingsRemarksShouldBeFound("isRedesignation.in=" + DEFAULT_IS_REDESIGNATION + "," + UPDATED_IS_REDESIGNATION);

        // Get all the userRatingsRemarksList where isRedesignation equals to UPDATED_IS_REDESIGNATION
        defaultUserRatingsRemarksShouldNotBeFound("isRedesignation.in=" + UPDATED_IS_REDESIGNATION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByIsRedesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where isRedesignation is not null
        defaultUserRatingsRemarksShouldBeFound("isRedesignation.specified=true");

        // Get all the userRatingsRemarksList where isRedesignation is null
        defaultUserRatingsRemarksShouldNotBeFound("isRedesignation.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByRecommendedSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where recommendedSalary equals to DEFAULT_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldBeFound("recommendedSalary.equals=" + DEFAULT_RECOMMENDED_SALARY);

        // Get all the userRatingsRemarksList where recommendedSalary equals to UPDATED_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldNotBeFound("recommendedSalary.equals=" + UPDATED_RECOMMENDED_SALARY);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByRecommendedSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where recommendedSalary in DEFAULT_RECOMMENDED_SALARY or UPDATED_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldBeFound("recommendedSalary.in=" + DEFAULT_RECOMMENDED_SALARY + "," + UPDATED_RECOMMENDED_SALARY);

        // Get all the userRatingsRemarksList where recommendedSalary equals to UPDATED_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldNotBeFound("recommendedSalary.in=" + UPDATED_RECOMMENDED_SALARY);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByRecommendedSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where recommendedSalary is not null
        defaultUserRatingsRemarksShouldBeFound("recommendedSalary.specified=true");

        // Get all the userRatingsRemarksList where recommendedSalary is null
        defaultUserRatingsRemarksShouldNotBeFound("recommendedSalary.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByRecommendedSalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where recommendedSalary is greater than or equal to DEFAULT_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldBeFound("recommendedSalary.greaterThanOrEqual=" + DEFAULT_RECOMMENDED_SALARY);

        // Get all the userRatingsRemarksList where recommendedSalary is greater than or equal to UPDATED_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldNotBeFound("recommendedSalary.greaterThanOrEqual=" + UPDATED_RECOMMENDED_SALARY);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByRecommendedSalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where recommendedSalary is less than or equal to DEFAULT_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldBeFound("recommendedSalary.lessThanOrEqual=" + DEFAULT_RECOMMENDED_SALARY);

        // Get all the userRatingsRemarksList where recommendedSalary is less than or equal to SMALLER_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldNotBeFound("recommendedSalary.lessThanOrEqual=" + SMALLER_RECOMMENDED_SALARY);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByRecommendedSalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where recommendedSalary is less than DEFAULT_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldNotBeFound("recommendedSalary.lessThan=" + DEFAULT_RECOMMENDED_SALARY);

        // Get all the userRatingsRemarksList where recommendedSalary is less than UPDATED_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldBeFound("recommendedSalary.lessThan=" + UPDATED_RECOMMENDED_SALARY);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByRecommendedSalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where recommendedSalary is greater than DEFAULT_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldNotBeFound("recommendedSalary.greaterThan=" + DEFAULT_RECOMMENDED_SALARY);

        // Get all the userRatingsRemarksList where recommendedSalary is greater than SMALLER_RECOMMENDED_SALARY
        defaultUserRatingsRemarksShouldBeFound("recommendedSalary.greaterThan=" + SMALLER_RECOMMENDED_SALARY);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where status equals to DEFAULT_STATUS
        defaultUserRatingsRemarksShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the userRatingsRemarksList where status equals to UPDATED_STATUS
        defaultUserRatingsRemarksShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultUserRatingsRemarksShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the userRatingsRemarksList where status equals to UPDATED_STATUS
        defaultUserRatingsRemarksShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where status is not null
        defaultUserRatingsRemarksShouldBeFound("status.specified=true");

        // Get all the userRatingsRemarksList where status is null
        defaultUserRatingsRemarksShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStatusContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where status contains DEFAULT_STATUS
        defaultUserRatingsRemarksShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the userRatingsRemarksList where status contains UPDATED_STATUS
        defaultUserRatingsRemarksShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where status does not contain DEFAULT_STATUS
        defaultUserRatingsRemarksShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the userRatingsRemarksList where status does not contain UPDATED_STATUS
        defaultUserRatingsRemarksShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserRatingsRemarksShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userRatingsRemarksList where createdAt equals to UPDATED_CREATED_AT
        defaultUserRatingsRemarksShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserRatingsRemarksShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userRatingsRemarksList where createdAt equals to UPDATED_CREATED_AT
        defaultUserRatingsRemarksShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where createdAt is not null
        defaultUserRatingsRemarksShouldBeFound("createdAt.specified=true");

        // Get all the userRatingsRemarksList where createdAt is null
        defaultUserRatingsRemarksShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserRatingsRemarksShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userRatingsRemarksList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserRatingsRemarksShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserRatingsRemarksShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userRatingsRemarksList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserRatingsRemarksShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where updatedAt is not null
        defaultUserRatingsRemarksShouldBeFound("updatedAt.specified=true");

        // Get all the userRatingsRemarksList where updatedAt is null
        defaultUserRatingsRemarksShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where deletedAt equals to DEFAULT_DELETED_AT
        defaultUserRatingsRemarksShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the userRatingsRemarksList where deletedAt equals to UPDATED_DELETED_AT
        defaultUserRatingsRemarksShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultUserRatingsRemarksShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the userRatingsRemarksList where deletedAt equals to UPDATED_DELETED_AT
        defaultUserRatingsRemarksShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where deletedAt is not null
        defaultUserRatingsRemarksShouldBeFound("deletedAt.specified=true");

        // Get all the userRatingsRemarksList where deletedAt is null
        defaultUserRatingsRemarksShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where version equals to DEFAULT_VERSION
        defaultUserRatingsRemarksShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userRatingsRemarksList where version equals to UPDATED_VERSION
        defaultUserRatingsRemarksShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserRatingsRemarksShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userRatingsRemarksList where version equals to UPDATED_VERSION
        defaultUserRatingsRemarksShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where version is not null
        defaultUserRatingsRemarksShouldBeFound("version.specified=true");

        // Get all the userRatingsRemarksList where version is null
        defaultUserRatingsRemarksShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where version is greater than or equal to DEFAULT_VERSION
        defaultUserRatingsRemarksShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userRatingsRemarksList where version is greater than or equal to UPDATED_VERSION
        defaultUserRatingsRemarksShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where version is less than or equal to DEFAULT_VERSION
        defaultUserRatingsRemarksShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userRatingsRemarksList where version is less than or equal to SMALLER_VERSION
        defaultUserRatingsRemarksShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where version is less than DEFAULT_VERSION
        defaultUserRatingsRemarksShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userRatingsRemarksList where version is less than UPDATED_VERSION
        defaultUserRatingsRemarksShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where version is greater than DEFAULT_VERSION
        defaultUserRatingsRemarksShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userRatingsRemarksList where version is greater than SMALLER_VERSION
        defaultUserRatingsRemarksShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByOtherCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where otherComments equals to DEFAULT_OTHER_COMMENTS
        defaultUserRatingsRemarksShouldBeFound("otherComments.equals=" + DEFAULT_OTHER_COMMENTS);

        // Get all the userRatingsRemarksList where otherComments equals to UPDATED_OTHER_COMMENTS
        defaultUserRatingsRemarksShouldNotBeFound("otherComments.equals=" + UPDATED_OTHER_COMMENTS);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByOtherCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where otherComments in DEFAULT_OTHER_COMMENTS or UPDATED_OTHER_COMMENTS
        defaultUserRatingsRemarksShouldBeFound("otherComments.in=" + DEFAULT_OTHER_COMMENTS + "," + UPDATED_OTHER_COMMENTS);

        // Get all the userRatingsRemarksList where otherComments equals to UPDATED_OTHER_COMMENTS
        defaultUserRatingsRemarksShouldNotBeFound("otherComments.in=" + UPDATED_OTHER_COMMENTS);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByOtherCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where otherComments is not null
        defaultUserRatingsRemarksShouldBeFound("otherComments.specified=true");

        // Get all the userRatingsRemarksList where otherComments is null
        defaultUserRatingsRemarksShouldNotBeFound("otherComments.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByOtherCommentsContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where otherComments contains DEFAULT_OTHER_COMMENTS
        defaultUserRatingsRemarksShouldBeFound("otherComments.contains=" + DEFAULT_OTHER_COMMENTS);

        // Get all the userRatingsRemarksList where otherComments contains UPDATED_OTHER_COMMENTS
        defaultUserRatingsRemarksShouldNotBeFound("otherComments.contains=" + UPDATED_OTHER_COMMENTS);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByOtherCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        // Get all the userRatingsRemarksList where otherComments does not contain DEFAULT_OTHER_COMMENTS
        defaultUserRatingsRemarksShouldNotBeFound("otherComments.doesNotContain=" + DEFAULT_OTHER_COMMENTS);

        // Get all the userRatingsRemarksList where otherComments does not contain UPDATED_OTHER_COMMENTS
        defaultUserRatingsRemarksShouldBeFound("otherComments.doesNotContain=" + UPDATED_OTHER_COMMENTS);
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByDesignationAfterPromotionIsEqualToSomething() throws Exception {
        Designations designationAfterPromotion;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);
            designationAfterPromotion = DesignationsResourceIT.createEntity(em);
        } else {
            designationAfterPromotion = TestUtil.findAll(em, Designations.class).get(0);
        }
        em.persist(designationAfterPromotion);
        em.flush();
        userRatingsRemarks.setDesignationAfterPromotion(designationAfterPromotion);
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);
        Long designationAfterPromotionId = designationAfterPromotion.getId();

        // Get all the userRatingsRemarksList where designationAfterPromotion equals to designationAfterPromotionId
        defaultUserRatingsRemarksShouldBeFound("designationAfterPromotionId.equals=" + designationAfterPromotionId);

        // Get all the userRatingsRemarksList where designationAfterPromotion equals to (designationAfterPromotionId + 1)
        defaultUserRatingsRemarksShouldNotBeFound("designationAfterPromotionId.equals=" + (designationAfterPromotionId + 1));
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByDesignationAfterRedesignationIsEqualToSomething() throws Exception {
        Designations designationAfterRedesignation;
        if (TestUtil.findAll(em, Designations.class).isEmpty()) {
            userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);
            designationAfterRedesignation = DesignationsResourceIT.createEntity(em);
        } else {
            designationAfterRedesignation = TestUtil.findAll(em, Designations.class).get(0);
        }
        em.persist(designationAfterRedesignation);
        em.flush();
        userRatingsRemarks.setDesignationAfterRedesignation(designationAfterRedesignation);
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);
        Long designationAfterRedesignationId = designationAfterRedesignation.getId();

        // Get all the userRatingsRemarksList where designationAfterRedesignation equals to designationAfterRedesignationId
        defaultUserRatingsRemarksShouldBeFound("designationAfterRedesignationId.equals=" + designationAfterRedesignationId);

        // Get all the userRatingsRemarksList where designationAfterRedesignation equals to (designationAfterRedesignationId + 1)
        defaultUserRatingsRemarksShouldNotBeFound("designationAfterRedesignationId.equals=" + (designationAfterRedesignationId + 1));
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByRaterIsEqualToSomething() throws Exception {
        Employees rater;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);
            rater = EmployeesResourceIT.createEntity(em);
        } else {
            rater = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(rater);
        em.flush();
        userRatingsRemarks.setRater(rater);
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);
        Long raterId = rater.getId();

        // Get all the userRatingsRemarksList where rater equals to raterId
        defaultUserRatingsRemarksShouldBeFound("raterId.equals=" + raterId);

        // Get all the userRatingsRemarksList where rater equals to (raterId + 1)
        defaultUserRatingsRemarksShouldNotBeFound("raterId.equals=" + (raterId + 1));
    }

    @Test
    @Transactional
    void getAllUserRatingsRemarksByPcEmployeeRatingIsEqualToSomething() throws Exception {
        PerformanceCycleEmployeeRating pcEmployeeRating;
        if (TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).isEmpty()) {
            userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);
            pcEmployeeRating = PerformanceCycleEmployeeRatingResourceIT.createEntity(em);
        } else {
            pcEmployeeRating = TestUtil.findAll(em, PerformanceCycleEmployeeRating.class).get(0);
        }
        em.persist(pcEmployeeRating);
        em.flush();
        userRatingsRemarks.setPcEmployeeRating(pcEmployeeRating);
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);
        Long pcEmployeeRatingId = pcEmployeeRating.getId();

        // Get all the userRatingsRemarksList where pcEmployeeRating equals to pcEmployeeRatingId
        defaultUserRatingsRemarksShouldBeFound("pcEmployeeRatingId.equals=" + pcEmployeeRatingId);

        // Get all the userRatingsRemarksList where pcEmployeeRating equals to (pcEmployeeRatingId + 1)
        defaultUserRatingsRemarksShouldNotBeFound("pcEmployeeRatingId.equals=" + (pcEmployeeRatingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserRatingsRemarksShouldBeFound(String filter) throws Exception {
        restUserRatingsRemarksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRatingsRemarks.getId().intValue())))
            .andExpect(jsonPath("$.[*].isPromotion").value(hasItem(DEFAULT_IS_PROMOTION.booleanValue())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].areaOfImprovement").value(hasItem(DEFAULT_AREA_OF_IMPROVEMENT)))
            .andExpect(jsonPath("$.[*].promotionJustification").value(hasItem(DEFAULT_PROMOTION_JUSTIFICATION)))
            .andExpect(jsonPath("$.[*].newGrade").value(hasItem(DEFAULT_NEW_GRADE)))
            .andExpect(jsonPath("$.[*].isRedesignation").value(hasItem(DEFAULT_IS_REDESIGNATION.booleanValue())))
            .andExpect(jsonPath("$.[*].recommendedSalary").value(hasItem(DEFAULT_RECOMMENDED_SALARY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].otherComments").value(hasItem(DEFAULT_OTHER_COMMENTS)));

        // Check, that the count call also returns 1
        restUserRatingsRemarksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserRatingsRemarksShouldNotBeFound(String filter) throws Exception {
        restUserRatingsRemarksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserRatingsRemarksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserRatingsRemarks() throws Exception {
        // Get the userRatingsRemarks
        restUserRatingsRemarksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserRatingsRemarks() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();

        // Update the userRatingsRemarks
        UserRatingsRemarks updatedUserRatingsRemarks = userRatingsRemarksRepository.findById(userRatingsRemarks.getId()).get();
        // Disconnect from session so that the updates on updatedUserRatingsRemarks are not directly saved in db
        em.detach(updatedUserRatingsRemarks);
        updatedUserRatingsRemarks
            .isPromotion(UPDATED_IS_PROMOTION)
            .strength(UPDATED_STRENGTH)
            .areaOfImprovement(UPDATED_AREA_OF_IMPROVEMENT)
            .promotionJustification(UPDATED_PROMOTION_JUSTIFICATION)
            .newGrade(UPDATED_NEW_GRADE)
            .isRedesignation(UPDATED_IS_REDESIGNATION)
            .recommendedSalary(UPDATED_RECOMMENDED_SALARY)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION)
            .otherComments(UPDATED_OTHER_COMMENTS);

        restUserRatingsRemarksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserRatingsRemarks.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserRatingsRemarks))
            )
            .andExpect(status().isOk());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
        UserRatingsRemarks testUserRatingsRemarks = userRatingsRemarksList.get(userRatingsRemarksList.size() - 1);
        assertThat(testUserRatingsRemarks.getIsPromotion()).isEqualTo(UPDATED_IS_PROMOTION);
        assertThat(testUserRatingsRemarks.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testUserRatingsRemarks.getAreaOfImprovement()).isEqualTo(UPDATED_AREA_OF_IMPROVEMENT);
        assertThat(testUserRatingsRemarks.getPromotionJustification()).isEqualTo(UPDATED_PROMOTION_JUSTIFICATION);
        assertThat(testUserRatingsRemarks.getNewGrade()).isEqualTo(UPDATED_NEW_GRADE);
        assertThat(testUserRatingsRemarks.getIsRedesignation()).isEqualTo(UPDATED_IS_REDESIGNATION);
        assertThat(testUserRatingsRemarks.getRecommendedSalary()).isEqualTo(UPDATED_RECOMMENDED_SALARY);
        assertThat(testUserRatingsRemarks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserRatingsRemarks.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRatingsRemarks.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRatingsRemarks.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserRatingsRemarks.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUserRatingsRemarks.getOtherComments()).isEqualTo(UPDATED_OTHER_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingUserRatingsRemarks() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();
        userRatingsRemarks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRatingsRemarksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userRatingsRemarks.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserRatingsRemarks() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();
        userRatingsRemarks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRatingsRemarksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserRatingsRemarks() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();
        userRatingsRemarks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRatingsRemarksMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserRatingsRemarksWithPatch() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();

        // Update the userRatingsRemarks using partial update
        UserRatingsRemarks partialUpdatedUserRatingsRemarks = new UserRatingsRemarks();
        partialUpdatedUserRatingsRemarks.setId(userRatingsRemarks.getId());

        partialUpdatedUserRatingsRemarks
            .strength(UPDATED_STRENGTH)
            .areaOfImprovement(UPDATED_AREA_OF_IMPROVEMENT)
            .newGrade(UPDATED_NEW_GRADE)
            .isRedesignation(UPDATED_IS_REDESIGNATION)
            .status(UPDATED_STATUS)
            .otherComments(UPDATED_OTHER_COMMENTS);

        restUserRatingsRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRatingsRemarks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRatingsRemarks))
            )
            .andExpect(status().isOk());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
        UserRatingsRemarks testUserRatingsRemarks = userRatingsRemarksList.get(userRatingsRemarksList.size() - 1);
        assertThat(testUserRatingsRemarks.getIsPromotion()).isEqualTo(DEFAULT_IS_PROMOTION);
        assertThat(testUserRatingsRemarks.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testUserRatingsRemarks.getAreaOfImprovement()).isEqualTo(UPDATED_AREA_OF_IMPROVEMENT);
        assertThat(testUserRatingsRemarks.getPromotionJustification()).isEqualTo(DEFAULT_PROMOTION_JUSTIFICATION);
        assertThat(testUserRatingsRemarks.getNewGrade()).isEqualTo(UPDATED_NEW_GRADE);
        assertThat(testUserRatingsRemarks.getIsRedesignation()).isEqualTo(UPDATED_IS_REDESIGNATION);
        assertThat(testUserRatingsRemarks.getRecommendedSalary()).isEqualTo(DEFAULT_RECOMMENDED_SALARY);
        assertThat(testUserRatingsRemarks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserRatingsRemarks.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserRatingsRemarks.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserRatingsRemarks.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testUserRatingsRemarks.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUserRatingsRemarks.getOtherComments()).isEqualTo(UPDATED_OTHER_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateUserRatingsRemarksWithPatch() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();

        // Update the userRatingsRemarks using partial update
        UserRatingsRemarks partialUpdatedUserRatingsRemarks = new UserRatingsRemarks();
        partialUpdatedUserRatingsRemarks.setId(userRatingsRemarks.getId());

        partialUpdatedUserRatingsRemarks
            .isPromotion(UPDATED_IS_PROMOTION)
            .strength(UPDATED_STRENGTH)
            .areaOfImprovement(UPDATED_AREA_OF_IMPROVEMENT)
            .promotionJustification(UPDATED_PROMOTION_JUSTIFICATION)
            .newGrade(UPDATED_NEW_GRADE)
            .isRedesignation(UPDATED_IS_REDESIGNATION)
            .recommendedSalary(UPDATED_RECOMMENDED_SALARY)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION)
            .otherComments(UPDATED_OTHER_COMMENTS);

        restUserRatingsRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRatingsRemarks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRatingsRemarks))
            )
            .andExpect(status().isOk());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
        UserRatingsRemarks testUserRatingsRemarks = userRatingsRemarksList.get(userRatingsRemarksList.size() - 1);
        assertThat(testUserRatingsRemarks.getIsPromotion()).isEqualTo(UPDATED_IS_PROMOTION);
        assertThat(testUserRatingsRemarks.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testUserRatingsRemarks.getAreaOfImprovement()).isEqualTo(UPDATED_AREA_OF_IMPROVEMENT);
        assertThat(testUserRatingsRemarks.getPromotionJustification()).isEqualTo(UPDATED_PROMOTION_JUSTIFICATION);
        assertThat(testUserRatingsRemarks.getNewGrade()).isEqualTo(UPDATED_NEW_GRADE);
        assertThat(testUserRatingsRemarks.getIsRedesignation()).isEqualTo(UPDATED_IS_REDESIGNATION);
        assertThat(testUserRatingsRemarks.getRecommendedSalary()).isEqualTo(UPDATED_RECOMMENDED_SALARY);
        assertThat(testUserRatingsRemarks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserRatingsRemarks.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserRatingsRemarks.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserRatingsRemarks.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testUserRatingsRemarks.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUserRatingsRemarks.getOtherComments()).isEqualTo(UPDATED_OTHER_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingUserRatingsRemarks() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();
        userRatingsRemarks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRatingsRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userRatingsRemarks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserRatingsRemarks() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();
        userRatingsRemarks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRatingsRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserRatingsRemarks() throws Exception {
        int databaseSizeBeforeUpdate = userRatingsRemarksRepository.findAll().size();
        userRatingsRemarks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRatingsRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRatingsRemarks))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRatingsRemarks in the database
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserRatingsRemarks() throws Exception {
        // Initialize the database
        userRatingsRemarksRepository.saveAndFlush(userRatingsRemarks);

        int databaseSizeBeforeDelete = userRatingsRemarksRepository.findAll().size();

        // Delete the userRatingsRemarks
        restUserRatingsRemarksMockMvc
            .perform(delete(ENTITY_API_URL_ID, userRatingsRemarks.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRatingsRemarks> userRatingsRemarksList = userRatingsRemarksRepository.findAll();
        assertThat(userRatingsRemarksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
