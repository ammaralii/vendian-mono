package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.RaterAttributeMappings;
import com.venturedive.vendian_mono.domain.RaterAttributes;
import com.venturedive.vendian_mono.repository.RaterAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.RaterAttributesCriteria;
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
 * Integration tests for the {@link RaterAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RaterAttributesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_EFFDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFFDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/rater-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RaterAttributesRepository raterAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRaterAttributesMockMvc;

    private RaterAttributes raterAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RaterAttributes createEntity(EntityManager em) {
        RaterAttributes raterAttributes = new RaterAttributes()
            .name(DEFAULT_NAME)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .effdate(DEFAULT_EFFDATE)
            .enddate(DEFAULT_ENDDATE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return raterAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RaterAttributes createUpdatedEntity(EntityManager em) {
        RaterAttributes raterAttributes = new RaterAttributes()
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .effdate(UPDATED_EFFDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return raterAttributes;
    }

    @BeforeEach
    public void initTest() {
        raterAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createRaterAttributes() throws Exception {
        int databaseSizeBeforeCreate = raterAttributesRepository.findAll().size();
        // Create the RaterAttributes
        restRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        RaterAttributes testRaterAttributes = raterAttributesList.get(raterAttributesList.size() - 1);
        assertThat(testRaterAttributes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRaterAttributes.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRaterAttributes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRaterAttributes.getEffdate()).isEqualTo(DEFAULT_EFFDATE);
        assertThat(testRaterAttributes.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testRaterAttributes.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRaterAttributes.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createRaterAttributesWithExistingId() throws Exception {
        // Create the RaterAttributes with an existing ID
        raterAttributes.setId(1L);

        int databaseSizeBeforeCreate = raterAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = raterAttributesRepository.findAll().size();
        // set the field null
        raterAttributes.setCreatedat(null);

        // Create the RaterAttributes, which fails.

        restRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isBadRequest());

        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = raterAttributesRepository.findAll().size();
        // set the field null
        raterAttributes.setUpdatedat(null);

        // Create the RaterAttributes, which fails.

        restRaterAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isBadRequest());

        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRaterAttributes() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList
        restRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raterAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].effdate").value(hasItem(DEFAULT_EFFDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getRaterAttributes() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get the raterAttributes
        restRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, raterAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(raterAttributes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.effdate").value(DEFAULT_EFFDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getRaterAttributesByIdFiltering() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        Long id = raterAttributes.getId();

        defaultRaterAttributesShouldBeFound("id.equals=" + id);
        defaultRaterAttributesShouldNotBeFound("id.notEquals=" + id);

        defaultRaterAttributesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRaterAttributesShouldNotBeFound("id.greaterThan=" + id);

        defaultRaterAttributesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRaterAttributesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where name equals to DEFAULT_NAME
        defaultRaterAttributesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the raterAttributesList where name equals to UPDATED_NAME
        defaultRaterAttributesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRaterAttributesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the raterAttributesList where name equals to UPDATED_NAME
        defaultRaterAttributesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where name is not null
        defaultRaterAttributesShouldBeFound("name.specified=true");

        // Get all the raterAttributesList where name is null
        defaultRaterAttributesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributesByNameContainsSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where name contains DEFAULT_NAME
        defaultRaterAttributesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the raterAttributesList where name contains UPDATED_NAME
        defaultRaterAttributesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where name does not contain DEFAULT_NAME
        defaultRaterAttributesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the raterAttributesList where name does not contain UPDATED_NAME
        defaultRaterAttributesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where title equals to DEFAULT_TITLE
        defaultRaterAttributesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the raterAttributesList where title equals to UPDATED_TITLE
        defaultRaterAttributesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultRaterAttributesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the raterAttributesList where title equals to UPDATED_TITLE
        defaultRaterAttributesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where title is not null
        defaultRaterAttributesShouldBeFound("title.specified=true");

        // Get all the raterAttributesList where title is null
        defaultRaterAttributesShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributesByTitleContainsSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where title contains DEFAULT_TITLE
        defaultRaterAttributesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the raterAttributesList where title contains UPDATED_TITLE
        defaultRaterAttributesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where title does not contain DEFAULT_TITLE
        defaultRaterAttributesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the raterAttributesList where title does not contain UPDATED_TITLE
        defaultRaterAttributesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where description equals to DEFAULT_DESCRIPTION
        defaultRaterAttributesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the raterAttributesList where description equals to UPDATED_DESCRIPTION
        defaultRaterAttributesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRaterAttributesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the raterAttributesList where description equals to UPDATED_DESCRIPTION
        defaultRaterAttributesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where description is not null
        defaultRaterAttributesShouldBeFound("description.specified=true");

        // Get all the raterAttributesList where description is null
        defaultRaterAttributesShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where description contains DEFAULT_DESCRIPTION
        defaultRaterAttributesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the raterAttributesList where description contains UPDATED_DESCRIPTION
        defaultRaterAttributesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where description does not contain DEFAULT_DESCRIPTION
        defaultRaterAttributesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the raterAttributesList where description does not contain UPDATED_DESCRIPTION
        defaultRaterAttributesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByEffdateIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where effdate equals to DEFAULT_EFFDATE
        defaultRaterAttributesShouldBeFound("effdate.equals=" + DEFAULT_EFFDATE);

        // Get all the raterAttributesList where effdate equals to UPDATED_EFFDATE
        defaultRaterAttributesShouldNotBeFound("effdate.equals=" + UPDATED_EFFDATE);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByEffdateIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where effdate in DEFAULT_EFFDATE or UPDATED_EFFDATE
        defaultRaterAttributesShouldBeFound("effdate.in=" + DEFAULT_EFFDATE + "," + UPDATED_EFFDATE);

        // Get all the raterAttributesList where effdate equals to UPDATED_EFFDATE
        defaultRaterAttributesShouldNotBeFound("effdate.in=" + UPDATED_EFFDATE);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByEffdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where effdate is not null
        defaultRaterAttributesShouldBeFound("effdate.specified=true");

        // Get all the raterAttributesList where effdate is null
        defaultRaterAttributesShouldNotBeFound("effdate.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributesByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where enddate equals to DEFAULT_ENDDATE
        defaultRaterAttributesShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the raterAttributesList where enddate equals to UPDATED_ENDDATE
        defaultRaterAttributesShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultRaterAttributesShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the raterAttributesList where enddate equals to UPDATED_ENDDATE
        defaultRaterAttributesShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where enddate is not null
        defaultRaterAttributesShouldBeFound("enddate.specified=true");

        // Get all the raterAttributesList where enddate is null
        defaultRaterAttributesShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where createdat equals to DEFAULT_CREATEDAT
        defaultRaterAttributesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the raterAttributesList where createdat equals to UPDATED_CREATEDAT
        defaultRaterAttributesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultRaterAttributesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the raterAttributesList where createdat equals to UPDATED_CREATEDAT
        defaultRaterAttributesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where createdat is not null
        defaultRaterAttributesShouldBeFound("createdat.specified=true");

        // Get all the raterAttributesList where createdat is null
        defaultRaterAttributesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultRaterAttributesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the raterAttributesList where updatedat equals to UPDATED_UPDATEDAT
        defaultRaterAttributesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultRaterAttributesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the raterAttributesList where updatedat equals to UPDATED_UPDATEDAT
        defaultRaterAttributesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllRaterAttributesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        // Get all the raterAttributesList where updatedat is not null
        defaultRaterAttributesShouldBeFound("updatedat.specified=true");

        // Get all the raterAttributesList where updatedat is null
        defaultRaterAttributesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllRaterAttributesByRaterattributemappingsRaterattributeIsEqualToSomething() throws Exception {
        RaterAttributeMappings raterattributemappingsRaterattribute;
        if (TestUtil.findAll(em, RaterAttributeMappings.class).isEmpty()) {
            raterAttributesRepository.saveAndFlush(raterAttributes);
            raterattributemappingsRaterattribute = RaterAttributeMappingsResourceIT.createEntity(em);
        } else {
            raterattributemappingsRaterattribute = TestUtil.findAll(em, RaterAttributeMappings.class).get(0);
        }
        em.persist(raterattributemappingsRaterattribute);
        em.flush();
        raterAttributes.addRaterattributemappingsRaterattribute(raterattributemappingsRaterattribute);
        raterAttributesRepository.saveAndFlush(raterAttributes);
        Long raterattributemappingsRaterattributeId = raterattributemappingsRaterattribute.getId();

        // Get all the raterAttributesList where raterattributemappingsRaterattribute equals to raterattributemappingsRaterattributeId
        defaultRaterAttributesShouldBeFound("raterattributemappingsRaterattributeId.equals=" + raterattributemappingsRaterattributeId);

        // Get all the raterAttributesList where raterattributemappingsRaterattribute equals to (raterattributemappingsRaterattributeId + 1)
        defaultRaterAttributesShouldNotBeFound(
            "raterattributemappingsRaterattributeId.equals=" + (raterattributemappingsRaterattributeId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRaterAttributesShouldBeFound(String filter) throws Exception {
        restRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raterAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].effdate").value(hasItem(DEFAULT_EFFDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRaterAttributesShouldNotBeFound(String filter) throws Exception {
        restRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRaterAttributesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRaterAttributes() throws Exception {
        // Get the raterAttributes
        restRaterAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRaterAttributes() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();

        // Update the raterAttributes
        RaterAttributes updatedRaterAttributes = raterAttributesRepository.findById(raterAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedRaterAttributes are not directly saved in db
        em.detach(updatedRaterAttributes);
        updatedRaterAttributes
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .effdate(UPDATED_EFFDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRaterAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
        RaterAttributes testRaterAttributes = raterAttributesList.get(raterAttributesList.size() - 1);
        assertThat(testRaterAttributes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRaterAttributes.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRaterAttributes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRaterAttributes.getEffdate()).isEqualTo(UPDATED_EFFDATE);
        assertThat(testRaterAttributes.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testRaterAttributes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRaterAttributes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();
        raterAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raterAttributes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();
        raterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();
        raterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaterAttributesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRaterAttributesWithPatch() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();

        // Update the raterAttributes using partial update
        RaterAttributes partialUpdatedRaterAttributes = new RaterAttributes();
        partialUpdatedRaterAttributes.setId(raterAttributes.getId());

        partialUpdatedRaterAttributes
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .effdate(UPDATED_EFFDATE)
            .enddate(UPDATED_ENDDATE)
            .updatedat(UPDATED_UPDATEDAT);

        restRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
        RaterAttributes testRaterAttributes = raterAttributesList.get(raterAttributesList.size() - 1);
        assertThat(testRaterAttributes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRaterAttributes.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRaterAttributes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRaterAttributes.getEffdate()).isEqualTo(UPDATED_EFFDATE);
        assertThat(testRaterAttributes.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testRaterAttributes.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRaterAttributes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateRaterAttributesWithPatch() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();

        // Update the raterAttributes using partial update
        RaterAttributes partialUpdatedRaterAttributes = new RaterAttributes();
        partialUpdatedRaterAttributes.setId(raterAttributes.getId());

        partialUpdatedRaterAttributes
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .effdate(UPDATED_EFFDATE)
            .enddate(UPDATED_ENDDATE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaterAttributes))
            )
            .andExpect(status().isOk());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
        RaterAttributes testRaterAttributes = raterAttributesList.get(raterAttributesList.size() - 1);
        assertThat(testRaterAttributes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRaterAttributes.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRaterAttributes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRaterAttributes.getEffdate()).isEqualTo(UPDATED_EFFDATE);
        assertThat(testRaterAttributes.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testRaterAttributes.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRaterAttributes.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();
        raterAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, raterAttributes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();
        raterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRaterAttributes() throws Exception {
        int databaseSizeBeforeUpdate = raterAttributesRepository.findAll().size();
        raterAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaterAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raterAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RaterAttributes in the database
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRaterAttributes() throws Exception {
        // Initialize the database
        raterAttributesRepository.saveAndFlush(raterAttributes);

        int databaseSizeBeforeDelete = raterAttributesRepository.findAll().size();

        // Delete the raterAttributes
        restRaterAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, raterAttributes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RaterAttributes> raterAttributesList = raterAttributesRepository.findAll();
        assertThat(raterAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
