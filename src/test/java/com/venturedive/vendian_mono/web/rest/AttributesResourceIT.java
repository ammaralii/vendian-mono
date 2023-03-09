package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.LeaveApprovers;
import com.venturedive.vendian_mono.domain.LeaveEscalationApprovers;
import com.venturedive.vendian_mono.domain.RaterAttributeMappings;
import com.venturedive.vendian_mono.domain.RatingAttributeMappings;
import com.venturedive.vendian_mono.domain.UserAttributeApprovalRules;
import com.venturedive.vendian_mono.domain.UserAttributeEscalationRules;
import com.venturedive.vendian_mono.domain.UserAttributes;
import com.venturedive.vendian_mono.domain.UserRelationApprovalRules;
import com.venturedive.vendian_mono.domain.UserRelations;
import com.venturedive.vendian_mono.repository.AttributesRepository;
import com.venturedive.vendian_mono.service.criteria.AttributesCriteria;
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
 * Integration tests for the {@link AttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final Instant DEFAULT_EFF_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFF_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributesRepository attributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributesMockMvc;

    private Attributes attributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attributes createEntity(EntityManager em) {
        Attributes attributes = new Attributes()
            .name(DEFAULT_NAME)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION)
            .effDate(DEFAULT_EFF_DATE);
        return attributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attributes createUpdatedEntity(EntityManager em) {
        Attributes attributes = new Attributes()
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION)
            .effDate(UPDATED_EFF_DATE);
        return attributes;
    }

    @BeforeEach
    public void initTest() {
        attributes = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributes() throws Exception {
        int databaseSizeBeforeCreate = attributesRepository.findAll().size();
        // Create the Attributes
        restAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isCreated());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeCreate + 1);
        Attributes testAttributes = attributesList.get(attributesList.size() - 1);
        assertThat(testAttributes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAttributes.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAttributes.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testAttributes.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAttributes.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
    }

    @Test
    @Transactional
    void createAttributesWithExistingId() throws Exception {
        // Create the Attributes with an existing ID
        attributes.setId(1L);

        int databaseSizeBeforeCreate = attributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributesRepository.findAll().size();
        // set the field null
        attributes.setCreatedAt(null);

        // Create the Attributes, which fails.

        restAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isBadRequest());

        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributesRepository.findAll().size();
        // set the field null
        attributes.setUpdatedAt(null);

        // Create the Attributes, which fails.

        restAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isBadRequest());

        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttributes() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList
        restAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())));
    }

    @Test
    @Transactional
    void getAttributes() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get the attributes
        restAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, attributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()));
    }

    @Test
    @Transactional
    void getAttributesByIdFiltering() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        Long id = attributes.getId();

        defaultAttributesShouldBeFound("id.equals=" + id);
        defaultAttributesShouldNotBeFound("id.notEquals=" + id);

        defaultAttributesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttributesShouldNotBeFound("id.greaterThan=" + id);

        defaultAttributesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttributesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAttributesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where name equals to DEFAULT_NAME
        defaultAttributesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the attributesList where name equals to UPDATED_NAME
        defaultAttributesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAttributesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAttributesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the attributesList where name equals to UPDATED_NAME
        defaultAttributesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAttributesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where name is not null
        defaultAttributesShouldBeFound("name.specified=true");

        // Get all the attributesList where name is null
        defaultAttributesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributesByNameContainsSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where name contains DEFAULT_NAME
        defaultAttributesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the attributesList where name contains UPDATED_NAME
        defaultAttributesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAttributesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where name does not contain DEFAULT_NAME
        defaultAttributesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the attributesList where name does not contain UPDATED_NAME
        defaultAttributesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAttributesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where createdAt equals to DEFAULT_CREATED_AT
        defaultAttributesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the attributesList where createdAt equals to UPDATED_CREATED_AT
        defaultAttributesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllAttributesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultAttributesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the attributesList where createdAt equals to UPDATED_CREATED_AT
        defaultAttributesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllAttributesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where createdAt is not null
        defaultAttributesShouldBeFound("createdAt.specified=true");

        // Get all the attributesList where createdAt is null
        defaultAttributesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultAttributesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the attributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultAttributesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllAttributesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultAttributesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the attributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultAttributesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllAttributesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where updatedAt is not null
        defaultAttributesShouldBeFound("updatedAt.specified=true");

        // Get all the attributesList where updatedAt is null
        defaultAttributesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where endDate equals to DEFAULT_END_DATE
        defaultAttributesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the attributesList where endDate equals to UPDATED_END_DATE
        defaultAttributesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllAttributesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultAttributesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the attributesList where endDate equals to UPDATED_END_DATE
        defaultAttributesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllAttributesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where endDate is not null
        defaultAttributesShouldBeFound("endDate.specified=true");

        // Get all the attributesList where endDate is null
        defaultAttributesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where version equals to DEFAULT_VERSION
        defaultAttributesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the attributesList where version equals to UPDATED_VERSION
        defaultAttributesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllAttributesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultAttributesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the attributesList where version equals to UPDATED_VERSION
        defaultAttributesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllAttributesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where version is not null
        defaultAttributesShouldBeFound("version.specified=true");

        // Get all the attributesList where version is null
        defaultAttributesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where version is greater than or equal to DEFAULT_VERSION
        defaultAttributesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the attributesList where version is greater than or equal to UPDATED_VERSION
        defaultAttributesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllAttributesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where version is less than or equal to DEFAULT_VERSION
        defaultAttributesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the attributesList where version is less than or equal to SMALLER_VERSION
        defaultAttributesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllAttributesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where version is less than DEFAULT_VERSION
        defaultAttributesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the attributesList where version is less than UPDATED_VERSION
        defaultAttributesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllAttributesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where version is greater than DEFAULT_VERSION
        defaultAttributesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the attributesList where version is greater than SMALLER_VERSION
        defaultAttributesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllAttributesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where effDate equals to DEFAULT_EFF_DATE
        defaultAttributesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the attributesList where effDate equals to UPDATED_EFF_DATE
        defaultAttributesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllAttributesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultAttributesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the attributesList where effDate equals to UPDATED_EFF_DATE
        defaultAttributesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllAttributesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        // Get all the attributesList where effDate is not null
        defaultAttributesShouldBeFound("effDate.specified=true");

        // Get all the attributesList where effDate is null
        defaultAttributesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributesByLeaveapproversAttributeIsEqualToSomething() throws Exception {
        LeaveApprovers leaveapproversAttribute;
        if (TestUtil.findAll(em, LeaveApprovers.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            leaveapproversAttribute = LeaveApproversResourceIT.createEntity(em);
        } else {
            leaveapproversAttribute = TestUtil.findAll(em, LeaveApprovers.class).get(0);
        }
        em.persist(leaveapproversAttribute);
        em.flush();
        attributes.addLeaveapproversAttribute(leaveapproversAttribute);
        attributesRepository.saveAndFlush(attributes);
        Long leaveapproversAttributeId = leaveapproversAttribute.getId();

        // Get all the attributesList where leaveapproversAttribute equals to leaveapproversAttributeId
        defaultAttributesShouldBeFound("leaveapproversAttributeId.equals=" + leaveapproversAttributeId);

        // Get all the attributesList where leaveapproversAttribute equals to (leaveapproversAttributeId + 1)
        defaultAttributesShouldNotBeFound("leaveapproversAttributeId.equals=" + (leaveapproversAttributeId + 1));
    }

    @Test
    @Transactional
    void getAllAttributesByLeaveescalationapproversAttributeIsEqualToSomething() throws Exception {
        LeaveEscalationApprovers leaveescalationapproversAttribute;
        if (TestUtil.findAll(em, LeaveEscalationApprovers.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            leaveescalationapproversAttribute = LeaveEscalationApproversResourceIT.createEntity(em);
        } else {
            leaveescalationapproversAttribute = TestUtil.findAll(em, LeaveEscalationApprovers.class).get(0);
        }
        em.persist(leaveescalationapproversAttribute);
        em.flush();
        attributes.addLeaveescalationapproversAttribute(leaveescalationapproversAttribute);
        attributesRepository.saveAndFlush(attributes);
        Long leaveescalationapproversAttributeId = leaveescalationapproversAttribute.getId();

        // Get all the attributesList where leaveescalationapproversAttribute equals to leaveescalationapproversAttributeId
        defaultAttributesShouldBeFound("leaveescalationapproversAttributeId.equals=" + leaveescalationapproversAttributeId);

        // Get all the attributesList where leaveescalationapproversAttribute equals to (leaveescalationapproversAttributeId + 1)
        defaultAttributesShouldNotBeFound("leaveescalationapproversAttributeId.equals=" + (leaveescalationapproversAttributeId + 1));
    }

    @Test
    @Transactional
    void getAllAttributesByRaterattributemappingsAttributesforIsEqualToSomething() throws Exception {
        RaterAttributeMappings raterattributemappingsAttributesfor;
        if (TestUtil.findAll(em, RaterAttributeMappings.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            raterattributemappingsAttributesfor = RaterAttributeMappingsResourceIT.createEntity(em);
        } else {
            raterattributemappingsAttributesfor = TestUtil.findAll(em, RaterAttributeMappings.class).get(0);
        }
        em.persist(raterattributemappingsAttributesfor);
        em.flush();
        attributes.addRaterattributemappingsAttributesfor(raterattributemappingsAttributesfor);
        attributesRepository.saveAndFlush(attributes);
        Long raterattributemappingsAttributesforId = raterattributemappingsAttributesfor.getId();

        // Get all the attributesList where raterattributemappingsAttributesfor equals to raterattributemappingsAttributesforId
        defaultAttributesShouldBeFound("raterattributemappingsAttributesforId.equals=" + raterattributemappingsAttributesforId);

        // Get all the attributesList where raterattributemappingsAttributesfor equals to (raterattributemappingsAttributesforId + 1)
        defaultAttributesShouldNotBeFound("raterattributemappingsAttributesforId.equals=" + (raterattributemappingsAttributesforId + 1));
    }

    @Test
    @Transactional
    void getAllAttributesByRaterattributemappingsAttributesbyIsEqualToSomething() throws Exception {
        RaterAttributeMappings raterattributemappingsAttributesby;
        if (TestUtil.findAll(em, RaterAttributeMappings.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            raterattributemappingsAttributesby = RaterAttributeMappingsResourceIT.createEntity(em);
        } else {
            raterattributemappingsAttributesby = TestUtil.findAll(em, RaterAttributeMappings.class).get(0);
        }
        em.persist(raterattributemappingsAttributesby);
        em.flush();
        attributes.addRaterattributemappingsAttributesby(raterattributemappingsAttributesby);
        attributesRepository.saveAndFlush(attributes);
        Long raterattributemappingsAttributesbyId = raterattributemappingsAttributesby.getId();

        // Get all the attributesList where raterattributemappingsAttributesby equals to raterattributemappingsAttributesbyId
        defaultAttributesShouldBeFound("raterattributemappingsAttributesbyId.equals=" + raterattributemappingsAttributesbyId);

        // Get all the attributesList where raterattributemappingsAttributesby equals to (raterattributemappingsAttributesbyId + 1)
        defaultAttributesShouldNotBeFound("raterattributemappingsAttributesbyId.equals=" + (raterattributemappingsAttributesbyId + 1));
    }

    @Test
    @Transactional
    void getAllAttributesByRatingattributemappingsAttributeIsEqualToSomething() throws Exception {
        RatingAttributeMappings ratingattributemappingsAttribute;
        if (TestUtil.findAll(em, RatingAttributeMappings.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            ratingattributemappingsAttribute = RatingAttributeMappingsResourceIT.createEntity(em);
        } else {
            ratingattributemappingsAttribute = TestUtil.findAll(em, RatingAttributeMappings.class).get(0);
        }
        em.persist(ratingattributemappingsAttribute);
        em.flush();
        attributes.addRatingattributemappingsAttribute(ratingattributemappingsAttribute);
        attributesRepository.saveAndFlush(attributes);
        Long ratingattributemappingsAttributeId = ratingattributemappingsAttribute.getId();

        // Get all the attributesList where ratingattributemappingsAttribute equals to ratingattributemappingsAttributeId
        defaultAttributesShouldBeFound("ratingattributemappingsAttributeId.equals=" + ratingattributemappingsAttributeId);

        // Get all the attributesList where ratingattributemappingsAttribute equals to (ratingattributemappingsAttributeId + 1)
        defaultAttributesShouldNotBeFound("ratingattributemappingsAttributeId.equals=" + (ratingattributemappingsAttributeId + 1));
    }

    @Test
    @Transactional
    void getAllAttributesByUserattributeapprovalrulesAttributeIsEqualToSomething() throws Exception {
        UserAttributeApprovalRules userattributeapprovalrulesAttribute;
        if (TestUtil.findAll(em, UserAttributeApprovalRules.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            userattributeapprovalrulesAttribute = UserAttributeApprovalRulesResourceIT.createEntity(em);
        } else {
            userattributeapprovalrulesAttribute = TestUtil.findAll(em, UserAttributeApprovalRules.class).get(0);
        }
        em.persist(userattributeapprovalrulesAttribute);
        em.flush();
        attributes.addUserattributeapprovalrulesAttribute(userattributeapprovalrulesAttribute);
        attributesRepository.saveAndFlush(attributes);
        Long userattributeapprovalrulesAttributeId = userattributeapprovalrulesAttribute.getId();

        // Get all the attributesList where userattributeapprovalrulesAttribute equals to userattributeapprovalrulesAttributeId
        defaultAttributesShouldBeFound("userattributeapprovalrulesAttributeId.equals=" + userattributeapprovalrulesAttributeId);

        // Get all the attributesList where userattributeapprovalrulesAttribute equals to (userattributeapprovalrulesAttributeId + 1)
        defaultAttributesShouldNotBeFound("userattributeapprovalrulesAttributeId.equals=" + (userattributeapprovalrulesAttributeId + 1));
    }

    @Test
    @Transactional
    void getAllAttributesByUserattributeescalationrulesAttributeIsEqualToSomething() throws Exception {
        UserAttributeEscalationRules userattributeescalationrulesAttribute;
        if (TestUtil.findAll(em, UserAttributeEscalationRules.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            userattributeescalationrulesAttribute = UserAttributeEscalationRulesResourceIT.createEntity(em);
        } else {
            userattributeescalationrulesAttribute = TestUtil.findAll(em, UserAttributeEscalationRules.class).get(0);
        }
        em.persist(userattributeescalationrulesAttribute);
        em.flush();
        attributes.addUserattributeescalationrulesAttribute(userattributeescalationrulesAttribute);
        attributesRepository.saveAndFlush(attributes);
        Long userattributeescalationrulesAttributeId = userattributeescalationrulesAttribute.getId();

        // Get all the attributesList where userattributeescalationrulesAttribute equals to userattributeescalationrulesAttributeId
        defaultAttributesShouldBeFound("userattributeescalationrulesAttributeId.equals=" + userattributeescalationrulesAttributeId);

        // Get all the attributesList where userattributeescalationrulesAttribute equals to (userattributeescalationrulesAttributeId + 1)
        defaultAttributesShouldNotBeFound(
            "userattributeescalationrulesAttributeId.equals=" + (userattributeescalationrulesAttributeId + 1)
        );
    }

    @Test
    @Transactional
    void getAllAttributesByUserattributesAttributeIsEqualToSomething() throws Exception {
        UserAttributes userattributesAttribute;
        if (TestUtil.findAll(em, UserAttributes.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            userattributesAttribute = UserAttributesResourceIT.createEntity(em);
        } else {
            userattributesAttribute = TestUtil.findAll(em, UserAttributes.class).get(0);
        }
        em.persist(userattributesAttribute);
        em.flush();
        attributes.addUserattributesAttribute(userattributesAttribute);
        attributesRepository.saveAndFlush(attributes);
        Long userattributesAttributeId = userattributesAttribute.getId();

        // Get all the attributesList where userattributesAttribute equals to userattributesAttributeId
        defaultAttributesShouldBeFound("userattributesAttributeId.equals=" + userattributesAttributeId);

        // Get all the attributesList where userattributesAttribute equals to (userattributesAttributeId + 1)
        defaultAttributesShouldNotBeFound("userattributesAttributeId.equals=" + (userattributesAttributeId + 1));
    }

    @Test
    @Transactional
    void getAllAttributesByUserrelationapprovalrulesAttributeIsEqualToSomething() throws Exception {
        UserRelationApprovalRules userrelationapprovalrulesAttribute;
        if (TestUtil.findAll(em, UserRelationApprovalRules.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            userrelationapprovalrulesAttribute = UserRelationApprovalRulesResourceIT.createEntity(em);
        } else {
            userrelationapprovalrulesAttribute = TestUtil.findAll(em, UserRelationApprovalRules.class).get(0);
        }
        em.persist(userrelationapprovalrulesAttribute);
        em.flush();
        attributes.addUserrelationapprovalrulesAttribute(userrelationapprovalrulesAttribute);
        attributesRepository.saveAndFlush(attributes);
        Long userrelationapprovalrulesAttributeId = userrelationapprovalrulesAttribute.getId();

        // Get all the attributesList where userrelationapprovalrulesAttribute equals to userrelationapprovalrulesAttributeId
        defaultAttributesShouldBeFound("userrelationapprovalrulesAttributeId.equals=" + userrelationapprovalrulesAttributeId);

        // Get all the attributesList where userrelationapprovalrulesAttribute equals to (userrelationapprovalrulesAttributeId + 1)
        defaultAttributesShouldNotBeFound("userrelationapprovalrulesAttributeId.equals=" + (userrelationapprovalrulesAttributeId + 1));
    }

    @Test
    @Transactional
    void getAllAttributesByUserrelationsAttributeIsEqualToSomething() throws Exception {
        UserRelations userrelationsAttribute;
        if (TestUtil.findAll(em, UserRelations.class).isEmpty()) {
            attributesRepository.saveAndFlush(attributes);
            userrelationsAttribute = UserRelationsResourceIT.createEntity(em);
        } else {
            userrelationsAttribute = TestUtil.findAll(em, UserRelations.class).get(0);
        }
        em.persist(userrelationsAttribute);
        em.flush();
        attributes.addUserrelationsAttribute(userrelationsAttribute);
        attributesRepository.saveAndFlush(attributes);
        Long userrelationsAttributeId = userrelationsAttribute.getId();

        // Get all the attributesList where userrelationsAttribute equals to userrelationsAttributeId
        defaultAttributesShouldBeFound("userrelationsAttributeId.equals=" + userrelationsAttributeId);

        // Get all the attributesList where userrelationsAttribute equals to (userrelationsAttributeId + 1)
        defaultAttributesShouldNotBeFound("userrelationsAttributeId.equals=" + (userrelationsAttributeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttributesShouldBeFound(String filter) throws Exception {
        restAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())));

        // Check, that the count call also returns 1
        restAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttributesShouldNotBeFound(String filter) throws Exception {
        restAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAttributes() throws Exception {
        // Get the attributes
        restAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttributes() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();

        // Update the attributes
        Attributes updatedAttributes = attributesRepository.findById(attributes.getId()).get();
        // Disconnect from session so that the updates on updatedAttributes are not directly saved in db
        em.detach(updatedAttributes);
        updatedAttributes
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION)
            .effDate(UPDATED_EFF_DATE);

        restAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAttributes))
            )
            .andExpect(status().isOk());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
        Attributes testAttributes = attributesList.get(attributesList.size() - 1);
        assertThat(testAttributes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAttributes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAttributes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();
        attributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributes() throws Exception {
        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();
        attributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributes() throws Exception {
        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();
        attributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributesWithPatch() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();

        // Update the attributes using partial update
        Attributes partialUpdatedAttributes = new Attributes();
        partialUpdatedAttributes.setId(attributes.getId());

        partialUpdatedAttributes.updatedAt(UPDATED_UPDATED_AT).endDate(UPDATED_END_DATE);

        restAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributes))
            )
            .andExpect(status().isOk());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
        Attributes testAttributes = attributesList.get(attributesList.size() - 1);
        assertThat(testAttributes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAttributes.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAttributes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAttributes.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAttributesWithPatch() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();

        // Update the attributes using partial update
        Attributes partialUpdatedAttributes = new Attributes();
        partialUpdatedAttributes.setId(attributes.getId());

        partialUpdatedAttributes
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION)
            .effDate(UPDATED_EFF_DATE);

        restAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributes))
            )
            .andExpect(status().isOk());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
        Attributes testAttributes = attributesList.get(attributesList.size() - 1);
        assertThat(testAttributes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAttributes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAttributes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAttributes() throws Exception {
        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();
        attributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributes() throws Exception {
        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();
        attributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributes() throws Exception {
        int databaseSizeBeforeUpdate = attributesRepository.findAll().size();
        attributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attributes in the database
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributes() throws Exception {
        // Initialize the database
        attributesRepository.saveAndFlush(attributes);

        int databaseSizeBeforeDelete = attributesRepository.findAll().size();

        // Delete the attributes
        restAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attributes> attributesList = attributesRepository.findAll();
        assertThat(attributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
