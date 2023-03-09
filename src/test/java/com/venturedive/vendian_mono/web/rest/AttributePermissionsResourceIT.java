package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.AttributePermissions;
import com.venturedive.vendian_mono.repository.AttributePermissionsRepository;
import com.venturedive.vendian_mono.service.criteria.AttributePermissionsCriteria;
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
 * Integration tests for the {@link AttributePermissionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributePermissionsResourceIT {

    private static final String DEFAULT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_ROUTE = "AAAAAAAAAA";
    private static final String UPDATED_ROUTE = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSEPERMISSIONS = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSEPERMISSIONS = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTPERMISSIONS = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTPERMISSIONS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/attribute-permissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributePermissionsRepository attributePermissionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributePermissionsMockMvc;

    private AttributePermissions attributePermissions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributePermissions createEntity(EntityManager em) {
        AttributePermissions attributePermissions = new AttributePermissions()
            .method(DEFAULT_METHOD)
            .route(DEFAULT_ROUTE)
            .responsepermissions(DEFAULT_RESPONSEPERMISSIONS)
            .requestpermissions(DEFAULT_REQUESTPERMISSIONS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return attributePermissions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributePermissions createUpdatedEntity(EntityManager em) {
        AttributePermissions attributePermissions = new AttributePermissions()
            .method(UPDATED_METHOD)
            .route(UPDATED_ROUTE)
            .responsepermissions(UPDATED_RESPONSEPERMISSIONS)
            .requestpermissions(UPDATED_REQUESTPERMISSIONS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return attributePermissions;
    }

    @BeforeEach
    public void initTest() {
        attributePermissions = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributePermissions() throws Exception {
        int databaseSizeBeforeCreate = attributePermissionsRepository.findAll().size();
        // Create the AttributePermissions
        restAttributePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isCreated());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeCreate + 1);
        AttributePermissions testAttributePermissions = attributePermissionsList.get(attributePermissionsList.size() - 1);
        assertThat(testAttributePermissions.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testAttributePermissions.getRoute()).isEqualTo(DEFAULT_ROUTE);
        assertThat(testAttributePermissions.getResponsepermissions()).isEqualTo(DEFAULT_RESPONSEPERMISSIONS);
        assertThat(testAttributePermissions.getRequestpermissions()).isEqualTo(DEFAULT_REQUESTPERMISSIONS);
        assertThat(testAttributePermissions.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testAttributePermissions.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createAttributePermissionsWithExistingId() throws Exception {
        // Create the AttributePermissions with an existing ID
        attributePermissions.setId(1L);

        int databaseSizeBeforeCreate = attributePermissionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributePermissionsRepository.findAll().size();
        // set the field null
        attributePermissions.setMethod(null);

        // Create the AttributePermissions, which fails.

        restAttributePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRouteIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributePermissionsRepository.findAll().size();
        // set the field null
        attributePermissions.setRoute(null);

        // Create the AttributePermissions, which fails.

        restAttributePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributePermissionsRepository.findAll().size();
        // set the field null
        attributePermissions.setCreatedat(null);

        // Create the AttributePermissions, which fails.

        restAttributePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributePermissionsRepository.findAll().size();
        // set the field null
        attributePermissions.setUpdatedat(null);

        // Create the AttributePermissions, which fails.

        restAttributePermissionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttributePermissions() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList
        restAttributePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributePermissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD)))
            .andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE)))
            .andExpect(jsonPath("$.[*].responsepermissions").value(hasItem(DEFAULT_RESPONSEPERMISSIONS)))
            .andExpect(jsonPath("$.[*].requestpermissions").value(hasItem(DEFAULT_REQUESTPERMISSIONS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getAttributePermissions() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get the attributePermissions
        restAttributePermissionsMockMvc
            .perform(get(ENTITY_API_URL_ID, attributePermissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributePermissions.getId().intValue()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD))
            .andExpect(jsonPath("$.route").value(DEFAULT_ROUTE))
            .andExpect(jsonPath("$.responsepermissions").value(DEFAULT_RESPONSEPERMISSIONS))
            .andExpect(jsonPath("$.requestpermissions").value(DEFAULT_REQUESTPERMISSIONS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getAttributePermissionsByIdFiltering() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        Long id = attributePermissions.getId();

        defaultAttributePermissionsShouldBeFound("id.equals=" + id);
        defaultAttributePermissionsShouldNotBeFound("id.notEquals=" + id);

        defaultAttributePermissionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttributePermissionsShouldNotBeFound("id.greaterThan=" + id);

        defaultAttributePermissionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttributePermissionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where method equals to DEFAULT_METHOD
        defaultAttributePermissionsShouldBeFound("method.equals=" + DEFAULT_METHOD);

        // Get all the attributePermissionsList where method equals to UPDATED_METHOD
        defaultAttributePermissionsShouldNotBeFound("method.equals=" + UPDATED_METHOD);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByMethodIsInShouldWork() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where method in DEFAULT_METHOD or UPDATED_METHOD
        defaultAttributePermissionsShouldBeFound("method.in=" + DEFAULT_METHOD + "," + UPDATED_METHOD);

        // Get all the attributePermissionsList where method equals to UPDATED_METHOD
        defaultAttributePermissionsShouldNotBeFound("method.in=" + UPDATED_METHOD);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where method is not null
        defaultAttributePermissionsShouldBeFound("method.specified=true");

        // Get all the attributePermissionsList where method is null
        defaultAttributePermissionsShouldNotBeFound("method.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByMethodContainsSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where method contains DEFAULT_METHOD
        defaultAttributePermissionsShouldBeFound("method.contains=" + DEFAULT_METHOD);

        // Get all the attributePermissionsList where method contains UPDATED_METHOD
        defaultAttributePermissionsShouldNotBeFound("method.contains=" + UPDATED_METHOD);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByMethodNotContainsSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where method does not contain DEFAULT_METHOD
        defaultAttributePermissionsShouldNotBeFound("method.doesNotContain=" + DEFAULT_METHOD);

        // Get all the attributePermissionsList where method does not contain UPDATED_METHOD
        defaultAttributePermissionsShouldBeFound("method.doesNotContain=" + UPDATED_METHOD);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRouteIsEqualToSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where route equals to DEFAULT_ROUTE
        defaultAttributePermissionsShouldBeFound("route.equals=" + DEFAULT_ROUTE);

        // Get all the attributePermissionsList where route equals to UPDATED_ROUTE
        defaultAttributePermissionsShouldNotBeFound("route.equals=" + UPDATED_ROUTE);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRouteIsInShouldWork() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where route in DEFAULT_ROUTE or UPDATED_ROUTE
        defaultAttributePermissionsShouldBeFound("route.in=" + DEFAULT_ROUTE + "," + UPDATED_ROUTE);

        // Get all the attributePermissionsList where route equals to UPDATED_ROUTE
        defaultAttributePermissionsShouldNotBeFound("route.in=" + UPDATED_ROUTE);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRouteIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where route is not null
        defaultAttributePermissionsShouldBeFound("route.specified=true");

        // Get all the attributePermissionsList where route is null
        defaultAttributePermissionsShouldNotBeFound("route.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRouteContainsSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where route contains DEFAULT_ROUTE
        defaultAttributePermissionsShouldBeFound("route.contains=" + DEFAULT_ROUTE);

        // Get all the attributePermissionsList where route contains UPDATED_ROUTE
        defaultAttributePermissionsShouldNotBeFound("route.contains=" + UPDATED_ROUTE);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRouteNotContainsSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where route does not contain DEFAULT_ROUTE
        defaultAttributePermissionsShouldNotBeFound("route.doesNotContain=" + DEFAULT_ROUTE);

        // Get all the attributePermissionsList where route does not contain UPDATED_ROUTE
        defaultAttributePermissionsShouldBeFound("route.doesNotContain=" + UPDATED_ROUTE);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByResponsepermissionsIsEqualToSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where responsepermissions equals to DEFAULT_RESPONSEPERMISSIONS
        defaultAttributePermissionsShouldBeFound("responsepermissions.equals=" + DEFAULT_RESPONSEPERMISSIONS);

        // Get all the attributePermissionsList where responsepermissions equals to UPDATED_RESPONSEPERMISSIONS
        defaultAttributePermissionsShouldNotBeFound("responsepermissions.equals=" + UPDATED_RESPONSEPERMISSIONS);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByResponsepermissionsIsInShouldWork() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where responsepermissions in DEFAULT_RESPONSEPERMISSIONS or UPDATED_RESPONSEPERMISSIONS
        defaultAttributePermissionsShouldBeFound(
            "responsepermissions.in=" + DEFAULT_RESPONSEPERMISSIONS + "," + UPDATED_RESPONSEPERMISSIONS
        );

        // Get all the attributePermissionsList where responsepermissions equals to UPDATED_RESPONSEPERMISSIONS
        defaultAttributePermissionsShouldNotBeFound("responsepermissions.in=" + UPDATED_RESPONSEPERMISSIONS);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByResponsepermissionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where responsepermissions is not null
        defaultAttributePermissionsShouldBeFound("responsepermissions.specified=true");

        // Get all the attributePermissionsList where responsepermissions is null
        defaultAttributePermissionsShouldNotBeFound("responsepermissions.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByResponsepermissionsContainsSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where responsepermissions contains DEFAULT_RESPONSEPERMISSIONS
        defaultAttributePermissionsShouldBeFound("responsepermissions.contains=" + DEFAULT_RESPONSEPERMISSIONS);

        // Get all the attributePermissionsList where responsepermissions contains UPDATED_RESPONSEPERMISSIONS
        defaultAttributePermissionsShouldNotBeFound("responsepermissions.contains=" + UPDATED_RESPONSEPERMISSIONS);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByResponsepermissionsNotContainsSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where responsepermissions does not contain DEFAULT_RESPONSEPERMISSIONS
        defaultAttributePermissionsShouldNotBeFound("responsepermissions.doesNotContain=" + DEFAULT_RESPONSEPERMISSIONS);

        // Get all the attributePermissionsList where responsepermissions does not contain UPDATED_RESPONSEPERMISSIONS
        defaultAttributePermissionsShouldBeFound("responsepermissions.doesNotContain=" + UPDATED_RESPONSEPERMISSIONS);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRequestpermissionsIsEqualToSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where requestpermissions equals to DEFAULT_REQUESTPERMISSIONS
        defaultAttributePermissionsShouldBeFound("requestpermissions.equals=" + DEFAULT_REQUESTPERMISSIONS);

        // Get all the attributePermissionsList where requestpermissions equals to UPDATED_REQUESTPERMISSIONS
        defaultAttributePermissionsShouldNotBeFound("requestpermissions.equals=" + UPDATED_REQUESTPERMISSIONS);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRequestpermissionsIsInShouldWork() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where requestpermissions in DEFAULT_REQUESTPERMISSIONS or UPDATED_REQUESTPERMISSIONS
        defaultAttributePermissionsShouldBeFound("requestpermissions.in=" + DEFAULT_REQUESTPERMISSIONS + "," + UPDATED_REQUESTPERMISSIONS);

        // Get all the attributePermissionsList where requestpermissions equals to UPDATED_REQUESTPERMISSIONS
        defaultAttributePermissionsShouldNotBeFound("requestpermissions.in=" + UPDATED_REQUESTPERMISSIONS);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRequestpermissionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where requestpermissions is not null
        defaultAttributePermissionsShouldBeFound("requestpermissions.specified=true");

        // Get all the attributePermissionsList where requestpermissions is null
        defaultAttributePermissionsShouldNotBeFound("requestpermissions.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRequestpermissionsContainsSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where requestpermissions contains DEFAULT_REQUESTPERMISSIONS
        defaultAttributePermissionsShouldBeFound("requestpermissions.contains=" + DEFAULT_REQUESTPERMISSIONS);

        // Get all the attributePermissionsList where requestpermissions contains UPDATED_REQUESTPERMISSIONS
        defaultAttributePermissionsShouldNotBeFound("requestpermissions.contains=" + UPDATED_REQUESTPERMISSIONS);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByRequestpermissionsNotContainsSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where requestpermissions does not contain DEFAULT_REQUESTPERMISSIONS
        defaultAttributePermissionsShouldNotBeFound("requestpermissions.doesNotContain=" + DEFAULT_REQUESTPERMISSIONS);

        // Get all the attributePermissionsList where requestpermissions does not contain UPDATED_REQUESTPERMISSIONS
        defaultAttributePermissionsShouldBeFound("requestpermissions.doesNotContain=" + UPDATED_REQUESTPERMISSIONS);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where createdat equals to DEFAULT_CREATEDAT
        defaultAttributePermissionsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the attributePermissionsList where createdat equals to UPDATED_CREATEDAT
        defaultAttributePermissionsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultAttributePermissionsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the attributePermissionsList where createdat equals to UPDATED_CREATEDAT
        defaultAttributePermissionsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where createdat is not null
        defaultAttributePermissionsShouldBeFound("createdat.specified=true");

        // Get all the attributePermissionsList where createdat is null
        defaultAttributePermissionsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultAttributePermissionsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the attributePermissionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultAttributePermissionsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultAttributePermissionsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the attributePermissionsList where updatedat equals to UPDATED_UPDATEDAT
        defaultAttributePermissionsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllAttributePermissionsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        // Get all the attributePermissionsList where updatedat is not null
        defaultAttributePermissionsShouldBeFound("updatedat.specified=true");

        // Get all the attributePermissionsList where updatedat is null
        defaultAttributePermissionsShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttributePermissionsShouldBeFound(String filter) throws Exception {
        restAttributePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributePermissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD)))
            .andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE)))
            .andExpect(jsonPath("$.[*].responsepermissions").value(hasItem(DEFAULT_RESPONSEPERMISSIONS)))
            .andExpect(jsonPath("$.[*].requestpermissions").value(hasItem(DEFAULT_REQUESTPERMISSIONS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restAttributePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttributePermissionsShouldNotBeFound(String filter) throws Exception {
        restAttributePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttributePermissionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAttributePermissions() throws Exception {
        // Get the attributePermissions
        restAttributePermissionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttributePermissions() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();

        // Update the attributePermissions
        AttributePermissions updatedAttributePermissions = attributePermissionsRepository.findById(attributePermissions.getId()).get();
        // Disconnect from session so that the updates on updatedAttributePermissions are not directly saved in db
        em.detach(updatedAttributePermissions);
        updatedAttributePermissions
            .method(UPDATED_METHOD)
            .route(UPDATED_ROUTE)
            .responsepermissions(UPDATED_RESPONSEPERMISSIONS)
            .requestpermissions(UPDATED_REQUESTPERMISSIONS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restAttributePermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAttributePermissions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAttributePermissions))
            )
            .andExpect(status().isOk());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
        AttributePermissions testAttributePermissions = attributePermissionsList.get(attributePermissionsList.size() - 1);
        assertThat(testAttributePermissions.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testAttributePermissions.getRoute()).isEqualTo(UPDATED_ROUTE);
        assertThat(testAttributePermissions.getResponsepermissions()).isEqualTo(UPDATED_RESPONSEPERMISSIONS);
        assertThat(testAttributePermissions.getRequestpermissions()).isEqualTo(UPDATED_REQUESTPERMISSIONS);
        assertThat(testAttributePermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testAttributePermissions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingAttributePermissions() throws Exception {
        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();
        attributePermissions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributePermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributePermissions.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributePermissions() throws Exception {
        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();
        attributePermissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributePermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributePermissions() throws Exception {
        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();
        attributePermissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributePermissionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributePermissionsWithPatch() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();

        // Update the attributePermissions using partial update
        AttributePermissions partialUpdatedAttributePermissions = new AttributePermissions();
        partialUpdatedAttributePermissions.setId(attributePermissions.getId());

        partialUpdatedAttributePermissions
            .responsepermissions(UPDATED_RESPONSEPERMISSIONS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restAttributePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributePermissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributePermissions))
            )
            .andExpect(status().isOk());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
        AttributePermissions testAttributePermissions = attributePermissionsList.get(attributePermissionsList.size() - 1);
        assertThat(testAttributePermissions.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testAttributePermissions.getRoute()).isEqualTo(DEFAULT_ROUTE);
        assertThat(testAttributePermissions.getResponsepermissions()).isEqualTo(UPDATED_RESPONSEPERMISSIONS);
        assertThat(testAttributePermissions.getRequestpermissions()).isEqualTo(DEFAULT_REQUESTPERMISSIONS);
        assertThat(testAttributePermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testAttributePermissions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateAttributePermissionsWithPatch() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();

        // Update the attributePermissions using partial update
        AttributePermissions partialUpdatedAttributePermissions = new AttributePermissions();
        partialUpdatedAttributePermissions.setId(attributePermissions.getId());

        partialUpdatedAttributePermissions
            .method(UPDATED_METHOD)
            .route(UPDATED_ROUTE)
            .responsepermissions(UPDATED_RESPONSEPERMISSIONS)
            .requestpermissions(UPDATED_REQUESTPERMISSIONS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restAttributePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributePermissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributePermissions))
            )
            .andExpect(status().isOk());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
        AttributePermissions testAttributePermissions = attributePermissionsList.get(attributePermissionsList.size() - 1);
        assertThat(testAttributePermissions.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testAttributePermissions.getRoute()).isEqualTo(UPDATED_ROUTE);
        assertThat(testAttributePermissions.getResponsepermissions()).isEqualTo(UPDATED_RESPONSEPERMISSIONS);
        assertThat(testAttributePermissions.getRequestpermissions()).isEqualTo(UPDATED_REQUESTPERMISSIONS);
        assertThat(testAttributePermissions.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testAttributePermissions.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingAttributePermissions() throws Exception {
        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();
        attributePermissions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributePermissions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributePermissions() throws Exception {
        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();
        attributePermissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributePermissions() throws Exception {
        int databaseSizeBeforeUpdate = attributePermissionsRepository.findAll().size();
        attributePermissions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributePermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributePermissions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributePermissions in the database
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributePermissions() throws Exception {
        // Initialize the database
        attributePermissionsRepository.saveAndFlush(attributePermissions);

        int databaseSizeBeforeDelete = attributePermissionsRepository.findAll().size();

        // Delete the attributePermissions
        restAttributePermissionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributePermissions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributePermissions> attributePermissionsList = attributePermissionsRepository.findAll();
        assertThat(attributePermissionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
