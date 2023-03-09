package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import com.venturedive.vendian_mono.domain.DealResourceEventLogs;
import com.venturedive.vendian_mono.domain.DealResourceStatus;
import com.venturedive.vendian_mono.repository.DealResourceEventLogsRepository;
import com.venturedive.vendian_mono.service.DealResourceEventLogsService;
import com.venturedive.vendian_mono.service.criteria.DealResourceEventLogsCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DealResourceEventLogsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DealResourceEventLogsResourceIT {

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/deal-resource-event-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealResourceEventLogsRepository dealResourceEventLogsRepository;

    @Mock
    private DealResourceEventLogsRepository dealResourceEventLogsRepositoryMock;

    @Mock
    private DealResourceEventLogsService dealResourceEventLogsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealResourceEventLogsMockMvc;

    private DealResourceEventLogs dealResourceEventLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealResourceEventLogs createEntity(EntityManager em) {
        DealResourceEventLogs dealResourceEventLogs = new DealResourceEventLogs().comments(DEFAULT_COMMENTS).createdat(DEFAULT_CREATEDAT);
        return dealResourceEventLogs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealResourceEventLogs createUpdatedEntity(EntityManager em) {
        DealResourceEventLogs dealResourceEventLogs = new DealResourceEventLogs().comments(UPDATED_COMMENTS).createdat(UPDATED_CREATEDAT);
        return dealResourceEventLogs;
    }

    @BeforeEach
    public void initTest() {
        dealResourceEventLogs = createEntity(em);
    }

    @Test
    @Transactional
    void createDealResourceEventLogs() throws Exception {
        int databaseSizeBeforeCreate = dealResourceEventLogsRepository.findAll().size();
        // Create the DealResourceEventLogs
        restDealResourceEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isCreated());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeCreate + 1);
        DealResourceEventLogs testDealResourceEventLogs = dealResourceEventLogsList.get(dealResourceEventLogsList.size() - 1);
        assertThat(testDealResourceEventLogs.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testDealResourceEventLogs.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
    }

    @Test
    @Transactional
    void createDealResourceEventLogsWithExistingId() throws Exception {
        // Create the DealResourceEventLogs with an existing ID
        dealResourceEventLogs.setId(1L);

        int databaseSizeBeforeCreate = dealResourceEventLogsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealResourceEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCommentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealResourceEventLogsRepository.findAll().size();
        // set the field null
        dealResourceEventLogs.setComments(null);

        // Create the DealResourceEventLogs, which fails.

        restDealResourceEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isBadRequest());

        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealResourceEventLogsRepository.findAll().size();
        // set the field null
        dealResourceEventLogs.setCreatedat(null);

        // Create the DealResourceEventLogs, which fails.

        restDealResourceEventLogsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isBadRequest());

        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogs() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList
        restDealResourceEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealResourceEventLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDealResourceEventLogsWithEagerRelationshipsIsEnabled() throws Exception {
        when(dealResourceEventLogsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDealResourceEventLogsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dealResourceEventLogsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDealResourceEventLogsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dealResourceEventLogsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDealResourceEventLogsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dealResourceEventLogsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDealResourceEventLogs() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get the dealResourceEventLogs
        restDealResourceEventLogsMockMvc
            .perform(get(ENTITY_API_URL_ID, dealResourceEventLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealResourceEventLogs.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()));
    }

    @Test
    @Transactional
    void getDealResourceEventLogsByIdFiltering() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        Long id = dealResourceEventLogs.getId();

        defaultDealResourceEventLogsShouldBeFound("id.equals=" + id);
        defaultDealResourceEventLogsShouldNotBeFound("id.notEquals=" + id);

        defaultDealResourceEventLogsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealResourceEventLogsShouldNotBeFound("id.greaterThan=" + id);

        defaultDealResourceEventLogsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealResourceEventLogsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList where comments equals to DEFAULT_COMMENTS
        defaultDealResourceEventLogsShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the dealResourceEventLogsList where comments equals to UPDATED_COMMENTS
        defaultDealResourceEventLogsShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultDealResourceEventLogsShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the dealResourceEventLogsList where comments equals to UPDATED_COMMENTS
        defaultDealResourceEventLogsShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList where comments is not null
        defaultDealResourceEventLogsShouldBeFound("comments.specified=true");

        // Get all the dealResourceEventLogsList where comments is null
        defaultDealResourceEventLogsShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList where comments contains DEFAULT_COMMENTS
        defaultDealResourceEventLogsShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the dealResourceEventLogsList where comments contains UPDATED_COMMENTS
        defaultDealResourceEventLogsShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList where comments does not contain DEFAULT_COMMENTS
        defaultDealResourceEventLogsShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the dealResourceEventLogsList where comments does not contain UPDATED_COMMENTS
        defaultDealResourceEventLogsShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList where createdat equals to DEFAULT_CREATEDAT
        defaultDealResourceEventLogsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the dealResourceEventLogsList where createdat equals to UPDATED_CREATEDAT
        defaultDealResourceEventLogsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDealResourceEventLogsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the dealResourceEventLogsList where createdat equals to UPDATED_CREATEDAT
        defaultDealResourceEventLogsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        // Get all the dealResourceEventLogsList where createdat is not null
        defaultDealResourceEventLogsShouldBeFound("createdat.specified=true");

        // Get all the dealResourceEventLogsList where createdat is null
        defaultDealResourceEventLogsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByMatchingresourceIsEqualToSomething() throws Exception {
        DealRequirementMatchingResources matchingresource;
        if (TestUtil.findAll(em, DealRequirementMatchingResources.class).isEmpty()) {
            dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);
            matchingresource = DealRequirementMatchingResourcesResourceIT.createEntity(em);
        } else {
            matchingresource = TestUtil.findAll(em, DealRequirementMatchingResources.class).get(0);
        }
        em.persist(matchingresource);
        em.flush();
        dealResourceEventLogs.setMatchingresource(matchingresource);
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);
        Long matchingresourceId = matchingresource.getId();

        // Get all the dealResourceEventLogsList where matchingresource equals to matchingresourceId
        defaultDealResourceEventLogsShouldBeFound("matchingresourceId.equals=" + matchingresourceId);

        // Get all the dealResourceEventLogsList where matchingresource equals to (matchingresourceId + 1)
        defaultDealResourceEventLogsShouldNotBeFound("matchingresourceId.equals=" + (matchingresourceId + 1));
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsByResourcestatusIsEqualToSomething() throws Exception {
        DealResourceStatus resourcestatus;
        if (TestUtil.findAll(em, DealResourceStatus.class).isEmpty()) {
            dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);
            resourcestatus = DealResourceStatusResourceIT.createEntity(em);
        } else {
            resourcestatus = TestUtil.findAll(em, DealResourceStatus.class).get(0);
        }
        em.persist(resourcestatus);
        em.flush();
        dealResourceEventLogs.setResourcestatus(resourcestatus);
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);
        Long resourcestatusId = resourcestatus.getId();

        // Get all the dealResourceEventLogsList where resourcestatus equals to resourcestatusId
        defaultDealResourceEventLogsShouldBeFound("resourcestatusId.equals=" + resourcestatusId);

        // Get all the dealResourceEventLogsList where resourcestatus equals to (resourcestatusId + 1)
        defaultDealResourceEventLogsShouldNotBeFound("resourcestatusId.equals=" + (resourcestatusId + 1));
    }

    @Test
    @Transactional
    void getAllDealResourceEventLogsBySystemstatusIsEqualToSomething() throws Exception {
        DealResourceStatus systemstatus;
        if (TestUtil.findAll(em, DealResourceStatus.class).isEmpty()) {
            dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);
            systemstatus = DealResourceStatusResourceIT.createEntity(em);
        } else {
            systemstatus = TestUtil.findAll(em, DealResourceStatus.class).get(0);
        }
        em.persist(systemstatus);
        em.flush();
        dealResourceEventLogs.setSystemstatus(systemstatus);
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);
        Long systemstatusId = systemstatus.getId();

        // Get all the dealResourceEventLogsList where systemstatus equals to systemstatusId
        defaultDealResourceEventLogsShouldBeFound("systemstatusId.equals=" + systemstatusId);

        // Get all the dealResourceEventLogsList where systemstatus equals to (systemstatusId + 1)
        defaultDealResourceEventLogsShouldNotBeFound("systemstatusId.equals=" + (systemstatusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealResourceEventLogsShouldBeFound(String filter) throws Exception {
        restDealResourceEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealResourceEventLogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())));

        // Check, that the count call also returns 1
        restDealResourceEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealResourceEventLogsShouldNotBeFound(String filter) throws Exception {
        restDealResourceEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealResourceEventLogsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDealResourceEventLogs() throws Exception {
        // Get the dealResourceEventLogs
        restDealResourceEventLogsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDealResourceEventLogs() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();

        // Update the dealResourceEventLogs
        DealResourceEventLogs updatedDealResourceEventLogs = dealResourceEventLogsRepository.findById(dealResourceEventLogs.getId()).get();
        // Disconnect from session so that the updates on updatedDealResourceEventLogs are not directly saved in db
        em.detach(updatedDealResourceEventLogs);
        updatedDealResourceEventLogs.comments(UPDATED_COMMENTS).createdat(UPDATED_CREATEDAT);

        restDealResourceEventLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealResourceEventLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealResourceEventLogs))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
        DealResourceEventLogs testDealResourceEventLogs = dealResourceEventLogsList.get(dealResourceEventLogsList.size() - 1);
        assertThat(testDealResourceEventLogs.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testDealResourceEventLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDealResourceEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();
        dealResourceEventLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealResourceEventLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealResourceEventLogs.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealResourceEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();
        dealResourceEventLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceEventLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealResourceEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();
        dealResourceEventLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceEventLogsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealResourceEventLogsWithPatch() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();

        // Update the dealResourceEventLogs using partial update
        DealResourceEventLogs partialUpdatedDealResourceEventLogs = new DealResourceEventLogs();
        partialUpdatedDealResourceEventLogs.setId(dealResourceEventLogs.getId());

        partialUpdatedDealResourceEventLogs.createdat(UPDATED_CREATEDAT);

        restDealResourceEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealResourceEventLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealResourceEventLogs))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
        DealResourceEventLogs testDealResourceEventLogs = dealResourceEventLogsList.get(dealResourceEventLogsList.size() - 1);
        assertThat(testDealResourceEventLogs.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testDealResourceEventLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDealResourceEventLogsWithPatch() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();

        // Update the dealResourceEventLogs using partial update
        DealResourceEventLogs partialUpdatedDealResourceEventLogs = new DealResourceEventLogs();
        partialUpdatedDealResourceEventLogs.setId(dealResourceEventLogs.getId());

        partialUpdatedDealResourceEventLogs.comments(UPDATED_COMMENTS).createdat(UPDATED_CREATEDAT);

        restDealResourceEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealResourceEventLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealResourceEventLogs))
            )
            .andExpect(status().isOk());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
        DealResourceEventLogs testDealResourceEventLogs = dealResourceEventLogsList.get(dealResourceEventLogsList.size() - 1);
        assertThat(testDealResourceEventLogs.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testDealResourceEventLogs.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDealResourceEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();
        dealResourceEventLogs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealResourceEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealResourceEventLogs.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealResourceEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();
        dealResourceEventLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealResourceEventLogs() throws Exception {
        int databaseSizeBeforeUpdate = dealResourceEventLogsRepository.findAll().size();
        dealResourceEventLogs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealResourceEventLogsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealResourceEventLogs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealResourceEventLogs in the database
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealResourceEventLogs() throws Exception {
        // Initialize the database
        dealResourceEventLogsRepository.saveAndFlush(dealResourceEventLogs);

        int databaseSizeBeforeDelete = dealResourceEventLogsRepository.findAll().size();

        // Delete the dealResourceEventLogs
        restDealResourceEventLogsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealResourceEventLogs.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealResourceEventLogs> dealResourceEventLogsList = dealResourceEventLogsRepository.findAll();
        assertThat(dealResourceEventLogsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
