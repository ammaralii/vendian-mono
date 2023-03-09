package com.venturedive.vendian_mono.web.rest;

import static com.venturedive.vendian_mono.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Configs;
import com.venturedive.vendian_mono.repository.ConfigsRepository;
import com.venturedive.vendian_mono.service.criteria.ConfigsCriteria;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ConfigsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    private static final Integer DEFAULT_INTVALUE = 1;
    private static final Integer UPDATED_INTVALUE = 2;
    private static final Integer SMALLER_INTVALUE = 1 - 1;

    private static final String DEFAULT_STRINGVALUE = "AAAAAAAAAA";
    private static final String UPDATED_STRINGVALUE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_DECIMALVALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DECIMALVALUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_DECIMALVALUE = new BigDecimal(1 - 1);

    private static final String DEFAULT_JSONVALUE = "AAAAAAAAAA";
    private static final String UPDATED_JSONVALUE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfigsRepository configsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigsMockMvc;

    private Configs configs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Configs createEntity(EntityManager em) {
        Configs configs = new Configs()
            .name(DEFAULT_NAME)
            .group(DEFAULT_GROUP)
            .intvalue(DEFAULT_INTVALUE)
            .stringvalue(DEFAULT_STRINGVALUE)
            .decimalvalue(DEFAULT_DECIMALVALUE)
            .jsonvalue(DEFAULT_JSONVALUE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return configs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Configs createUpdatedEntity(EntityManager em) {
        Configs configs = new Configs()
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .intvalue(UPDATED_INTVALUE)
            .stringvalue(UPDATED_STRINGVALUE)
            .decimalvalue(UPDATED_DECIMALVALUE)
            .jsonvalue(UPDATED_JSONVALUE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return configs;
    }

    @BeforeEach
    public void initTest() {
        configs = createEntity(em);
    }

    @Test
    @Transactional
    void createConfigs() throws Exception {
        int databaseSizeBeforeCreate = configsRepository.findAll().size();
        // Create the Configs
        restConfigsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isCreated());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeCreate + 1);
        Configs testConfigs = configsList.get(configsList.size() - 1);
        assertThat(testConfigs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConfigs.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testConfigs.getIntvalue()).isEqualTo(DEFAULT_INTVALUE);
        assertThat(testConfigs.getStringvalue()).isEqualTo(DEFAULT_STRINGVALUE);
        assertThat(testConfigs.getDecimalvalue()).isEqualByComparingTo(DEFAULT_DECIMALVALUE);
        assertThat(testConfigs.getJsonvalue()).isEqualTo(DEFAULT_JSONVALUE);
        assertThat(testConfigs.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testConfigs.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createConfigsWithExistingId() throws Exception {
        // Create the Configs with an existing ID
        configs.setId(1L);

        int databaseSizeBeforeCreate = configsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = configsRepository.findAll().size();
        // set the field null
        configs.setCreatedat(null);

        // Create the Configs, which fails.

        restConfigsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isBadRequest());

        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = configsRepository.findAll().size();
        // set the field null
        configs.setUpdatedat(null);

        // Create the Configs, which fails.

        restConfigsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isBadRequest());

        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConfigs() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList
        restConfigsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configs.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].intvalue").value(hasItem(DEFAULT_INTVALUE)))
            .andExpect(jsonPath("$.[*].stringvalue").value(hasItem(DEFAULT_STRINGVALUE)))
            .andExpect(jsonPath("$.[*].decimalvalue").value(hasItem(sameNumber(DEFAULT_DECIMALVALUE))))
            .andExpect(jsonPath("$.[*].jsonvalue").value(hasItem(DEFAULT_JSONVALUE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getConfigs() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get the configs
        restConfigsMockMvc
            .perform(get(ENTITY_API_URL_ID, configs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(configs.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.intvalue").value(DEFAULT_INTVALUE))
            .andExpect(jsonPath("$.stringvalue").value(DEFAULT_STRINGVALUE))
            .andExpect(jsonPath("$.decimalvalue").value(sameNumber(DEFAULT_DECIMALVALUE)))
            .andExpect(jsonPath("$.jsonvalue").value(DEFAULT_JSONVALUE))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getConfigsByIdFiltering() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        Long id = configs.getId();

        defaultConfigsShouldBeFound("id.equals=" + id);
        defaultConfigsShouldNotBeFound("id.notEquals=" + id);

        defaultConfigsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConfigsShouldNotBeFound("id.greaterThan=" + id);

        defaultConfigsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConfigsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConfigsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where name equals to DEFAULT_NAME
        defaultConfigsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the configsList where name equals to UPDATED_NAME
        defaultConfigsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultConfigsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the configsList where name equals to UPDATED_NAME
        defaultConfigsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where name is not null
        defaultConfigsShouldBeFound("name.specified=true");

        // Get all the configsList where name is null
        defaultConfigsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByNameContainsSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where name contains DEFAULT_NAME
        defaultConfigsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the configsList where name contains UPDATED_NAME
        defaultConfigsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where name does not contain DEFAULT_NAME
        defaultConfigsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the configsList where name does not contain UPDATED_NAME
        defaultConfigsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConfigsByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where group equals to DEFAULT_GROUP
        defaultConfigsShouldBeFound("group.equals=" + DEFAULT_GROUP);

        // Get all the configsList where group equals to UPDATED_GROUP
        defaultConfigsShouldNotBeFound("group.equals=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllConfigsByGroupIsInShouldWork() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where group in DEFAULT_GROUP or UPDATED_GROUP
        defaultConfigsShouldBeFound("group.in=" + DEFAULT_GROUP + "," + UPDATED_GROUP);

        // Get all the configsList where group equals to UPDATED_GROUP
        defaultConfigsShouldNotBeFound("group.in=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllConfigsByGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where group is not null
        defaultConfigsShouldBeFound("group.specified=true");

        // Get all the configsList where group is null
        defaultConfigsShouldNotBeFound("group.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByGroupContainsSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where group contains DEFAULT_GROUP
        defaultConfigsShouldBeFound("group.contains=" + DEFAULT_GROUP);

        // Get all the configsList where group contains UPDATED_GROUP
        defaultConfigsShouldNotBeFound("group.contains=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllConfigsByGroupNotContainsSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where group does not contain DEFAULT_GROUP
        defaultConfigsShouldNotBeFound("group.doesNotContain=" + DEFAULT_GROUP);

        // Get all the configsList where group does not contain UPDATED_GROUP
        defaultConfigsShouldBeFound("group.doesNotContain=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    void getAllConfigsByIntvalueIsEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where intvalue equals to DEFAULT_INTVALUE
        defaultConfigsShouldBeFound("intvalue.equals=" + DEFAULT_INTVALUE);

        // Get all the configsList where intvalue equals to UPDATED_INTVALUE
        defaultConfigsShouldNotBeFound("intvalue.equals=" + UPDATED_INTVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByIntvalueIsInShouldWork() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where intvalue in DEFAULT_INTVALUE or UPDATED_INTVALUE
        defaultConfigsShouldBeFound("intvalue.in=" + DEFAULT_INTVALUE + "," + UPDATED_INTVALUE);

        // Get all the configsList where intvalue equals to UPDATED_INTVALUE
        defaultConfigsShouldNotBeFound("intvalue.in=" + UPDATED_INTVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByIntvalueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where intvalue is not null
        defaultConfigsShouldBeFound("intvalue.specified=true");

        // Get all the configsList where intvalue is null
        defaultConfigsShouldNotBeFound("intvalue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByIntvalueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where intvalue is greater than or equal to DEFAULT_INTVALUE
        defaultConfigsShouldBeFound("intvalue.greaterThanOrEqual=" + DEFAULT_INTVALUE);

        // Get all the configsList where intvalue is greater than or equal to UPDATED_INTVALUE
        defaultConfigsShouldNotBeFound("intvalue.greaterThanOrEqual=" + UPDATED_INTVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByIntvalueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where intvalue is less than or equal to DEFAULT_INTVALUE
        defaultConfigsShouldBeFound("intvalue.lessThanOrEqual=" + DEFAULT_INTVALUE);

        // Get all the configsList where intvalue is less than or equal to SMALLER_INTVALUE
        defaultConfigsShouldNotBeFound("intvalue.lessThanOrEqual=" + SMALLER_INTVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByIntvalueIsLessThanSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where intvalue is less than DEFAULT_INTVALUE
        defaultConfigsShouldNotBeFound("intvalue.lessThan=" + DEFAULT_INTVALUE);

        // Get all the configsList where intvalue is less than UPDATED_INTVALUE
        defaultConfigsShouldBeFound("intvalue.lessThan=" + UPDATED_INTVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByIntvalueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where intvalue is greater than DEFAULT_INTVALUE
        defaultConfigsShouldNotBeFound("intvalue.greaterThan=" + DEFAULT_INTVALUE);

        // Get all the configsList where intvalue is greater than SMALLER_INTVALUE
        defaultConfigsShouldBeFound("intvalue.greaterThan=" + SMALLER_INTVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByStringvalueIsEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where stringvalue equals to DEFAULT_STRINGVALUE
        defaultConfigsShouldBeFound("stringvalue.equals=" + DEFAULT_STRINGVALUE);

        // Get all the configsList where stringvalue equals to UPDATED_STRINGVALUE
        defaultConfigsShouldNotBeFound("stringvalue.equals=" + UPDATED_STRINGVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByStringvalueIsInShouldWork() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where stringvalue in DEFAULT_STRINGVALUE or UPDATED_STRINGVALUE
        defaultConfigsShouldBeFound("stringvalue.in=" + DEFAULT_STRINGVALUE + "," + UPDATED_STRINGVALUE);

        // Get all the configsList where stringvalue equals to UPDATED_STRINGVALUE
        defaultConfigsShouldNotBeFound("stringvalue.in=" + UPDATED_STRINGVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByStringvalueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where stringvalue is not null
        defaultConfigsShouldBeFound("stringvalue.specified=true");

        // Get all the configsList where stringvalue is null
        defaultConfigsShouldNotBeFound("stringvalue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByStringvalueContainsSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where stringvalue contains DEFAULT_STRINGVALUE
        defaultConfigsShouldBeFound("stringvalue.contains=" + DEFAULT_STRINGVALUE);

        // Get all the configsList where stringvalue contains UPDATED_STRINGVALUE
        defaultConfigsShouldNotBeFound("stringvalue.contains=" + UPDATED_STRINGVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByStringvalueNotContainsSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where stringvalue does not contain DEFAULT_STRINGVALUE
        defaultConfigsShouldNotBeFound("stringvalue.doesNotContain=" + DEFAULT_STRINGVALUE);

        // Get all the configsList where stringvalue does not contain UPDATED_STRINGVALUE
        defaultConfigsShouldBeFound("stringvalue.doesNotContain=" + UPDATED_STRINGVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByDecimalvalueIsEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where decimalvalue equals to DEFAULT_DECIMALVALUE
        defaultConfigsShouldBeFound("decimalvalue.equals=" + DEFAULT_DECIMALVALUE);

        // Get all the configsList where decimalvalue equals to UPDATED_DECIMALVALUE
        defaultConfigsShouldNotBeFound("decimalvalue.equals=" + UPDATED_DECIMALVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByDecimalvalueIsInShouldWork() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where decimalvalue in DEFAULT_DECIMALVALUE or UPDATED_DECIMALVALUE
        defaultConfigsShouldBeFound("decimalvalue.in=" + DEFAULT_DECIMALVALUE + "," + UPDATED_DECIMALVALUE);

        // Get all the configsList where decimalvalue equals to UPDATED_DECIMALVALUE
        defaultConfigsShouldNotBeFound("decimalvalue.in=" + UPDATED_DECIMALVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByDecimalvalueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where decimalvalue is not null
        defaultConfigsShouldBeFound("decimalvalue.specified=true");

        // Get all the configsList where decimalvalue is null
        defaultConfigsShouldNotBeFound("decimalvalue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByDecimalvalueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where decimalvalue is greater than or equal to DEFAULT_DECIMALVALUE
        defaultConfigsShouldBeFound("decimalvalue.greaterThanOrEqual=" + DEFAULT_DECIMALVALUE);

        // Get all the configsList where decimalvalue is greater than or equal to UPDATED_DECIMALVALUE
        defaultConfigsShouldNotBeFound("decimalvalue.greaterThanOrEqual=" + UPDATED_DECIMALVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByDecimalvalueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where decimalvalue is less than or equal to DEFAULT_DECIMALVALUE
        defaultConfigsShouldBeFound("decimalvalue.lessThanOrEqual=" + DEFAULT_DECIMALVALUE);

        // Get all the configsList where decimalvalue is less than or equal to SMALLER_DECIMALVALUE
        defaultConfigsShouldNotBeFound("decimalvalue.lessThanOrEqual=" + SMALLER_DECIMALVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByDecimalvalueIsLessThanSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where decimalvalue is less than DEFAULT_DECIMALVALUE
        defaultConfigsShouldNotBeFound("decimalvalue.lessThan=" + DEFAULT_DECIMALVALUE);

        // Get all the configsList where decimalvalue is less than UPDATED_DECIMALVALUE
        defaultConfigsShouldBeFound("decimalvalue.lessThan=" + UPDATED_DECIMALVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByDecimalvalueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where decimalvalue is greater than DEFAULT_DECIMALVALUE
        defaultConfigsShouldNotBeFound("decimalvalue.greaterThan=" + DEFAULT_DECIMALVALUE);

        // Get all the configsList where decimalvalue is greater than SMALLER_DECIMALVALUE
        defaultConfigsShouldBeFound("decimalvalue.greaterThan=" + SMALLER_DECIMALVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByJsonvalueIsEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where jsonvalue equals to DEFAULT_JSONVALUE
        defaultConfigsShouldBeFound("jsonvalue.equals=" + DEFAULT_JSONVALUE);

        // Get all the configsList where jsonvalue equals to UPDATED_JSONVALUE
        defaultConfigsShouldNotBeFound("jsonvalue.equals=" + UPDATED_JSONVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByJsonvalueIsInShouldWork() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where jsonvalue in DEFAULT_JSONVALUE or UPDATED_JSONVALUE
        defaultConfigsShouldBeFound("jsonvalue.in=" + DEFAULT_JSONVALUE + "," + UPDATED_JSONVALUE);

        // Get all the configsList where jsonvalue equals to UPDATED_JSONVALUE
        defaultConfigsShouldNotBeFound("jsonvalue.in=" + UPDATED_JSONVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByJsonvalueIsNullOrNotNull() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where jsonvalue is not null
        defaultConfigsShouldBeFound("jsonvalue.specified=true");

        // Get all the configsList where jsonvalue is null
        defaultConfigsShouldNotBeFound("jsonvalue.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByJsonvalueContainsSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where jsonvalue contains DEFAULT_JSONVALUE
        defaultConfigsShouldBeFound("jsonvalue.contains=" + DEFAULT_JSONVALUE);

        // Get all the configsList where jsonvalue contains UPDATED_JSONVALUE
        defaultConfigsShouldNotBeFound("jsonvalue.contains=" + UPDATED_JSONVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByJsonvalueNotContainsSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where jsonvalue does not contain DEFAULT_JSONVALUE
        defaultConfigsShouldNotBeFound("jsonvalue.doesNotContain=" + DEFAULT_JSONVALUE);

        // Get all the configsList where jsonvalue does not contain UPDATED_JSONVALUE
        defaultConfigsShouldBeFound("jsonvalue.doesNotContain=" + UPDATED_JSONVALUE);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where createdat equals to DEFAULT_CREATEDAT
        defaultConfigsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the configsList where createdat equals to UPDATED_CREATEDAT
        defaultConfigsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultConfigsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the configsList where createdat equals to UPDATED_CREATEDAT
        defaultConfigsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllConfigsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where createdat is not null
        defaultConfigsShouldBeFound("createdat.specified=true");

        // Get all the configsList where createdat is null
        defaultConfigsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllConfigsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultConfigsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the configsList where updatedat equals to UPDATED_UPDATEDAT
        defaultConfigsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllConfigsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultConfigsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the configsList where updatedat equals to UPDATED_UPDATEDAT
        defaultConfigsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllConfigsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        // Get all the configsList where updatedat is not null
        defaultConfigsShouldBeFound("updatedat.specified=true");

        // Get all the configsList where updatedat is null
        defaultConfigsShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConfigsShouldBeFound(String filter) throws Exception {
        restConfigsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configs.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].intvalue").value(hasItem(DEFAULT_INTVALUE)))
            .andExpect(jsonPath("$.[*].stringvalue").value(hasItem(DEFAULT_STRINGVALUE)))
            .andExpect(jsonPath("$.[*].decimalvalue").value(hasItem(sameNumber(DEFAULT_DECIMALVALUE))))
            .andExpect(jsonPath("$.[*].jsonvalue").value(hasItem(DEFAULT_JSONVALUE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restConfigsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConfigsShouldNotBeFound(String filter) throws Exception {
        restConfigsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConfigsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConfigs() throws Exception {
        // Get the configs
        restConfigsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfigs() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        int databaseSizeBeforeUpdate = configsRepository.findAll().size();

        // Update the configs
        Configs updatedConfigs = configsRepository.findById(configs.getId()).get();
        // Disconnect from session so that the updates on updatedConfigs are not directly saved in db
        em.detach(updatedConfigs);
        updatedConfigs
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .intvalue(UPDATED_INTVALUE)
            .stringvalue(UPDATED_STRINGVALUE)
            .decimalvalue(UPDATED_DECIMALVALUE)
            .jsonvalue(UPDATED_JSONVALUE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restConfigsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConfigs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConfigs))
            )
            .andExpect(status().isOk());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
        Configs testConfigs = configsList.get(configsList.size() - 1);
        assertThat(testConfigs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConfigs.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testConfigs.getIntvalue()).isEqualTo(UPDATED_INTVALUE);
        assertThat(testConfigs.getStringvalue()).isEqualTo(UPDATED_STRINGVALUE);
        assertThat(testConfigs.getDecimalvalue()).isEqualByComparingTo(UPDATED_DECIMALVALUE);
        assertThat(testConfigs.getJsonvalue()).isEqualTo(UPDATED_JSONVALUE);
        assertThat(testConfigs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testConfigs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingConfigs() throws Exception {
        int databaseSizeBeforeUpdate = configsRepository.findAll().size();
        configs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfigs() throws Exception {
        int databaseSizeBeforeUpdate = configsRepository.findAll().size();
        configs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfigs() throws Exception {
        int databaseSizeBeforeUpdate = configsRepository.findAll().size();
        configs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigsMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigsWithPatch() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        int databaseSizeBeforeUpdate = configsRepository.findAll().size();

        // Update the configs using partial update
        Configs partialUpdatedConfigs = new Configs();
        partialUpdatedConfigs.setId(configs.getId());

        partialUpdatedConfigs
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .stringvalue(UPDATED_STRINGVALUE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restConfigsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigs))
            )
            .andExpect(status().isOk());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
        Configs testConfigs = configsList.get(configsList.size() - 1);
        assertThat(testConfigs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConfigs.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testConfigs.getIntvalue()).isEqualTo(DEFAULT_INTVALUE);
        assertThat(testConfigs.getStringvalue()).isEqualTo(UPDATED_STRINGVALUE);
        assertThat(testConfigs.getDecimalvalue()).isEqualByComparingTo(DEFAULT_DECIMALVALUE);
        assertThat(testConfigs.getJsonvalue()).isEqualTo(DEFAULT_JSONVALUE);
        assertThat(testConfigs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testConfigs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateConfigsWithPatch() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        int databaseSizeBeforeUpdate = configsRepository.findAll().size();

        // Update the configs using partial update
        Configs partialUpdatedConfigs = new Configs();
        partialUpdatedConfigs.setId(configs.getId());

        partialUpdatedConfigs
            .name(UPDATED_NAME)
            .group(UPDATED_GROUP)
            .intvalue(UPDATED_INTVALUE)
            .stringvalue(UPDATED_STRINGVALUE)
            .decimalvalue(UPDATED_DECIMALVALUE)
            .jsonvalue(UPDATED_JSONVALUE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restConfigsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigs))
            )
            .andExpect(status().isOk());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
        Configs testConfigs = configsList.get(configsList.size() - 1);
        assertThat(testConfigs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConfigs.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testConfigs.getIntvalue()).isEqualTo(UPDATED_INTVALUE);
        assertThat(testConfigs.getStringvalue()).isEqualTo(UPDATED_STRINGVALUE);
        assertThat(testConfigs.getDecimalvalue()).isEqualByComparingTo(UPDATED_DECIMALVALUE);
        assertThat(testConfigs.getJsonvalue()).isEqualTo(UPDATED_JSONVALUE);
        assertThat(testConfigs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testConfigs.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingConfigs() throws Exception {
        int databaseSizeBeforeUpdate = configsRepository.findAll().size();
        configs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfigs() throws Exception {
        int databaseSizeBeforeUpdate = configsRepository.findAll().size();
        configs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfigs() throws Exception {
        int databaseSizeBeforeUpdate = configsRepository.findAll().size();
        configs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Configs in the database
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfigs() throws Exception {
        // Initialize the database
        configsRepository.saveAndFlush(configs);

        int databaseSizeBeforeDelete = configsRepository.findAll().size();

        // Delete the configs
        restConfigsMockMvc
            .perform(delete(ENTITY_API_URL_ID, configs.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Configs> configsList = configsRepository.findAll();
        assertThat(configsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
