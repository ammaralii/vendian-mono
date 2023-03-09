package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Benefits;
import com.venturedive.vendian_mono.domain.CompensationBenefits;
import com.venturedive.vendian_mono.domain.EmployeeCompensation;
import com.venturedive.vendian_mono.repository.CompensationBenefitsRepository;
import com.venturedive.vendian_mono.service.CompensationBenefitsService;
import com.venturedive.vendian_mono.service.criteria.CompensationBenefitsCriteria;
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
 * Integration tests for the {@link CompensationBenefitsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CompensationBenefitsResourceIT {

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;
    private static final Float SMALLER_AMOUNT = 1F - 1F;

    private static final Boolean DEFAULT_ISDELETED = false;
    private static final Boolean UPDATED_ISDELETED = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/compensation-benefits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompensationBenefitsRepository compensationBenefitsRepository;

    @Mock
    private CompensationBenefitsRepository compensationBenefitsRepositoryMock;

    @Mock
    private CompensationBenefitsService compensationBenefitsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompensationBenefitsMockMvc;

    private CompensationBenefits compensationBenefits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompensationBenefits createEntity(EntityManager em) {
        CompensationBenefits compensationBenefits = new CompensationBenefits()
            .amount(DEFAULT_AMOUNT)
            .isdeleted(DEFAULT_ISDELETED)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        // Add required entity
        Benefits benefits;
        if (TestUtil.findAll(em, Benefits.class).isEmpty()) {
            benefits = BenefitsResourceIT.createEntity(em);
            em.persist(benefits);
            em.flush();
        } else {
            benefits = TestUtil.findAll(em, Benefits.class).get(0);
        }
        compensationBenefits.setBenefit(benefits);
        // Add required entity
        EmployeeCompensation employeeCompensation;
        if (TestUtil.findAll(em, EmployeeCompensation.class).isEmpty()) {
            employeeCompensation = EmployeeCompensationResourceIT.createEntity(em);
            em.persist(employeeCompensation);
            em.flush();
        } else {
            employeeCompensation = TestUtil.findAll(em, EmployeeCompensation.class).get(0);
        }
        compensationBenefits.setEmployeecompensation(employeeCompensation);
        return compensationBenefits;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompensationBenefits createUpdatedEntity(EntityManager em) {
        CompensationBenefits compensationBenefits = new CompensationBenefits()
            .amount(UPDATED_AMOUNT)
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        // Add required entity
        Benefits benefits;
        if (TestUtil.findAll(em, Benefits.class).isEmpty()) {
            benefits = BenefitsResourceIT.createUpdatedEntity(em);
            em.persist(benefits);
            em.flush();
        } else {
            benefits = TestUtil.findAll(em, Benefits.class).get(0);
        }
        compensationBenefits.setBenefit(benefits);
        // Add required entity
        EmployeeCompensation employeeCompensation;
        if (TestUtil.findAll(em, EmployeeCompensation.class).isEmpty()) {
            employeeCompensation = EmployeeCompensationResourceIT.createUpdatedEntity(em);
            em.persist(employeeCompensation);
            em.flush();
        } else {
            employeeCompensation = TestUtil.findAll(em, EmployeeCompensation.class).get(0);
        }
        compensationBenefits.setEmployeecompensation(employeeCompensation);
        return compensationBenefits;
    }

    @BeforeEach
    public void initTest() {
        compensationBenefits = createEntity(em);
    }

    @Test
    @Transactional
    void createCompensationBenefits() throws Exception {
        int databaseSizeBeforeCreate = compensationBenefitsRepository.findAll().size();
        // Create the CompensationBenefits
        restCompensationBenefitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compensationBenefits))
            )
            .andExpect(status().isCreated());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeCreate + 1);
        CompensationBenefits testCompensationBenefits = compensationBenefitsList.get(compensationBenefitsList.size() - 1);
        assertThat(testCompensationBenefits.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCompensationBenefits.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testCompensationBenefits.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testCompensationBenefits.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createCompensationBenefitsWithExistingId() throws Exception {
        // Create the CompensationBenefits with an existing ID
        compensationBenefits.setId(1L);

        int databaseSizeBeforeCreate = compensationBenefitsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompensationBenefitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compensationBenefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompensationBenefits() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList
        restCompensationBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compensationBenefits.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompensationBenefitsWithEagerRelationshipsIsEnabled() throws Exception {
        when(compensationBenefitsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompensationBenefitsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(compensationBenefitsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompensationBenefitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(compensationBenefitsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompensationBenefitsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(compensationBenefitsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCompensationBenefits() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get the compensationBenefits
        restCompensationBenefitsMockMvc
            .perform(get(ENTITY_API_URL_ID, compensationBenefits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compensationBenefits.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.isdeleted").value(DEFAULT_ISDELETED.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getCompensationBenefitsByIdFiltering() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        Long id = compensationBenefits.getId();

        defaultCompensationBenefitsShouldBeFound("id.equals=" + id);
        defaultCompensationBenefitsShouldNotBeFound("id.notEquals=" + id);

        defaultCompensationBenefitsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompensationBenefitsShouldNotBeFound("id.greaterThan=" + id);

        defaultCompensationBenefitsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompensationBenefitsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where amount equals to DEFAULT_AMOUNT
        defaultCompensationBenefitsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the compensationBenefitsList where amount equals to UPDATED_AMOUNT
        defaultCompensationBenefitsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCompensationBenefitsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the compensationBenefitsList where amount equals to UPDATED_AMOUNT
        defaultCompensationBenefitsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where amount is not null
        defaultCompensationBenefitsShouldBeFound("amount.specified=true");

        // Get all the compensationBenefitsList where amount is null
        defaultCompensationBenefitsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCompensationBenefitsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the compensationBenefitsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCompensationBenefitsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCompensationBenefitsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the compensationBenefitsList where amount is less than or equal to SMALLER_AMOUNT
        defaultCompensationBenefitsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where amount is less than DEFAULT_AMOUNT
        defaultCompensationBenefitsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the compensationBenefitsList where amount is less than UPDATED_AMOUNT
        defaultCompensationBenefitsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where amount is greater than DEFAULT_AMOUNT
        defaultCompensationBenefitsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the compensationBenefitsList where amount is greater than SMALLER_AMOUNT
        defaultCompensationBenefitsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByIsdeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where isdeleted equals to DEFAULT_ISDELETED
        defaultCompensationBenefitsShouldBeFound("isdeleted.equals=" + DEFAULT_ISDELETED);

        // Get all the compensationBenefitsList where isdeleted equals to UPDATED_ISDELETED
        defaultCompensationBenefitsShouldNotBeFound("isdeleted.equals=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByIsdeletedIsInShouldWork() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where isdeleted in DEFAULT_ISDELETED or UPDATED_ISDELETED
        defaultCompensationBenefitsShouldBeFound("isdeleted.in=" + DEFAULT_ISDELETED + "," + UPDATED_ISDELETED);

        // Get all the compensationBenefitsList where isdeleted equals to UPDATED_ISDELETED
        defaultCompensationBenefitsShouldNotBeFound("isdeleted.in=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByIsdeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where isdeleted is not null
        defaultCompensationBenefitsShouldBeFound("isdeleted.specified=true");

        // Get all the compensationBenefitsList where isdeleted is null
        defaultCompensationBenefitsShouldNotBeFound("isdeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where createdat equals to DEFAULT_CREATEDAT
        defaultCompensationBenefitsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the compensationBenefitsList where createdat equals to UPDATED_CREATEDAT
        defaultCompensationBenefitsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultCompensationBenefitsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the compensationBenefitsList where createdat equals to UPDATED_CREATEDAT
        defaultCompensationBenefitsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where createdat is not null
        defaultCompensationBenefitsShouldBeFound("createdat.specified=true");

        // Get all the compensationBenefitsList where createdat is null
        defaultCompensationBenefitsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultCompensationBenefitsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the compensationBenefitsList where updatedat equals to UPDATED_UPDATEDAT
        defaultCompensationBenefitsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultCompensationBenefitsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the compensationBenefitsList where updatedat equals to UPDATED_UPDATEDAT
        defaultCompensationBenefitsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        // Get all the compensationBenefitsList where updatedat is not null
        defaultCompensationBenefitsShouldBeFound("updatedat.specified=true");

        // Get all the compensationBenefitsList where updatedat is null
        defaultCompensationBenefitsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByBenefitIsEqualToSomething() throws Exception {
        Benefits benefit;
        if (TestUtil.findAll(em, Benefits.class).isEmpty()) {
            compensationBenefitsRepository.saveAndFlush(compensationBenefits);
            benefit = BenefitsResourceIT.createEntity(em);
        } else {
            benefit = TestUtil.findAll(em, Benefits.class).get(0);
        }
        em.persist(benefit);
        em.flush();
        compensationBenefits.setBenefit(benefit);
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);
        Long benefitId = benefit.getId();

        // Get all the compensationBenefitsList where benefit equals to benefitId
        defaultCompensationBenefitsShouldBeFound("benefitId.equals=" + benefitId);

        // Get all the compensationBenefitsList where benefit equals to (benefitId + 1)
        defaultCompensationBenefitsShouldNotBeFound("benefitId.equals=" + (benefitId + 1));
    }

    @Test
    @Transactional
    void getAllCompensationBenefitsByEmployeecompensationIsEqualToSomething() throws Exception {
        EmployeeCompensation employeecompensation;
        if (TestUtil.findAll(em, EmployeeCompensation.class).isEmpty()) {
            compensationBenefitsRepository.saveAndFlush(compensationBenefits);
            employeecompensation = EmployeeCompensationResourceIT.createEntity(em);
        } else {
            employeecompensation = TestUtil.findAll(em, EmployeeCompensation.class).get(0);
        }
        em.persist(employeecompensation);
        em.flush();
        compensationBenefits.setEmployeecompensation(employeecompensation);
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);
        Long employeecompensationId = employeecompensation.getId();

        // Get all the compensationBenefitsList where employeecompensation equals to employeecompensationId
        defaultCompensationBenefitsShouldBeFound("employeecompensationId.equals=" + employeecompensationId);

        // Get all the compensationBenefitsList where employeecompensation equals to (employeecompensationId + 1)
        defaultCompensationBenefitsShouldNotBeFound("employeecompensationId.equals=" + (employeecompensationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompensationBenefitsShouldBeFound(String filter) throws Exception {
        restCompensationBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compensationBenefits.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restCompensationBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompensationBenefitsShouldNotBeFound(String filter) throws Exception {
        restCompensationBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompensationBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompensationBenefits() throws Exception {
        // Get the compensationBenefits
        restCompensationBenefitsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompensationBenefits() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();

        // Update the compensationBenefits
        CompensationBenefits updatedCompensationBenefits = compensationBenefitsRepository.findById(compensationBenefits.getId()).get();
        // Disconnect from session so that the updates on updatedCompensationBenefits are not directly saved in db
        em.detach(updatedCompensationBenefits);
        updatedCompensationBenefits
            .amount(UPDATED_AMOUNT)
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restCompensationBenefitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompensationBenefits.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompensationBenefits))
            )
            .andExpect(status().isOk());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
        CompensationBenefits testCompensationBenefits = compensationBenefitsList.get(compensationBenefitsList.size() - 1);
        assertThat(testCompensationBenefits.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCompensationBenefits.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testCompensationBenefits.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testCompensationBenefits.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingCompensationBenefits() throws Exception {
        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();
        compensationBenefits.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompensationBenefitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compensationBenefits.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compensationBenefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompensationBenefits() throws Exception {
        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();
        compensationBenefits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompensationBenefitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compensationBenefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompensationBenefits() throws Exception {
        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();
        compensationBenefits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompensationBenefitsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compensationBenefits))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompensationBenefitsWithPatch() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();

        // Update the compensationBenefits using partial update
        CompensationBenefits partialUpdatedCompensationBenefits = new CompensationBenefits();
        partialUpdatedCompensationBenefits.setId(compensationBenefits.getId());

        partialUpdatedCompensationBenefits.amount(UPDATED_AMOUNT).isdeleted(UPDATED_ISDELETED).updatedat(UPDATED_UPDATEDAT);

        restCompensationBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompensationBenefits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompensationBenefits))
            )
            .andExpect(status().isOk());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
        CompensationBenefits testCompensationBenefits = compensationBenefitsList.get(compensationBenefitsList.size() - 1);
        assertThat(testCompensationBenefits.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCompensationBenefits.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testCompensationBenefits.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testCompensationBenefits.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateCompensationBenefitsWithPatch() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();

        // Update the compensationBenefits using partial update
        CompensationBenefits partialUpdatedCompensationBenefits = new CompensationBenefits();
        partialUpdatedCompensationBenefits.setId(compensationBenefits.getId());

        partialUpdatedCompensationBenefits
            .amount(UPDATED_AMOUNT)
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restCompensationBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompensationBenefits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompensationBenefits))
            )
            .andExpect(status().isOk());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
        CompensationBenefits testCompensationBenefits = compensationBenefitsList.get(compensationBenefitsList.size() - 1);
        assertThat(testCompensationBenefits.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCompensationBenefits.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testCompensationBenefits.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testCompensationBenefits.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingCompensationBenefits() throws Exception {
        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();
        compensationBenefits.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompensationBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, compensationBenefits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compensationBenefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompensationBenefits() throws Exception {
        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();
        compensationBenefits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompensationBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compensationBenefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompensationBenefits() throws Exception {
        int databaseSizeBeforeUpdate = compensationBenefitsRepository.findAll().size();
        compensationBenefits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompensationBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compensationBenefits))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompensationBenefits in the database
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompensationBenefits() throws Exception {
        // Initialize the database
        compensationBenefitsRepository.saveAndFlush(compensationBenefits);

        int databaseSizeBeforeDelete = compensationBenefitsRepository.findAll().size();

        // Delete the compensationBenefits
        restCompensationBenefitsMockMvc
            .perform(delete(ENTITY_API_URL_ID, compensationBenefits.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompensationBenefits> compensationBenefitsList = compensationBenefitsRepository.findAll();
        assertThat(compensationBenefitsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
