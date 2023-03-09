package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeaveRequestsOlds;
import com.venturedive.vendian_mono.domain.LeaveTypesOlds;
import com.venturedive.vendian_mono.repository.LeaveTypesOldsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypesOldsCriteria;
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
 * Integration tests for the {@link LeaveTypesOldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTypesOldsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/leave-types-olds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTypesOldsRepository leaveTypesOldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypesOldsMockMvc;

    private LeaveTypesOlds leaveTypesOlds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypesOlds createEntity(EntityManager em) {
        LeaveTypesOlds leaveTypesOlds = new LeaveTypesOlds()
            .name(DEFAULT_NAME)
            .isactive(DEFAULT_ISACTIVE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return leaveTypesOlds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveTypesOlds createUpdatedEntity(EntityManager em) {
        LeaveTypesOlds leaveTypesOlds = new LeaveTypesOlds()
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return leaveTypesOlds;
    }

    @BeforeEach
    public void initTest() {
        leaveTypesOlds = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveTypesOlds() throws Exception {
        int databaseSizeBeforeCreate = leaveTypesOldsRepository.findAll().size();
        // Create the LeaveTypesOlds
        restLeaveTypesOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveTypesOlds testLeaveTypesOlds = leaveTypesOldsList.get(leaveTypesOldsList.size() - 1);
        assertThat(testLeaveTypesOlds.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaveTypesOlds.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testLeaveTypesOlds.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testLeaveTypesOlds.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createLeaveTypesOldsWithExistingId() throws Exception {
        // Create the LeaveTypesOlds with an existing ID
        leaveTypesOlds.setId(1L);

        int databaseSizeBeforeCreate = leaveTypesOldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypesOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesOldsRepository.findAll().size();
        // set the field null
        leaveTypesOlds.setCreatedat(null);

        // Create the LeaveTypesOlds, which fails.

        restLeaveTypesOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypesOldsRepository.findAll().size();
        // set the field null
        leaveTypesOlds.setUpdatedat(null);

        // Create the LeaveTypesOlds, which fails.

        restLeaveTypesOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isBadRequest());

        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOlds() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList
        restLeaveTypesOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypesOlds.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getLeaveTypesOlds() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get the leaveTypesOlds
        restLeaveTypesOldsMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveTypesOlds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveTypesOlds.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getLeaveTypesOldsByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        Long id = leaveTypesOlds.getId();

        defaultLeaveTypesOldsShouldBeFound("id.equals=" + id);
        defaultLeaveTypesOldsShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypesOldsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypesOldsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypesOldsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypesOldsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where name equals to DEFAULT_NAME
        defaultLeaveTypesOldsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the leaveTypesOldsList where name equals to UPDATED_NAME
        defaultLeaveTypesOldsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLeaveTypesOldsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the leaveTypesOldsList where name equals to UPDATED_NAME
        defaultLeaveTypesOldsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where name is not null
        defaultLeaveTypesOldsShouldBeFound("name.specified=true");

        // Get all the leaveTypesOldsList where name is null
        defaultLeaveTypesOldsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByNameContainsSomething() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where name contains DEFAULT_NAME
        defaultLeaveTypesOldsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the leaveTypesOldsList where name contains UPDATED_NAME
        defaultLeaveTypesOldsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where name does not contain DEFAULT_NAME
        defaultLeaveTypesOldsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the leaveTypesOldsList where name does not contain UPDATED_NAME
        defaultLeaveTypesOldsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where isactive equals to DEFAULT_ISACTIVE
        defaultLeaveTypesOldsShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the leaveTypesOldsList where isactive equals to UPDATED_ISACTIVE
        defaultLeaveTypesOldsShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultLeaveTypesOldsShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the leaveTypesOldsList where isactive equals to UPDATED_ISACTIVE
        defaultLeaveTypesOldsShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where isactive is not null
        defaultLeaveTypesOldsShouldBeFound("isactive.specified=true");

        // Get all the leaveTypesOldsList where isactive is null
        defaultLeaveTypesOldsShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where createdat equals to DEFAULT_CREATEDAT
        defaultLeaveTypesOldsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the leaveTypesOldsList where createdat equals to UPDATED_CREATEDAT
        defaultLeaveTypesOldsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultLeaveTypesOldsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the leaveTypesOldsList where createdat equals to UPDATED_CREATEDAT
        defaultLeaveTypesOldsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where createdat is not null
        defaultLeaveTypesOldsShouldBeFound("createdat.specified=true");

        // Get all the leaveTypesOldsList where createdat is null
        defaultLeaveTypesOldsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultLeaveTypesOldsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the leaveTypesOldsList where updatedat equals to UPDATED_UPDATEDAT
        defaultLeaveTypesOldsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultLeaveTypesOldsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the leaveTypesOldsList where updatedat equals to UPDATED_UPDATEDAT
        defaultLeaveTypesOldsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        // Get all the leaveTypesOldsList where updatedat is not null
        defaultLeaveTypesOldsShouldBeFound("updatedat.specified=true");

        // Get all the leaveTypesOldsList where updatedat is null
        defaultLeaveTypesOldsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesOldsByLeaverequestsoldsLeavetypeIsEqualToSomething() throws Exception {
        LeaveRequestsOlds leaverequestsoldsLeavetype;
        if (TestUtil.findAll(em, LeaveRequestsOlds.class).isEmpty()) {
            leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);
            leaverequestsoldsLeavetype = LeaveRequestsOldsResourceIT.createEntity(em);
        } else {
            leaverequestsoldsLeavetype = TestUtil.findAll(em, LeaveRequestsOlds.class).get(0);
        }
        em.persist(leaverequestsoldsLeavetype);
        em.flush();
        leaveTypesOlds.addLeaverequestsoldsLeavetype(leaverequestsoldsLeavetype);
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);
        Long leaverequestsoldsLeavetypeId = leaverequestsoldsLeavetype.getId();

        // Get all the leaveTypesOldsList where leaverequestsoldsLeavetype equals to leaverequestsoldsLeavetypeId
        defaultLeaveTypesOldsShouldBeFound("leaverequestsoldsLeavetypeId.equals=" + leaverequestsoldsLeavetypeId);

        // Get all the leaveTypesOldsList where leaverequestsoldsLeavetype equals to (leaverequestsoldsLeavetypeId + 1)
        defaultLeaveTypesOldsShouldNotBeFound("leaverequestsoldsLeavetypeId.equals=" + (leaverequestsoldsLeavetypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypesOldsShouldBeFound(String filter) throws Exception {
        restLeaveTypesOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveTypesOlds.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restLeaveTypesOldsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypesOldsShouldNotBeFound(String filter) throws Exception {
        restLeaveTypesOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypesOldsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveTypesOlds() throws Exception {
        // Get the leaveTypesOlds
        restLeaveTypesOldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveTypesOlds() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();

        // Update the leaveTypesOlds
        LeaveTypesOlds updatedLeaveTypesOlds = leaveTypesOldsRepository.findById(leaveTypesOlds.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveTypesOlds are not directly saved in db
        em.detach(updatedLeaveTypesOlds);
        updatedLeaveTypesOlds.name(UPDATED_NAME).isactive(UPDATED_ISACTIVE).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restLeaveTypesOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeaveTypesOlds.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeaveTypesOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypesOlds testLeaveTypesOlds = leaveTypesOldsList.get(leaveTypesOldsList.size() - 1);
        assertThat(testLeaveTypesOlds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveTypesOlds.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testLeaveTypesOlds.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeaveTypesOlds.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingLeaveTypesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();
        leaveTypesOlds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypesOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypesOlds.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveTypesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();
        leaveTypesOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypesOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveTypesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();
        leaveTypesOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypesOldsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTypesOldsWithPatch() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();

        // Update the leaveTypesOlds using partial update
        LeaveTypesOlds partialUpdatedLeaveTypesOlds = new LeaveTypesOlds();
        partialUpdatedLeaveTypesOlds.setId(leaveTypesOlds.getId());

        partialUpdatedLeaveTypesOlds.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT);

        restLeaveTypesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypesOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypesOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypesOlds testLeaveTypesOlds = leaveTypesOldsList.get(leaveTypesOldsList.size() - 1);
        assertThat(testLeaveTypesOlds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveTypesOlds.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testLeaveTypesOlds.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeaveTypesOlds.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTypesOldsWithPatch() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();

        // Update the leaveTypesOlds using partial update
        LeaveTypesOlds partialUpdatedLeaveTypesOlds = new LeaveTypesOlds();
        partialUpdatedLeaveTypesOlds.setId(leaveTypesOlds.getId());

        partialUpdatedLeaveTypesOlds
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restLeaveTypesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveTypesOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveTypesOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
        LeaveTypesOlds testLeaveTypesOlds = leaveTypesOldsList.get(leaveTypesOldsList.size() - 1);
        assertThat(testLeaveTypesOlds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveTypesOlds.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testLeaveTypesOlds.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeaveTypesOlds.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveTypesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();
        leaveTypesOlds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTypesOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveTypesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();
        leaveTypesOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveTypesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypesOldsRepository.findAll().size();
        leaveTypesOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypesOlds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveTypesOlds in the database
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveTypesOlds() throws Exception {
        // Initialize the database
        leaveTypesOldsRepository.saveAndFlush(leaveTypesOlds);

        int databaseSizeBeforeDelete = leaveTypesOldsRepository.findAll().size();

        // Delete the leaveTypesOlds
        restLeaveTypesOldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveTypesOlds.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveTypesOlds> leaveTypesOldsList = leaveTypesOldsRepository.findAll();
        assertThat(leaveTypesOldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
