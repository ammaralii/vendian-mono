package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.UserAttributes;
import com.venturedive.vendian_mono.repository.UserAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.UserAttributesCriteria;
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
 * Integration tests for the {@link UserAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAttributesResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EFF_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFF_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserAttributesRepository userAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAttributesMockMvc;

    private UserAttributes userAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAttributes createEntity(EntityManager em) {
        UserAttributes userAttributes = new UserAttributes()
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .version(DEFAULT_VERSION)
            .endDate(DEFAULT_END_DATE)
            .effDate(DEFAULT_EFF_DATE);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        userAttributes.setAttribute(attributes);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        userAttributes.setUser(employees);
        return userAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAttributes createUpdatedEntity(EntityManager em) {
        UserAttributes userAttributes = new UserAttributes()
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION)
            .endDate(UPDATED_END_DATE)
            .effDate(UPDATED_EFF_DATE);
        // Add required entity
        Attributes attributes;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            attributes = AttributesResourceIT.createUpdatedEntity(em);
            em.persist(attributes);
            em.flush();
        } else {
            attributes = TestUtil.findAll(em, Attributes.class).get(0);
        }
        userAttributes.setAttribute(attributes);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        userAttributes.setUser(employees);
        return userAttributes;
    }

    @BeforeEach
    public void initTest() {
        userAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createUserAttributes() throws Exception {
        int databaseSizeBeforeCreate = userAttributesRepository.findAll().size();
        // Create the UserAttributes
        restUserAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        UserAttributes testUserAttributes = userAttributesList.get(userAttributesList.size() - 1);
        assertThat(testUserAttributes.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserAttributes.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUserAttributes.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testUserAttributes.getEffDate()).isEqualTo(DEFAULT_EFF_DATE);
    }

    @Test
    @Transactional
    void createUserAttributesWithExistingId() throws Exception {
        // Create the UserAttributes with an existing ID
        userAttributes.setId(1L);

        int databaseSizeBeforeCreate = userAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributesRepository.findAll().size();
        // set the field null
        userAttributes.setCreatedAt(null);

        // Create the UserAttributes, which fails.

        restUserAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributesRepository.findAll().size();
        // set the field null
        userAttributes.setUpdatedAt(null);

        // Create the UserAttributes, which fails.

        restUserAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAttributesRepository.findAll().size();
        // set the field null
        userAttributes.setVersion(null);

        // Create the UserAttributes, which fails.

        restUserAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isBadRequest());

        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserAttributes() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList
        restUserAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserAttributes() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get the userAttributes
        restUserAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, userAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAttributes.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.effDate").value(DEFAULT_EFF_DATE.toString()));
    }

    @Test
    @Transactional
    void getUserAttributesByIdFiltering() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        Long id = userAttributes.getId();

        defaultUserAttributesShouldBeFound("id.equals=" + id);
        defaultUserAttributesShouldNotBeFound("id.notEquals=" + id);

        defaultUserAttributesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserAttributesShouldNotBeFound("id.greaterThan=" + id);

        defaultUserAttributesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserAttributesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserAttributesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserAttributesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userAttributesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserAttributesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserAttributesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userAttributesList where createdAt equals to UPDATED_CREATED_AT
        defaultUserAttributesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where createdAt is not null
        defaultUserAttributesShouldBeFound("createdAt.specified=true");

        // Get all the userAttributesList where createdAt is null
        defaultUserAttributesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserAttributesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userAttributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserAttributesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserAttributesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userAttributesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserAttributesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllUserAttributesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where updatedAt is not null
        defaultUserAttributesShouldBeFound("updatedAt.specified=true");

        // Get all the userAttributesList where updatedAt is null
        defaultUserAttributesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where version equals to DEFAULT_VERSION
        defaultUserAttributesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the userAttributesList where version equals to UPDATED_VERSION
        defaultUserAttributesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultUserAttributesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the userAttributesList where version equals to UPDATED_VERSION
        defaultUserAttributesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where version is not null
        defaultUserAttributesShouldBeFound("version.specified=true");

        // Get all the userAttributesList where version is null
        defaultUserAttributesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where version is greater than or equal to DEFAULT_VERSION
        defaultUserAttributesShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userAttributesList where version is greater than or equal to UPDATED_VERSION
        defaultUserAttributesShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where version is less than or equal to DEFAULT_VERSION
        defaultUserAttributesShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the userAttributesList where version is less than or equal to SMALLER_VERSION
        defaultUserAttributesShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where version is less than DEFAULT_VERSION
        defaultUserAttributesShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the userAttributesList where version is less than UPDATED_VERSION
        defaultUserAttributesShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where version is greater than DEFAULT_VERSION
        defaultUserAttributesShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the userAttributesList where version is greater than SMALLER_VERSION
        defaultUserAttributesShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllUserAttributesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where endDate equals to DEFAULT_END_DATE
        defaultUserAttributesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the userAttributesList where endDate equals to UPDATED_END_DATE
        defaultUserAttributesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultUserAttributesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the userAttributesList where endDate equals to UPDATED_END_DATE
        defaultUserAttributesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where endDate is not null
        defaultUserAttributesShouldBeFound("endDate.specified=true");

        // Get all the userAttributesList where endDate is null
        defaultUserAttributesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributesByEffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where effDate equals to DEFAULT_EFF_DATE
        defaultUserAttributesShouldBeFound("effDate.equals=" + DEFAULT_EFF_DATE);

        // Get all the userAttributesList where effDate equals to UPDATED_EFF_DATE
        defaultUserAttributesShouldNotBeFound("effDate.equals=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributesByEffDateIsInShouldWork() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where effDate in DEFAULT_EFF_DATE or UPDATED_EFF_DATE
        defaultUserAttributesShouldBeFound("effDate.in=" + DEFAULT_EFF_DATE + "," + UPDATED_EFF_DATE);

        // Get all the userAttributesList where effDate equals to UPDATED_EFF_DATE
        defaultUserAttributesShouldNotBeFound("effDate.in=" + UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void getAllUserAttributesByEffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList where effDate is not null
        defaultUserAttributesShouldBeFound("effDate.specified=true");

        // Get all the userAttributesList where effDate is null
        defaultUserAttributesShouldNotBeFound("effDate.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAttributesByAttributeIsEqualToSomething() throws Exception {
        Attributes attribute;
        if (TestUtil.findAll(em, Attributes.class).isEmpty()) {
            userAttributesRepository.saveAndFlush(userAttributes);
            attribute = AttributesResourceIT.createEntity(em);
        } else {
            attribute = TestUtil.findAll(em, Attributes.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        userAttributes.setAttribute(attribute);
        userAttributesRepository.saveAndFlush(userAttributes);
        Long attributeId = attribute.getId();

        // Get all the userAttributesList where attribute equals to attributeId
        defaultUserAttributesShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the userAttributesList where attribute equals to (attributeId + 1)
        defaultUserAttributesShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    @Test
    @Transactional
    void getAllUserAttributesByUserIsEqualToSomething() throws Exception {
        Employees user;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            userAttributesRepository.saveAndFlush(userAttributes);
            user = EmployeesResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(user);
        em.flush();
        userAttributes.setUser(user);
        userAttributesRepository.saveAndFlush(userAttributes);
        Long userId = user.getId();

        // Get all the userAttributesList where user equals to userId
        defaultUserAttributesShouldBeFound("userId.equals=" + userId);

        // Get all the userAttributesList where user equals to (userId + 1)
        defaultUserAttributesShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserAttributesShouldBeFound(String filter) throws Exception {
        restUserAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].effDate").value(hasItem(DEFAULT_EFF_DATE.toString())));

        // Check, that the count call also returns 1
        restUserAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserAttributesShouldNotBeFound(String filter) throws Exception {
        restUserAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserAttributes() throws Exception {
        // Get the userAttributes
        restUserAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserAttributes() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();

        // Update the userAttributes
        UserAttributes updatedUserAttributes = userAttributesRepository.findById(userAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedUserAttributes are not directly saved in db
        em.detach(updatedUserAttributes);
        updatedUserAttributes
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION)
            .endDate(UPDATED_END_DATE)
            .effDate(UPDATED_EFF_DATE);

        restUserAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserAttributes))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributes testUserAttributes = userAttributesList.get(userAttributesList.size() - 1);
        assertThat(testUserAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUserAttributes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserAttributes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();
        userAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();
        userAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();
        userAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAttributesWithPatch() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();

        // Update the userAttributes using partial update
        UserAttributes partialUpdatedUserAttributes = new UserAttributes();
        partialUpdatedUserAttributes.setId(userAttributes.getId());

        partialUpdatedUserAttributes
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .endDate(UPDATED_END_DATE)
            .effDate(UPDATED_EFF_DATE);

        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAttributes))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributes testUserAttributes = userAttributesList.get(userAttributesList.size() - 1);
        assertThat(testUserAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testUserAttributes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserAttributes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserAttributesWithPatch() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();

        // Update the userAttributes using partial update
        UserAttributes partialUpdatedUserAttributes = new UserAttributes();
        partialUpdatedUserAttributes.setId(userAttributes.getId());

        partialUpdatedUserAttributes
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .version(UPDATED_VERSION)
            .endDate(UPDATED_END_DATE)
            .effDate(UPDATED_EFF_DATE);

        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAttributes))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
        UserAttributes testUserAttributes = userAttributesList.get(userAttributesList.size() - 1);
        assertThat(testUserAttributes.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAttributes.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAttributes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testUserAttributes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testUserAttributes.getEffDate()).isEqualTo(UPDATED_EFF_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();
        userAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();
        userAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAttributes() throws Exception {
        int databaseSizeBeforeUpdate = userAttributesRepository.findAll().size();
        userAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAttributes in the database
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAttributes() throws Exception {
        // Initialize the database
        userAttributesRepository.saveAndFlush(userAttributes);

        int databaseSizeBeforeDelete = userAttributesRepository.findAll().size();

        // Delete the userAttributes
        restUserAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAttributes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAttributes> userAttributesList = userAttributesRepository.findAll();
        assertThat(userAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
