package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Configurations;
import com.venturedive.vendian_mono.repository.ConfigurationsRepository;
import com.venturedive.vendian_mono.service.criteria.ConfigurationsCriteria;
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
 * Integration tests for the {@link ConfigurationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigurationsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    private static final Integer DEFAULT_INT_VALUE = 1;
    private static final Integer UPDATED_INT_VALUE = 2;
    private static final Integer SMALLER_INT_VALUE = 1 - 1;

    private static final String DEFAULT_STRING_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_STRING_VALUE = "BBBBBBBBBB";

    private static final Double DEFAULT_DOUBLE_VALUE = 1D;
    private static final Double UPDATED_DOUBLE_VALUE = 2D;
    private static final Double SMALLER_DOUBLE_VALUE = 1D - 1D;

    private static final Instant DEFAULT_DATE_VALUE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_VALUE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_JSON_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_JSON_VALUE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfigurationsRepository configurationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigurationsMockMvc;

    private Configurations configurations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Configurations createEntity(EntityManager em) {
        Configurations configurations = new Configurations()
            .name(DEFAULT_NAME)
            .group(DEFAULT_GROUP)
            .intValue(DEFAULT_INT_VALUE)
            .stringValue(DEFAULT_STRING_VALUE)
            .doubleValue(DEFAULT_DOUBLE_VALUE)
            .dateValue(DEFAULT_DATE_VALUE)
            .jsonValue(DEFAULT_JSON_VALUE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT)
            .version(DEFAULT_VERSION);
        return configurations;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Configurations createUpdatedEntity(EntityManager em) {
        Configurations configurations = new Configurations()
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .intValue(UPDATED_INT_VALUE)
            .stringValue(UPDATED_STRING_VALUE)
            .doubleValue(UPDATED_DOUBLE_VALUE)
            .dateValue(UPDATED_DATE_VALUE)
            .jsonValue(UPDATED_JSON_VALUE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);
        return configurations;
    }

    @BeforeEach
    public void initTest() {
        configurations = createEntity(em);
    }

    @Test
    @Transactional
    void createConfigurations() throws Exception {
        int databaseSizeBeforeCreate = configurationsRepository.findAll().size();
        // Create the Configurations
        restConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isCreated());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeCreate + 1);
        Configurations testConfigurations = configurationsList.get(configurationsList.size() - 1);
        assertThat(testConfigurations.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConfigurations.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testConfigurations.getIntValue()).isEqualTo(DEFAULT_INT_VALUE);
        assertThat(testConfigurations.getStringValue()).isEqualTo(DEFAULT_STRING_VALUE);
        assertThat(testConfigurations.getDoubleValue()).isEqualTo(DEFAULT_DOUBLE_VALUE);
        assertThat(testConfigurations.getDateValue()).isEqualTo(DEFAULT_DATE_VALUE);
        assertThat(testConfigurations.getJsonValue()).isEqualTo(DEFAULT_JSON_VALUE);
        assertThat(testConfigurations.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testConfigurations.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testConfigurations.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testConfigurations.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createConfigurationsWithExistingId() throws Exception {
        // Create the Configurations with an existing ID
        configurations.setId(1L);

        int databaseSizeBeforeCreate = configurationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationsRepository.findAll().size();
        // set the field null
        configurations.setName(null);

        // Create the Configurations, which fails.

        restConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationsRepository.findAll().size();
        // set the field null
        configurations.setGroup(null);

        // Create the Configurations, which fails.

        restConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationsRepository.findAll().size();
        // set the field null
        configurations.setCreatedAt(null);

        // Create the Configurations, which fails.

        restConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationsRepository.findAll().size();
        // set the field null
        configurations.setUpdatedAt(null);

        // Create the Configurations, which fails.

        restConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationsRepository.findAll().size();
        // set the field null
        configurations.setVersion(null);

        // Create the Configurations, which fails.

        restConfigurationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConfigurations() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList
        restConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configurations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].intValue").value(hasItem(DEFAULT_INT_VALUE)))
            .andExpect(jsonPath("$.[*].stringValue").value(hasItem(DEFAULT_STRING_VALUE)))
            .andExpect(jsonPath("$.[*].doubleValue").value(hasItem(DEFAULT_DOUBLE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateValue").value(hasItem(DEFAULT_DATE_VALUE.toString())))
            .andExpect(jsonPath("$.[*].jsonValue").value(hasItem(DEFAULT_JSON_VALUE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getConfigurations() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get the configurations
        restConfigurationsMockMvc
            .perform(get(ENTITY_API_URL_ID, configurations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(configurations.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.intValue").value(DEFAULT_INT_VALUE))
            .andExpect(jsonPath("$.stringValue").value(DEFAULT_STRING_VALUE))
            .andExpect(jsonPath("$.doubleValue").value(DEFAULT_DOUBLE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.dateValue").value(DEFAULT_DATE_VALUE.toString()))
            .andExpect(jsonPath("$.jsonValue").value(DEFAULT_JSON_VALUE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getConfigurationsByIdFiltering() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        Long id = configurations.getId();

        defaultConfigurationsShouldBeFound("id.equals=" + id);
        defaultConfigurationsShouldNotBeFound("id.notEquals=" + id);

        defaultConfigurationsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConfigurationsShouldNotBeFound("id.greaterThan=" + id);

        defaultConfigurationsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConfigurationsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConfigurationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where name equals to DEFAULT_NAME
        defaultConfigurationsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the configurationsList where name equals to UPDATED_NAME
        defaultConfigurationsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConfigurationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultConfigurationsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the configurationsList where name equals to UPDATED_NAME
        defaultConfigurationsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConfigurationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where name is not null
        defaultConfigurationsShouldBeFound("name.specified=true");

        // Get all the configurationsList where name is null
        defaultConfigurationsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByNameContainsSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where name contains DEFAULT_NAME
        defaultConfigurationsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the configurationsList where name contains UPDATED_NAME
        defaultConfigurationsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConfigurationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where name does not contain DEFAULT_NAME
        defaultConfigurationsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the configurationsList where name does not contain UPDATED_NAME
        defaultConfigurationsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConfigurationsByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where group equals to DEFAULT_GROUP
        defaultConfigurationsShouldBeFound("group.equals=" + DEFAULT_GROUP);

        // Get all the configurationsList where group equals to UPDATED_GROUP
        defaultConfigurationsShouldNotBeFound("group.equals=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllConfigurationsByGroupIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where group in DEFAULT_GROUP or UPDATED_GROUP
        defaultConfigurationsShouldBeFound("group.in=" + DEFAULT_GROUP + "," + UPDATED_GROUP);

        // Get all the configurationsList where group equals to UPDATED_GROUP
        defaultConfigurationsShouldNotBeFound("group.in=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllConfigurationsByGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where group is not null
        defaultConfigurationsShouldBeFound("group.specified=true");

        // Get all the configurationsList where group is null
        defaultConfigurationsShouldNotBeFound("group.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByGroupContainsSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where group contains DEFAULT_GROUP
        defaultConfigurationsShouldBeFound("group.contains=" + DEFAULT_GROUP);

        // Get all the configurationsList where group contains UPDATED_GROUP
        defaultConfigurationsShouldNotBeFound("group.contains=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllConfigurationsByGroupNotContainsSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where group does not contain DEFAULT_GROUP
        defaultConfigurationsShouldNotBeFound("group.doesNotContain=" + DEFAULT_GROUP);

        // Get all the configurationsList where group does not contain UPDATED_GROUP
        defaultConfigurationsShouldBeFound("group.doesNotContain=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllConfigurationsByIntValueIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where intValue equals to DEFAULT_INT_VALUE
        defaultConfigurationsShouldBeFound("intValue.equals=" + DEFAULT_INT_VALUE);

        // Get all the configurationsList where intValue equals to UPDATED_INT_VALUE
        defaultConfigurationsShouldNotBeFound("intValue.equals=" + UPDATED_INT_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByIntValueIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where intValue in DEFAULT_INT_VALUE or UPDATED_INT_VALUE
        defaultConfigurationsShouldBeFound("intValue.in=" + DEFAULT_INT_VALUE + "," + UPDATED_INT_VALUE);

        // Get all the configurationsList where intValue equals to UPDATED_INT_VALUE
        defaultConfigurationsShouldNotBeFound("intValue.in=" + UPDATED_INT_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByIntValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where intValue is not null
        defaultConfigurationsShouldBeFound("intValue.specified=true");

        // Get all the configurationsList where intValue is null
        defaultConfigurationsShouldNotBeFound("intValue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByIntValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where intValue is greater than or equal to DEFAULT_INT_VALUE
        defaultConfigurationsShouldBeFound("intValue.greaterThanOrEqual=" + DEFAULT_INT_VALUE);

        // Get all the configurationsList where intValue is greater than or equal to UPDATED_INT_VALUE
        defaultConfigurationsShouldNotBeFound("intValue.greaterThanOrEqual=" + UPDATED_INT_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByIntValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where intValue is less than or equal to DEFAULT_INT_VALUE
        defaultConfigurationsShouldBeFound("intValue.lessThanOrEqual=" + DEFAULT_INT_VALUE);

        // Get all the configurationsList where intValue is less than or equal to SMALLER_INT_VALUE
        defaultConfigurationsShouldNotBeFound("intValue.lessThanOrEqual=" + SMALLER_INT_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByIntValueIsLessThanSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where intValue is less than DEFAULT_INT_VALUE
        defaultConfigurationsShouldNotBeFound("intValue.lessThan=" + DEFAULT_INT_VALUE);

        // Get all the configurationsList where intValue is less than UPDATED_INT_VALUE
        defaultConfigurationsShouldBeFound("intValue.lessThan=" + UPDATED_INT_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByIntValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where intValue is greater than DEFAULT_INT_VALUE
        defaultConfigurationsShouldNotBeFound("intValue.greaterThan=" + DEFAULT_INT_VALUE);

        // Get all the configurationsList where intValue is greater than SMALLER_INT_VALUE
        defaultConfigurationsShouldBeFound("intValue.greaterThan=" + SMALLER_INT_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByStringValueIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where stringValue equals to DEFAULT_STRING_VALUE
        defaultConfigurationsShouldBeFound("stringValue.equals=" + DEFAULT_STRING_VALUE);

        // Get all the configurationsList where stringValue equals to UPDATED_STRING_VALUE
        defaultConfigurationsShouldNotBeFound("stringValue.equals=" + UPDATED_STRING_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByStringValueIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where stringValue in DEFAULT_STRING_VALUE or UPDATED_STRING_VALUE
        defaultConfigurationsShouldBeFound("stringValue.in=" + DEFAULT_STRING_VALUE + "," + UPDATED_STRING_VALUE);

        // Get all the configurationsList where stringValue equals to UPDATED_STRING_VALUE
        defaultConfigurationsShouldNotBeFound("stringValue.in=" + UPDATED_STRING_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByStringValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where stringValue is not null
        defaultConfigurationsShouldBeFound("stringValue.specified=true");

        // Get all the configurationsList where stringValue is null
        defaultConfigurationsShouldNotBeFound("stringValue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByStringValueContainsSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where stringValue contains DEFAULT_STRING_VALUE
        defaultConfigurationsShouldBeFound("stringValue.contains=" + DEFAULT_STRING_VALUE);

        // Get all the configurationsList where stringValue contains UPDATED_STRING_VALUE
        defaultConfigurationsShouldNotBeFound("stringValue.contains=" + UPDATED_STRING_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByStringValueNotContainsSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where stringValue does not contain DEFAULT_STRING_VALUE
        defaultConfigurationsShouldNotBeFound("stringValue.doesNotContain=" + DEFAULT_STRING_VALUE);

        // Get all the configurationsList where stringValue does not contain UPDATED_STRING_VALUE
        defaultConfigurationsShouldBeFound("stringValue.doesNotContain=" + UPDATED_STRING_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDoubleValueIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where doubleValue equals to DEFAULT_DOUBLE_VALUE
        defaultConfigurationsShouldBeFound("doubleValue.equals=" + DEFAULT_DOUBLE_VALUE);

        // Get all the configurationsList where doubleValue equals to UPDATED_DOUBLE_VALUE
        defaultConfigurationsShouldNotBeFound("doubleValue.equals=" + UPDATED_DOUBLE_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDoubleValueIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where doubleValue in DEFAULT_DOUBLE_VALUE or UPDATED_DOUBLE_VALUE
        defaultConfigurationsShouldBeFound("doubleValue.in=" + DEFAULT_DOUBLE_VALUE + "," + UPDATED_DOUBLE_VALUE);

        // Get all the configurationsList where doubleValue equals to UPDATED_DOUBLE_VALUE
        defaultConfigurationsShouldNotBeFound("doubleValue.in=" + UPDATED_DOUBLE_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDoubleValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where doubleValue is not null
        defaultConfigurationsShouldBeFound("doubleValue.specified=true");

        // Get all the configurationsList where doubleValue is null
        defaultConfigurationsShouldNotBeFound("doubleValue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByDoubleValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where doubleValue is greater than or equal to DEFAULT_DOUBLE_VALUE
        defaultConfigurationsShouldBeFound("doubleValue.greaterThanOrEqual=" + DEFAULT_DOUBLE_VALUE);

        // Get all the configurationsList where doubleValue is greater than or equal to UPDATED_DOUBLE_VALUE
        defaultConfigurationsShouldNotBeFound("doubleValue.greaterThanOrEqual=" + UPDATED_DOUBLE_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDoubleValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where doubleValue is less than or equal to DEFAULT_DOUBLE_VALUE
        defaultConfigurationsShouldBeFound("doubleValue.lessThanOrEqual=" + DEFAULT_DOUBLE_VALUE);

        // Get all the configurationsList where doubleValue is less than or equal to SMALLER_DOUBLE_VALUE
        defaultConfigurationsShouldNotBeFound("doubleValue.lessThanOrEqual=" + SMALLER_DOUBLE_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDoubleValueIsLessThanSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where doubleValue is less than DEFAULT_DOUBLE_VALUE
        defaultConfigurationsShouldNotBeFound("doubleValue.lessThan=" + DEFAULT_DOUBLE_VALUE);

        // Get all the configurationsList where doubleValue is less than UPDATED_DOUBLE_VALUE
        defaultConfigurationsShouldBeFound("doubleValue.lessThan=" + UPDATED_DOUBLE_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDoubleValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where doubleValue is greater than DEFAULT_DOUBLE_VALUE
        defaultConfigurationsShouldNotBeFound("doubleValue.greaterThan=" + DEFAULT_DOUBLE_VALUE);

        // Get all the configurationsList where doubleValue is greater than SMALLER_DOUBLE_VALUE
        defaultConfigurationsShouldBeFound("doubleValue.greaterThan=" + SMALLER_DOUBLE_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDateValueIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where dateValue equals to DEFAULT_DATE_VALUE
        defaultConfigurationsShouldBeFound("dateValue.equals=" + DEFAULT_DATE_VALUE);

        // Get all the configurationsList where dateValue equals to UPDATED_DATE_VALUE
        defaultConfigurationsShouldNotBeFound("dateValue.equals=" + UPDATED_DATE_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDateValueIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where dateValue in DEFAULT_DATE_VALUE or UPDATED_DATE_VALUE
        defaultConfigurationsShouldBeFound("dateValue.in=" + DEFAULT_DATE_VALUE + "," + UPDATED_DATE_VALUE);

        // Get all the configurationsList where dateValue equals to UPDATED_DATE_VALUE
        defaultConfigurationsShouldNotBeFound("dateValue.in=" + UPDATED_DATE_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDateValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where dateValue is not null
        defaultConfigurationsShouldBeFound("dateValue.specified=true");

        // Get all the configurationsList where dateValue is null
        defaultConfigurationsShouldNotBeFound("dateValue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByJsonValueIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where jsonValue equals to DEFAULT_JSON_VALUE
        defaultConfigurationsShouldBeFound("jsonValue.equals=" + DEFAULT_JSON_VALUE);

        // Get all the configurationsList where jsonValue equals to UPDATED_JSON_VALUE
        defaultConfigurationsShouldNotBeFound("jsonValue.equals=" + UPDATED_JSON_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByJsonValueIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where jsonValue in DEFAULT_JSON_VALUE or UPDATED_JSON_VALUE
        defaultConfigurationsShouldBeFound("jsonValue.in=" + DEFAULT_JSON_VALUE + "," + UPDATED_JSON_VALUE);

        // Get all the configurationsList where jsonValue equals to UPDATED_JSON_VALUE
        defaultConfigurationsShouldNotBeFound("jsonValue.in=" + UPDATED_JSON_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByJsonValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where jsonValue is not null
        defaultConfigurationsShouldBeFound("jsonValue.specified=true");

        // Get all the configurationsList where jsonValue is null
        defaultConfigurationsShouldNotBeFound("jsonValue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByJsonValueContainsSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where jsonValue contains DEFAULT_JSON_VALUE
        defaultConfigurationsShouldBeFound("jsonValue.contains=" + DEFAULT_JSON_VALUE);

        // Get all the configurationsList where jsonValue contains UPDATED_JSON_VALUE
        defaultConfigurationsShouldNotBeFound("jsonValue.contains=" + UPDATED_JSON_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByJsonValueNotContainsSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where jsonValue does not contain DEFAULT_JSON_VALUE
        defaultConfigurationsShouldNotBeFound("jsonValue.doesNotContain=" + DEFAULT_JSON_VALUE);

        // Get all the configurationsList where jsonValue does not contain UPDATED_JSON_VALUE
        defaultConfigurationsShouldBeFound("jsonValue.doesNotContain=" + UPDATED_JSON_VALUE);
    }

    @Test
    @Transactional
    void getAllConfigurationsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where createdAt equals to DEFAULT_CREATED_AT
        defaultConfigurationsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the configurationsList where createdAt equals to UPDATED_CREATED_AT
        defaultConfigurationsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllConfigurationsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultConfigurationsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the configurationsList where createdAt equals to UPDATED_CREATED_AT
        defaultConfigurationsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllConfigurationsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where createdAt is not null
        defaultConfigurationsShouldBeFound("createdAt.specified=true");

        // Get all the configurationsList where createdAt is null
        defaultConfigurationsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultConfigurationsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the configurationsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultConfigurationsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllConfigurationsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultConfigurationsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the configurationsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultConfigurationsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllConfigurationsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where updatedAt is not null
        defaultConfigurationsShouldBeFound("updatedAt.specified=true");

        // Get all the configurationsList where updatedAt is null
        defaultConfigurationsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultConfigurationsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the configurationsList where deletedAt equals to UPDATED_DELETED_AT
        defaultConfigurationsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultConfigurationsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the configurationsList where deletedAt equals to UPDATED_DELETED_AT
        defaultConfigurationsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void getAllConfigurationsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where deletedAt is not null
        defaultConfigurationsShouldBeFound("deletedAt.specified=true");

        // Get all the configurationsList where deletedAt is null
        defaultConfigurationsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where version equals to DEFAULT_VERSION
        defaultConfigurationsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the configurationsList where version equals to UPDATED_VERSION
        defaultConfigurationsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllConfigurationsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultConfigurationsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the configurationsList where version equals to UPDATED_VERSION
        defaultConfigurationsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllConfigurationsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where version is not null
        defaultConfigurationsShouldBeFound("version.specified=true");

        // Get all the configurationsList where version is null
        defaultConfigurationsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigurationsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where version is greater than or equal to DEFAULT_VERSION
        defaultConfigurationsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the configurationsList where version is greater than or equal to UPDATED_VERSION
        defaultConfigurationsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllConfigurationsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where version is less than or equal to DEFAULT_VERSION
        defaultConfigurationsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the configurationsList where version is less than or equal to SMALLER_VERSION
        defaultConfigurationsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllConfigurationsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where version is less than DEFAULT_VERSION
        defaultConfigurationsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the configurationsList where version is less than UPDATED_VERSION
        defaultConfigurationsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllConfigurationsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        // Get all the configurationsList where version is greater than DEFAULT_VERSION
        defaultConfigurationsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the configurationsList where version is greater than SMALLER_VERSION
        defaultConfigurationsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConfigurationsShouldBeFound(String filter) throws Exception {
        restConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configurations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].intValue").value(hasItem(DEFAULT_INT_VALUE)))
            .andExpect(jsonPath("$.[*].stringValue").value(hasItem(DEFAULT_STRING_VALUE)))
            .andExpect(jsonPath("$.[*].doubleValue").value(hasItem(DEFAULT_DOUBLE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateValue").value(hasItem(DEFAULT_DATE_VALUE.toString())))
            .andExpect(jsonPath("$.[*].jsonValue").value(hasItem(DEFAULT_JSON_VALUE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));

        // Check, that the count call also returns 1
        restConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConfigurationsShouldNotBeFound(String filter) throws Exception {
        restConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConfigurationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConfigurations() throws Exception {
        // Get the configurations
        restConfigurationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfigurations() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();

        // Update the configurations
        Configurations updatedConfigurations = configurationsRepository.findById(configurations.getId()).get();
        // Disconnect from session so that the updates on updatedConfigurations are not directly saved in db
        em.detach(updatedConfigurations);
        updatedConfigurations
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .intValue(UPDATED_INT_VALUE)
            .stringValue(UPDATED_STRING_VALUE)
            .doubleValue(UPDATED_DOUBLE_VALUE)
            .dateValue(UPDATED_DATE_VALUE)
            .jsonValue(UPDATED_JSON_VALUE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restConfigurationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConfigurations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConfigurations))
            )
            .andExpect(status().isOk());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
        Configurations testConfigurations = configurationsList.get(configurationsList.size() - 1);
        assertThat(testConfigurations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConfigurations.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testConfigurations.getIntValue()).isEqualTo(UPDATED_INT_VALUE);
        assertThat(testConfigurations.getStringValue()).isEqualTo(UPDATED_STRING_VALUE);
        assertThat(testConfigurations.getDoubleValue()).isEqualTo(UPDATED_DOUBLE_VALUE);
        assertThat(testConfigurations.getDateValue()).isEqualTo(UPDATED_DATE_VALUE);
        assertThat(testConfigurations.getJsonValue()).isEqualTo(UPDATED_JSON_VALUE);
        assertThat(testConfigurations.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testConfigurations.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testConfigurations.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testConfigurations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();
        configurations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigurationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configurations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();
        configurations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigurationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();
        configurations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigurationsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigurationsWithPatch() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();

        // Update the configurations using partial update
        Configurations partialUpdatedConfigurations = new Configurations();
        partialUpdatedConfigurations.setId(configurations.getId());

        partialUpdatedConfigurations.stringValue(UPDATED_STRING_VALUE).createdAt(UPDATED_CREATED_AT).version(UPDATED_VERSION);

        restConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigurations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigurations))
            )
            .andExpect(status().isOk());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
        Configurations testConfigurations = configurationsList.get(configurationsList.size() - 1);
        assertThat(testConfigurations.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConfigurations.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testConfigurations.getIntValue()).isEqualTo(DEFAULT_INT_VALUE);
        assertThat(testConfigurations.getStringValue()).isEqualTo(UPDATED_STRING_VALUE);
        assertThat(testConfigurations.getDoubleValue()).isEqualTo(DEFAULT_DOUBLE_VALUE);
        assertThat(testConfigurations.getDateValue()).isEqualTo(DEFAULT_DATE_VALUE);
        assertThat(testConfigurations.getJsonValue()).isEqualTo(DEFAULT_JSON_VALUE);
        assertThat(testConfigurations.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testConfigurations.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testConfigurations.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testConfigurations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateConfigurationsWithPatch() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();

        // Update the configurations using partial update
        Configurations partialUpdatedConfigurations = new Configurations();
        partialUpdatedConfigurations.setId(configurations.getId());

        partialUpdatedConfigurations
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .intValue(UPDATED_INT_VALUE)
            .stringValue(UPDATED_STRING_VALUE)
            .doubleValue(UPDATED_DOUBLE_VALUE)
            .dateValue(UPDATED_DATE_VALUE)
            .jsonValue(UPDATED_JSON_VALUE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT)
            .version(UPDATED_VERSION);

        restConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigurations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigurations))
            )
            .andExpect(status().isOk());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
        Configurations testConfigurations = configurationsList.get(configurationsList.size() - 1);
        assertThat(testConfigurations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConfigurations.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testConfigurations.getIntValue()).isEqualTo(UPDATED_INT_VALUE);
        assertThat(testConfigurations.getStringValue()).isEqualTo(UPDATED_STRING_VALUE);
        assertThat(testConfigurations.getDoubleValue()).isEqualTo(UPDATED_DOUBLE_VALUE);
        assertThat(testConfigurations.getDateValue()).isEqualTo(UPDATED_DATE_VALUE);
        assertThat(testConfigurations.getJsonValue()).isEqualTo(UPDATED_JSON_VALUE);
        assertThat(testConfigurations.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testConfigurations.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testConfigurations.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testConfigurations.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();
        configurations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configurations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();
        configurations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = configurationsRepository.findAll().size();
        configurations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigurationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configurations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Configurations in the database
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfigurations() throws Exception {
        // Initialize the database
        configurationsRepository.saveAndFlush(configurations);

        int databaseSizeBeforeDelete = configurationsRepository.findAll().size();

        // Delete the configurations
        restConfigurationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, configurations.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Configurations> configurationsList = configurationsRepository.findAll();
        assertThat(configurationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
