package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import com.venturedive.vendian_mono.domain.DealRequirements;
import com.venturedive.vendian_mono.domain.DealResourceEventLogs;
import com.venturedive.vendian_mono.domain.DealResourceStatus;
import com.venturedive.vendian_mono.domain.DealResources;
import com.venturedive.vendian_mono.repository.DealRequirementMatchingResourcesRepository;
import com.venturedive.vendian_mono.service.DealRequirementMatchingResourcesService;
import com.venturedive.vendian_mono.service.criteria.DealRequirementMatchingResourcesCriteria;
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
 * Integration tests for the {@link DealRequirementMatchingResourcesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DealRequirementMatchingResourcesResourceIT {

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/deal-requirement-matching-resources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealRequirementMatchingResourcesRepository dealRequirementMatchingResourcesRepository;

    @Mock
    private DealRequirementMatchingResourcesRepository dealRequirementMatchingResourcesRepositoryMock;

    @Mock
    private DealRequirementMatchingResourcesService dealRequirementMatchingResourcesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealRequirementMatchingResourcesMockMvc;

    private DealRequirementMatchingResources dealRequirementMatchingResources;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealRequirementMatchingResources createEntity(EntityManager em) {
        DealRequirementMatchingResources dealRequirementMatchingResources = new DealRequirementMatchingResources()
            .comments(DEFAULT_COMMENTS)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return dealRequirementMatchingResources;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealRequirementMatchingResources createUpdatedEntity(EntityManager em) {
        DealRequirementMatchingResources dealRequirementMatchingResources = new DealRequirementMatchingResources()
            .comments(UPDATED_COMMENTS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return dealRequirementMatchingResources;
    }

    @BeforeEach
    public void initTest() {
        dealRequirementMatchingResources = createEntity(em);
    }

    @Test
    @Transactional
    void createDealRequirementMatchingResources() throws Exception {
        int databaseSizeBeforeCreate = dealRequirementMatchingResourcesRepository.findAll().size();
        // Create the DealRequirementMatchingResources
        restDealRequirementMatchingResourcesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirementMatchingResources))
            )
            .andExpect(status().isCreated());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeCreate + 1);
        DealRequirementMatchingResources testDealRequirementMatchingResources = dealRequirementMatchingResourcesList.get(
            dealRequirementMatchingResourcesList.size() - 1
        );
        assertThat(testDealRequirementMatchingResources.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testDealRequirementMatchingResources.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDealRequirementMatchingResources.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testDealRequirementMatchingResources.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createDealRequirementMatchingResourcesWithExistingId() throws Exception {
        // Create the DealRequirementMatchingResources with an existing ID
        dealRequirementMatchingResources.setId(1L);

        int databaseSizeBeforeCreate = dealRequirementMatchingResourcesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealRequirementMatchingResourcesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirementMatchingResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResources() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList
        restDealRequirementMatchingResourcesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealRequirementMatchingResources.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDealRequirementMatchingResourcesWithEagerRelationshipsIsEnabled() throws Exception {
        when(dealRequirementMatchingResourcesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDealRequirementMatchingResourcesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dealRequirementMatchingResourcesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDealRequirementMatchingResourcesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dealRequirementMatchingResourcesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDealRequirementMatchingResourcesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dealRequirementMatchingResourcesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDealRequirementMatchingResources() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get the dealRequirementMatchingResources
        restDealRequirementMatchingResourcesMockMvc
            .perform(get(ENTITY_API_URL_ID, dealRequirementMatchingResources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealRequirementMatchingResources.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getDealRequirementMatchingResourcesByIdFiltering() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        Long id = dealRequirementMatchingResources.getId();

        defaultDealRequirementMatchingResourcesShouldBeFound("id.equals=" + id);
        defaultDealRequirementMatchingResourcesShouldNotBeFound("id.notEquals=" + id);

        defaultDealRequirementMatchingResourcesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealRequirementMatchingResourcesShouldNotBeFound("id.greaterThan=" + id);

        defaultDealRequirementMatchingResourcesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealRequirementMatchingResourcesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where comments equals to DEFAULT_COMMENTS
        defaultDealRequirementMatchingResourcesShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the dealRequirementMatchingResourcesList where comments equals to UPDATED_COMMENTS
        defaultDealRequirementMatchingResourcesShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultDealRequirementMatchingResourcesShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the dealRequirementMatchingResourcesList where comments equals to UPDATED_COMMENTS
        defaultDealRequirementMatchingResourcesShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where comments is not null
        defaultDealRequirementMatchingResourcesShouldBeFound("comments.specified=true");

        // Get all the dealRequirementMatchingResourcesList where comments is null
        defaultDealRequirementMatchingResourcesShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByCommentsContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where comments contains DEFAULT_COMMENTS
        defaultDealRequirementMatchingResourcesShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the dealRequirementMatchingResourcesList where comments contains UPDATED_COMMENTS
        defaultDealRequirementMatchingResourcesShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where comments does not contain DEFAULT_COMMENTS
        defaultDealRequirementMatchingResourcesShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the dealRequirementMatchingResourcesList where comments does not contain UPDATED_COMMENTS
        defaultDealRequirementMatchingResourcesShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where createdat equals to DEFAULT_CREATEDAT
        defaultDealRequirementMatchingResourcesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the dealRequirementMatchingResourcesList where createdat equals to UPDATED_CREATEDAT
        defaultDealRequirementMatchingResourcesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDealRequirementMatchingResourcesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the dealRequirementMatchingResourcesList where createdat equals to UPDATED_CREATEDAT
        defaultDealRequirementMatchingResourcesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where createdat is not null
        defaultDealRequirementMatchingResourcesShouldBeFound("createdat.specified=true");

        // Get all the dealRequirementMatchingResourcesList where createdat is null
        defaultDealRequirementMatchingResourcesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultDealRequirementMatchingResourcesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the dealRequirementMatchingResourcesList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealRequirementMatchingResourcesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultDealRequirementMatchingResourcesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the dealRequirementMatchingResourcesList where updatedat equals to UPDATED_UPDATEDAT
        defaultDealRequirementMatchingResourcesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where updatedat is not null
        defaultDealRequirementMatchingResourcesShouldBeFound("updatedat.specified=true");

        // Get all the dealRequirementMatchingResourcesList where updatedat is null
        defaultDealRequirementMatchingResourcesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where deletedat equals to DEFAULT_DELETEDAT
        defaultDealRequirementMatchingResourcesShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the dealRequirementMatchingResourcesList where deletedat equals to UPDATED_DELETEDAT
        defaultDealRequirementMatchingResourcesShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultDealRequirementMatchingResourcesShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the dealRequirementMatchingResourcesList where deletedat equals to UPDATED_DELETEDAT
        defaultDealRequirementMatchingResourcesShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        // Get all the dealRequirementMatchingResourcesList where deletedat is not null
        defaultDealRequirementMatchingResourcesShouldBeFound("deletedat.specified=true");

        // Get all the dealRequirementMatchingResourcesList where deletedat is null
        defaultDealRequirementMatchingResourcesShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByDealrequirementIsEqualToSomething() throws Exception {
        DealRequirements dealrequirement;
        if (TestUtil.findAll(em, DealRequirements.class).isEmpty()) {
            dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
            dealrequirement = DealRequirementsResourceIT.createEntity(em);
        } else {
            dealrequirement = TestUtil.findAll(em, DealRequirements.class).get(0);
        }
        em.persist(dealrequirement);
        em.flush();
        dealRequirementMatchingResources.setDealrequirement(dealrequirement);
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
        Long dealrequirementId = dealrequirement.getId();

        // Get all the dealRequirementMatchingResourcesList where dealrequirement equals to dealrequirementId
        defaultDealRequirementMatchingResourcesShouldBeFound("dealrequirementId.equals=" + dealrequirementId);

        // Get all the dealRequirementMatchingResourcesList where dealrequirement equals to (dealrequirementId + 1)
        defaultDealRequirementMatchingResourcesShouldNotBeFound("dealrequirementId.equals=" + (dealrequirementId + 1));
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByResourceIsEqualToSomething() throws Exception {
        DealResources resource;
        if (TestUtil.findAll(em, DealResources.class).isEmpty()) {
            dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
            resource = DealResourcesResourceIT.createEntity(em);
        } else {
            resource = TestUtil.findAll(em, DealResources.class).get(0);
        }
        em.persist(resource);
        em.flush();
        dealRequirementMatchingResources.setResource(resource);
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
        Long resourceId = resource.getId();

        // Get all the dealRequirementMatchingResourcesList where resource equals to resourceId
        defaultDealRequirementMatchingResourcesShouldBeFound("resourceId.equals=" + resourceId);

        // Get all the dealRequirementMatchingResourcesList where resource equals to (resourceId + 1)
        defaultDealRequirementMatchingResourcesShouldNotBeFound("resourceId.equals=" + (resourceId + 1));
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByResourcestatusIsEqualToSomething() throws Exception {
        DealResourceStatus resourcestatus;
        if (TestUtil.findAll(em, DealResourceStatus.class).isEmpty()) {
            dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
            resourcestatus = DealResourceStatusResourceIT.createEntity(em);
        } else {
            resourcestatus = TestUtil.findAll(em, DealResourceStatus.class).get(0);
        }
        em.persist(resourcestatus);
        em.flush();
        dealRequirementMatchingResources.setResourcestatus(resourcestatus);
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
        Long resourcestatusId = resourcestatus.getId();

        // Get all the dealRequirementMatchingResourcesList where resourcestatus equals to resourcestatusId
        defaultDealRequirementMatchingResourcesShouldBeFound("resourcestatusId.equals=" + resourcestatusId);

        // Get all the dealRequirementMatchingResourcesList where resourcestatus equals to (resourcestatusId + 1)
        defaultDealRequirementMatchingResourcesShouldNotBeFound("resourcestatusId.equals=" + (resourcestatusId + 1));
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesBySystemstatusIsEqualToSomething() throws Exception {
        DealResourceStatus systemstatus;
        if (TestUtil.findAll(em, DealResourceStatus.class).isEmpty()) {
            dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
            systemstatus = DealResourceStatusResourceIT.createEntity(em);
        } else {
            systemstatus = TestUtil.findAll(em, DealResourceStatus.class).get(0);
        }
        em.persist(systemstatus);
        em.flush();
        dealRequirementMatchingResources.setSystemstatus(systemstatus);
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
        Long systemstatusId = systemstatus.getId();

        // Get all the dealRequirementMatchingResourcesList where systemstatus equals to systemstatusId
        defaultDealRequirementMatchingResourcesShouldBeFound("systemstatusId.equals=" + systemstatusId);

        // Get all the dealRequirementMatchingResourcesList where systemstatus equals to (systemstatusId + 1)
        defaultDealRequirementMatchingResourcesShouldNotBeFound("systemstatusId.equals=" + (systemstatusId + 1));
    }

    @Test
    @Transactional
    void getAllDealRequirementMatchingResourcesByDealresourceeventlogsMatchingresourceIsEqualToSomething() throws Exception {
        DealResourceEventLogs dealresourceeventlogsMatchingresource;
        if (TestUtil.findAll(em, DealResourceEventLogs.class).isEmpty()) {
            dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
            dealresourceeventlogsMatchingresource = DealResourceEventLogsResourceIT.createEntity(em);
        } else {
            dealresourceeventlogsMatchingresource = TestUtil.findAll(em, DealResourceEventLogs.class).get(0);
        }
        em.persist(dealresourceeventlogsMatchingresource);
        em.flush();
        dealRequirementMatchingResources.addDealresourceeventlogsMatchingresource(dealresourceeventlogsMatchingresource);
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);
        Long dealresourceeventlogsMatchingresourceId = dealresourceeventlogsMatchingresource.getId();

        // Get all the dealRequirementMatchingResourcesList where dealresourceeventlogsMatchingresource equals to dealresourceeventlogsMatchingresourceId
        defaultDealRequirementMatchingResourcesShouldBeFound(
            "dealresourceeventlogsMatchingresourceId.equals=" + dealresourceeventlogsMatchingresourceId
        );

        // Get all the dealRequirementMatchingResourcesList where dealresourceeventlogsMatchingresource equals to (dealresourceeventlogsMatchingresourceId + 1)
        defaultDealRequirementMatchingResourcesShouldNotBeFound(
            "dealresourceeventlogsMatchingresourceId.equals=" + (dealresourceeventlogsMatchingresourceId + 1)
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealRequirementMatchingResourcesShouldBeFound(String filter) throws Exception {
        restDealRequirementMatchingResourcesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealRequirementMatchingResources.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restDealRequirementMatchingResourcesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealRequirementMatchingResourcesShouldNotBeFound(String filter) throws Exception {
        restDealRequirementMatchingResourcesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealRequirementMatchingResourcesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDealRequirementMatchingResources() throws Exception {
        // Get the dealRequirementMatchingResources
        restDealRequirementMatchingResourcesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDealRequirementMatchingResources() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();

        // Update the dealRequirementMatchingResources
        DealRequirementMatchingResources updatedDealRequirementMatchingResources = dealRequirementMatchingResourcesRepository
            .findById(dealRequirementMatchingResources.getId())
            .get();
        // Disconnect from session so that the updates on updatedDealRequirementMatchingResources are not directly saved in db
        em.detach(updatedDealRequirementMatchingResources);
        updatedDealRequirementMatchingResources
            .comments(UPDATED_COMMENTS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restDealRequirementMatchingResourcesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealRequirementMatchingResources.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealRequirementMatchingResources))
            )
            .andExpect(status().isOk());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
        DealRequirementMatchingResources testDealRequirementMatchingResources = dealRequirementMatchingResourcesList.get(
            dealRequirementMatchingResourcesList.size() - 1
        );
        assertThat(testDealRequirementMatchingResources.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testDealRequirementMatchingResources.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealRequirementMatchingResources.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealRequirementMatchingResources.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDealRequirementMatchingResources() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();
        dealRequirementMatchingResources.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealRequirementMatchingResourcesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealRequirementMatchingResources.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirementMatchingResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealRequirementMatchingResources() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();
        dealRequirementMatchingResources.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealRequirementMatchingResourcesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirementMatchingResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealRequirementMatchingResources() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();
        dealRequirementMatchingResources.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealRequirementMatchingResourcesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirementMatchingResources))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealRequirementMatchingResourcesWithPatch() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();

        // Update the dealRequirementMatchingResources using partial update
        DealRequirementMatchingResources partialUpdatedDealRequirementMatchingResources = new DealRequirementMatchingResources();
        partialUpdatedDealRequirementMatchingResources.setId(dealRequirementMatchingResources.getId());

        partialUpdatedDealRequirementMatchingResources.comments(UPDATED_COMMENTS).updatedat(UPDATED_UPDATEDAT);

        restDealRequirementMatchingResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealRequirementMatchingResources.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealRequirementMatchingResources))
            )
            .andExpect(status().isOk());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
        DealRequirementMatchingResources testDealRequirementMatchingResources = dealRequirementMatchingResourcesList.get(
            dealRequirementMatchingResourcesList.size() - 1
        );
        assertThat(testDealRequirementMatchingResources.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testDealRequirementMatchingResources.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testDealRequirementMatchingResources.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealRequirementMatchingResources.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDealRequirementMatchingResourcesWithPatch() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();

        // Update the dealRequirementMatchingResources using partial update
        DealRequirementMatchingResources partialUpdatedDealRequirementMatchingResources = new DealRequirementMatchingResources();
        partialUpdatedDealRequirementMatchingResources.setId(dealRequirementMatchingResources.getId());

        partialUpdatedDealRequirementMatchingResources
            .comments(UPDATED_COMMENTS)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restDealRequirementMatchingResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealRequirementMatchingResources.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealRequirementMatchingResources))
            )
            .andExpect(status().isOk());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
        DealRequirementMatchingResources testDealRequirementMatchingResources = dealRequirementMatchingResourcesList.get(
            dealRequirementMatchingResourcesList.size() - 1
        );
        assertThat(testDealRequirementMatchingResources.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testDealRequirementMatchingResources.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testDealRequirementMatchingResources.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testDealRequirementMatchingResources.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDealRequirementMatchingResources() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();
        dealRequirementMatchingResources.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealRequirementMatchingResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealRequirementMatchingResources.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirementMatchingResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealRequirementMatchingResources() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();
        dealRequirementMatchingResources.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealRequirementMatchingResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirementMatchingResources))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealRequirementMatchingResources() throws Exception {
        int databaseSizeBeforeUpdate = dealRequirementMatchingResourcesRepository.findAll().size();
        dealRequirementMatchingResources.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealRequirementMatchingResourcesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealRequirementMatchingResources))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealRequirementMatchingResources in the database
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealRequirementMatchingResources() throws Exception {
        // Initialize the database
        dealRequirementMatchingResourcesRepository.saveAndFlush(dealRequirementMatchingResources);

        int databaseSizeBeforeDelete = dealRequirementMatchingResourcesRepository.findAll().size();

        // Delete the dealRequirementMatchingResources
        restDealRequirementMatchingResourcesMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealRequirementMatchingResources.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealRequirementMatchingResources> dealRequirementMatchingResourcesList = dealRequirementMatchingResourcesRepository.findAll();
        assertThat(dealRequirementMatchingResourcesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
