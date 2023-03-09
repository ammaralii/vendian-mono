package com.venturedive.vendian_mono.web.rest;

import static com.venturedive.vendian_mono.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.LeavesCopy;
import com.venturedive.vendian_mono.repository.LeavesCopyRepository;
import com.venturedive.vendian_mono.service.criteria.LeavesCopyCriteria;
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
 * Integration tests for the {@link LeavesCopyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeavesCopyResourceIT {

    private static final BigDecimal DEFAULT_ANNUALTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANNUALTOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_ANNUALTOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ANNUALUSED = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANNUALUSED = new BigDecimal(2);
    private static final BigDecimal SMALLER_ANNUALUSED = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ANNUALADJUSTMENTS = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANNUALADJUSTMENTS = new BigDecimal(2);
    private static final BigDecimal SMALLER_ANNUALADJUSTMENTS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CASUALTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASUALTOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_CASUALTOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CASUALUSED = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASUALUSED = new BigDecimal(2);
    private static final BigDecimal SMALLER_CASUALUSED = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SICKTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SICKTOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SICKTOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SICKUSED = new BigDecimal(1);
    private static final BigDecimal UPDATED_SICKUSED = new BigDecimal(2);
    private static final BigDecimal SMALLER_SICKUSED = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ANNUALTOTALNEXTYEAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANNUALTOTALNEXTYEAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_ANNUALTOTALNEXTYEAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ANNUALUSEDNEXTYEAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANNUALUSEDNEXTYEAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_ANNUALUSEDNEXTYEAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CASUALTOTALNEXTYEAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASUALTOTALNEXTYEAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_CASUALTOTALNEXTYEAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CASUALUSEDNEXTYEAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASUALUSEDNEXTYEAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_CASUALUSEDNEXTYEAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SICKTOTALNEXTYEAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_SICKTOTALNEXTYEAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_SICKTOTALNEXTYEAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SICKUSEDNEXTYEAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_SICKUSEDNEXTYEAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_SICKUSEDNEXTYEAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CARRYFORWARD = new BigDecimal(1);
    private static final BigDecimal UPDATED_CARRYFORWARD = new BigDecimal(2);
    private static final BigDecimal SMALLER_CARRYFORWARD = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/leaves-copies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeavesCopyRepository leavesCopyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeavesCopyMockMvc;

    private LeavesCopy leavesCopy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeavesCopy createEntity(EntityManager em) {
        LeavesCopy leavesCopy = new LeavesCopy()
            .annualtotal(DEFAULT_ANNUALTOTAL)
            .annualused(DEFAULT_ANNUALUSED)
            .annualadjustments(DEFAULT_ANNUALADJUSTMENTS)
            .casualtotal(DEFAULT_CASUALTOTAL)
            .casualused(DEFAULT_CASUALUSED)
            .sicktotal(DEFAULT_SICKTOTAL)
            .sickused(DEFAULT_SICKUSED)
            .annualtotalnextyear(DEFAULT_ANNUALTOTALNEXTYEAR)
            .annualusednextyear(DEFAULT_ANNUALUSEDNEXTYEAR)
            .casualtotalnextyear(DEFAULT_CASUALTOTALNEXTYEAR)
            .casualusednextyear(DEFAULT_CASUALUSEDNEXTYEAR)
            .sicktotalnextyear(DEFAULT_SICKTOTALNEXTYEAR)
            .sickusednextyear(DEFAULT_SICKUSEDNEXTYEAR)
            .carryforward(DEFAULT_CARRYFORWARD)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return leavesCopy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeavesCopy createUpdatedEntity(EntityManager em) {
        LeavesCopy leavesCopy = new LeavesCopy()
            .annualtotal(UPDATED_ANNUALTOTAL)
            .annualused(UPDATED_ANNUALUSED)
            .annualadjustments(UPDATED_ANNUALADJUSTMENTS)
            .casualtotal(UPDATED_CASUALTOTAL)
            .casualused(UPDATED_CASUALUSED)
            .sicktotal(UPDATED_SICKTOTAL)
            .sickused(UPDATED_SICKUSED)
            .annualtotalnextyear(UPDATED_ANNUALTOTALNEXTYEAR)
            .annualusednextyear(UPDATED_ANNUALUSEDNEXTYEAR)
            .casualtotalnextyear(UPDATED_CASUALTOTALNEXTYEAR)
            .casualusednextyear(UPDATED_CASUALUSEDNEXTYEAR)
            .sicktotalnextyear(UPDATED_SICKTOTALNEXTYEAR)
            .sickusednextyear(UPDATED_SICKUSEDNEXTYEAR)
            .carryforward(UPDATED_CARRYFORWARD)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return leavesCopy;
    }

    @BeforeEach
    public void initTest() {
        leavesCopy = createEntity(em);
    }

    @Test
    @Transactional
    void createLeavesCopy() throws Exception {
        int databaseSizeBeforeCreate = leavesCopyRepository.findAll().size();
        // Create the LeavesCopy
        restLeavesCopyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isCreated());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeCreate + 1);
        LeavesCopy testLeavesCopy = leavesCopyList.get(leavesCopyList.size() - 1);
        assertThat(testLeavesCopy.getAnnualtotal()).isEqualByComparingTo(DEFAULT_ANNUALTOTAL);
        assertThat(testLeavesCopy.getAnnualused()).isEqualByComparingTo(DEFAULT_ANNUALUSED);
        assertThat(testLeavesCopy.getAnnualadjustments()).isEqualByComparingTo(DEFAULT_ANNUALADJUSTMENTS);
        assertThat(testLeavesCopy.getCasualtotal()).isEqualByComparingTo(DEFAULT_CASUALTOTAL);
        assertThat(testLeavesCopy.getCasualused()).isEqualByComparingTo(DEFAULT_CASUALUSED);
        assertThat(testLeavesCopy.getSicktotal()).isEqualByComparingTo(DEFAULT_SICKTOTAL);
        assertThat(testLeavesCopy.getSickused()).isEqualByComparingTo(DEFAULT_SICKUSED);
        assertThat(testLeavesCopy.getAnnualtotalnextyear()).isEqualByComparingTo(DEFAULT_ANNUALTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getAnnualusednextyear()).isEqualByComparingTo(DEFAULT_ANNUALUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getCasualtotalnextyear()).isEqualByComparingTo(DEFAULT_CASUALTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getCasualusednextyear()).isEqualByComparingTo(DEFAULT_CASUALUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getSicktotalnextyear()).isEqualByComparingTo(DEFAULT_SICKTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getSickusednextyear()).isEqualByComparingTo(DEFAULT_SICKUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getCarryforward()).isEqualByComparingTo(DEFAULT_CARRYFORWARD);
        assertThat(testLeavesCopy.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testLeavesCopy.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createLeavesCopyWithExistingId() throws Exception {
        // Create the LeavesCopy with an existing ID
        leavesCopy.setId(1L);

        int databaseSizeBeforeCreate = leavesCopyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeavesCopyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = leavesCopyRepository.findAll().size();
        // set the field null
        leavesCopy.setCreatedat(null);

        // Create the LeavesCopy, which fails.

        restLeavesCopyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isBadRequest());

        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = leavesCopyRepository.findAll().size();
        // set the field null
        leavesCopy.setUpdatedat(null);

        // Create the LeavesCopy, which fails.

        restLeavesCopyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isBadRequest());

        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeavesCopies() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList
        restLeavesCopyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leavesCopy.getId().intValue())))
            .andExpect(jsonPath("$.[*].annualtotal").value(hasItem(sameNumber(DEFAULT_ANNUALTOTAL))))
            .andExpect(jsonPath("$.[*].annualused").value(hasItem(sameNumber(DEFAULT_ANNUALUSED))))
            .andExpect(jsonPath("$.[*].annualadjustments").value(hasItem(sameNumber(DEFAULT_ANNUALADJUSTMENTS))))
            .andExpect(jsonPath("$.[*].casualtotal").value(hasItem(sameNumber(DEFAULT_CASUALTOTAL))))
            .andExpect(jsonPath("$.[*].casualused").value(hasItem(sameNumber(DEFAULT_CASUALUSED))))
            .andExpect(jsonPath("$.[*].sicktotal").value(hasItem(sameNumber(DEFAULT_SICKTOTAL))))
            .andExpect(jsonPath("$.[*].sickused").value(hasItem(sameNumber(DEFAULT_SICKUSED))))
            .andExpect(jsonPath("$.[*].annualtotalnextyear").value(hasItem(sameNumber(DEFAULT_ANNUALTOTALNEXTYEAR))))
            .andExpect(jsonPath("$.[*].annualusednextyear").value(hasItem(sameNumber(DEFAULT_ANNUALUSEDNEXTYEAR))))
            .andExpect(jsonPath("$.[*].casualtotalnextyear").value(hasItem(sameNumber(DEFAULT_CASUALTOTALNEXTYEAR))))
            .andExpect(jsonPath("$.[*].casualusednextyear").value(hasItem(sameNumber(DEFAULT_CASUALUSEDNEXTYEAR))))
            .andExpect(jsonPath("$.[*].sicktotalnextyear").value(hasItem(sameNumber(DEFAULT_SICKTOTALNEXTYEAR))))
            .andExpect(jsonPath("$.[*].sickusednextyear").value(hasItem(sameNumber(DEFAULT_SICKUSEDNEXTYEAR))))
            .andExpect(jsonPath("$.[*].carryforward").value(hasItem(sameNumber(DEFAULT_CARRYFORWARD))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getLeavesCopy() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get the leavesCopy
        restLeavesCopyMockMvc
            .perform(get(ENTITY_API_URL_ID, leavesCopy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leavesCopy.getId().intValue()))
            .andExpect(jsonPath("$.annualtotal").value(sameNumber(DEFAULT_ANNUALTOTAL)))
            .andExpect(jsonPath("$.annualused").value(sameNumber(DEFAULT_ANNUALUSED)))
            .andExpect(jsonPath("$.annualadjustments").value(sameNumber(DEFAULT_ANNUALADJUSTMENTS)))
            .andExpect(jsonPath("$.casualtotal").value(sameNumber(DEFAULT_CASUALTOTAL)))
            .andExpect(jsonPath("$.casualused").value(sameNumber(DEFAULT_CASUALUSED)))
            .andExpect(jsonPath("$.sicktotal").value(sameNumber(DEFAULT_SICKTOTAL)))
            .andExpect(jsonPath("$.sickused").value(sameNumber(DEFAULT_SICKUSED)))
            .andExpect(jsonPath("$.annualtotalnextyear").value(sameNumber(DEFAULT_ANNUALTOTALNEXTYEAR)))
            .andExpect(jsonPath("$.annualusednextyear").value(sameNumber(DEFAULT_ANNUALUSEDNEXTYEAR)))
            .andExpect(jsonPath("$.casualtotalnextyear").value(sameNumber(DEFAULT_CASUALTOTALNEXTYEAR)))
            .andExpect(jsonPath("$.casualusednextyear").value(sameNumber(DEFAULT_CASUALUSEDNEXTYEAR)))
            .andExpect(jsonPath("$.sicktotalnextyear").value(sameNumber(DEFAULT_SICKTOTALNEXTYEAR)))
            .andExpect(jsonPath("$.sickusednextyear").value(sameNumber(DEFAULT_SICKUSEDNEXTYEAR)))
            .andExpect(jsonPath("$.carryforward").value(sameNumber(DEFAULT_CARRYFORWARD)))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getLeavesCopiesByIdFiltering() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        Long id = leavesCopy.getId();

        defaultLeavesCopyShouldBeFound("id.equals=" + id);
        defaultLeavesCopyShouldNotBeFound("id.notEquals=" + id);

        defaultLeavesCopyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeavesCopyShouldNotBeFound("id.greaterThan=" + id);

        defaultLeavesCopyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeavesCopyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotal equals to DEFAULT_ANNUALTOTAL
        defaultLeavesCopyShouldBeFound("annualtotal.equals=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesCopyList where annualtotal equals to UPDATED_ANNUALTOTAL
        defaultLeavesCopyShouldNotBeFound("annualtotal.equals=" + UPDATED_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotal in DEFAULT_ANNUALTOTAL or UPDATED_ANNUALTOTAL
        defaultLeavesCopyShouldBeFound("annualtotal.in=" + DEFAULT_ANNUALTOTAL + "," + UPDATED_ANNUALTOTAL);

        // Get all the leavesCopyList where annualtotal equals to UPDATED_ANNUALTOTAL
        defaultLeavesCopyShouldNotBeFound("annualtotal.in=" + UPDATED_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotal is not null
        defaultLeavesCopyShouldBeFound("annualtotal.specified=true");

        // Get all the leavesCopyList where annualtotal is null
        defaultLeavesCopyShouldNotBeFound("annualtotal.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotal is greater than or equal to DEFAULT_ANNUALTOTAL
        defaultLeavesCopyShouldBeFound("annualtotal.greaterThanOrEqual=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesCopyList where annualtotal is greater than or equal to UPDATED_ANNUALTOTAL
        defaultLeavesCopyShouldNotBeFound("annualtotal.greaterThanOrEqual=" + UPDATED_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotal is less than or equal to DEFAULT_ANNUALTOTAL
        defaultLeavesCopyShouldBeFound("annualtotal.lessThanOrEqual=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesCopyList where annualtotal is less than or equal to SMALLER_ANNUALTOTAL
        defaultLeavesCopyShouldNotBeFound("annualtotal.lessThanOrEqual=" + SMALLER_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotal is less than DEFAULT_ANNUALTOTAL
        defaultLeavesCopyShouldNotBeFound("annualtotal.lessThan=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesCopyList where annualtotal is less than UPDATED_ANNUALTOTAL
        defaultLeavesCopyShouldBeFound("annualtotal.lessThan=" + UPDATED_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotal is greater than DEFAULT_ANNUALTOTAL
        defaultLeavesCopyShouldNotBeFound("annualtotal.greaterThan=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesCopyList where annualtotal is greater than SMALLER_ANNUALTOTAL
        defaultLeavesCopyShouldBeFound("annualtotal.greaterThan=" + SMALLER_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusedIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualused equals to DEFAULT_ANNUALUSED
        defaultLeavesCopyShouldBeFound("annualused.equals=" + DEFAULT_ANNUALUSED);

        // Get all the leavesCopyList where annualused equals to UPDATED_ANNUALUSED
        defaultLeavesCopyShouldNotBeFound("annualused.equals=" + UPDATED_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusedIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualused in DEFAULT_ANNUALUSED or UPDATED_ANNUALUSED
        defaultLeavesCopyShouldBeFound("annualused.in=" + DEFAULT_ANNUALUSED + "," + UPDATED_ANNUALUSED);

        // Get all the leavesCopyList where annualused equals to UPDATED_ANNUALUSED
        defaultLeavesCopyShouldNotBeFound("annualused.in=" + UPDATED_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualused is not null
        defaultLeavesCopyShouldBeFound("annualused.specified=true");

        // Get all the leavesCopyList where annualused is null
        defaultLeavesCopyShouldNotBeFound("annualused.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualused is greater than or equal to DEFAULT_ANNUALUSED
        defaultLeavesCopyShouldBeFound("annualused.greaterThanOrEqual=" + DEFAULT_ANNUALUSED);

        // Get all the leavesCopyList where annualused is greater than or equal to UPDATED_ANNUALUSED
        defaultLeavesCopyShouldNotBeFound("annualused.greaterThanOrEqual=" + UPDATED_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualused is less than or equal to DEFAULT_ANNUALUSED
        defaultLeavesCopyShouldBeFound("annualused.lessThanOrEqual=" + DEFAULT_ANNUALUSED);

        // Get all the leavesCopyList where annualused is less than or equal to SMALLER_ANNUALUSED
        defaultLeavesCopyShouldNotBeFound("annualused.lessThanOrEqual=" + SMALLER_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusedIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualused is less than DEFAULT_ANNUALUSED
        defaultLeavesCopyShouldNotBeFound("annualused.lessThan=" + DEFAULT_ANNUALUSED);

        // Get all the leavesCopyList where annualused is less than UPDATED_ANNUALUSED
        defaultLeavesCopyShouldBeFound("annualused.lessThan=" + UPDATED_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualused is greater than DEFAULT_ANNUALUSED
        defaultLeavesCopyShouldNotBeFound("annualused.greaterThan=" + DEFAULT_ANNUALUSED);

        // Get all the leavesCopyList where annualused is greater than SMALLER_ANNUALUSED
        defaultLeavesCopyShouldBeFound("annualused.greaterThan=" + SMALLER_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualadjustmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualadjustments equals to DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldBeFound("annualadjustments.equals=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesCopyList where annualadjustments equals to UPDATED_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldNotBeFound("annualadjustments.equals=" + UPDATED_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualadjustmentsIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualadjustments in DEFAULT_ANNUALADJUSTMENTS or UPDATED_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldBeFound("annualadjustments.in=" + DEFAULT_ANNUALADJUSTMENTS + "," + UPDATED_ANNUALADJUSTMENTS);

        // Get all the leavesCopyList where annualadjustments equals to UPDATED_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldNotBeFound("annualadjustments.in=" + UPDATED_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualadjustmentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualadjustments is not null
        defaultLeavesCopyShouldBeFound("annualadjustments.specified=true");

        // Get all the leavesCopyList where annualadjustments is null
        defaultLeavesCopyShouldNotBeFound("annualadjustments.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualadjustmentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualadjustments is greater than or equal to DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldBeFound("annualadjustments.greaterThanOrEqual=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesCopyList where annualadjustments is greater than or equal to UPDATED_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldNotBeFound("annualadjustments.greaterThanOrEqual=" + UPDATED_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualadjustmentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualadjustments is less than or equal to DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldBeFound("annualadjustments.lessThanOrEqual=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesCopyList where annualadjustments is less than or equal to SMALLER_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldNotBeFound("annualadjustments.lessThanOrEqual=" + SMALLER_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualadjustmentsIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualadjustments is less than DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldNotBeFound("annualadjustments.lessThan=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesCopyList where annualadjustments is less than UPDATED_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldBeFound("annualadjustments.lessThan=" + UPDATED_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualadjustmentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualadjustments is greater than DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldNotBeFound("annualadjustments.greaterThan=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesCopyList where annualadjustments is greater than SMALLER_ANNUALADJUSTMENTS
        defaultLeavesCopyShouldBeFound("annualadjustments.greaterThan=" + SMALLER_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotal equals to DEFAULT_CASUALTOTAL
        defaultLeavesCopyShouldBeFound("casualtotal.equals=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesCopyList where casualtotal equals to UPDATED_CASUALTOTAL
        defaultLeavesCopyShouldNotBeFound("casualtotal.equals=" + UPDATED_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotal in DEFAULT_CASUALTOTAL or UPDATED_CASUALTOTAL
        defaultLeavesCopyShouldBeFound("casualtotal.in=" + DEFAULT_CASUALTOTAL + "," + UPDATED_CASUALTOTAL);

        // Get all the leavesCopyList where casualtotal equals to UPDATED_CASUALTOTAL
        defaultLeavesCopyShouldNotBeFound("casualtotal.in=" + UPDATED_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotal is not null
        defaultLeavesCopyShouldBeFound("casualtotal.specified=true");

        // Get all the leavesCopyList where casualtotal is null
        defaultLeavesCopyShouldNotBeFound("casualtotal.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotal is greater than or equal to DEFAULT_CASUALTOTAL
        defaultLeavesCopyShouldBeFound("casualtotal.greaterThanOrEqual=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesCopyList where casualtotal is greater than or equal to UPDATED_CASUALTOTAL
        defaultLeavesCopyShouldNotBeFound("casualtotal.greaterThanOrEqual=" + UPDATED_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotal is less than or equal to DEFAULT_CASUALTOTAL
        defaultLeavesCopyShouldBeFound("casualtotal.lessThanOrEqual=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesCopyList where casualtotal is less than or equal to SMALLER_CASUALTOTAL
        defaultLeavesCopyShouldNotBeFound("casualtotal.lessThanOrEqual=" + SMALLER_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotal is less than DEFAULT_CASUALTOTAL
        defaultLeavesCopyShouldNotBeFound("casualtotal.lessThan=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesCopyList where casualtotal is less than UPDATED_CASUALTOTAL
        defaultLeavesCopyShouldBeFound("casualtotal.lessThan=" + UPDATED_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotal is greater than DEFAULT_CASUALTOTAL
        defaultLeavesCopyShouldNotBeFound("casualtotal.greaterThan=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesCopyList where casualtotal is greater than SMALLER_CASUALTOTAL
        defaultLeavesCopyShouldBeFound("casualtotal.greaterThan=" + SMALLER_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusedIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualused equals to DEFAULT_CASUALUSED
        defaultLeavesCopyShouldBeFound("casualused.equals=" + DEFAULT_CASUALUSED);

        // Get all the leavesCopyList where casualused equals to UPDATED_CASUALUSED
        defaultLeavesCopyShouldNotBeFound("casualused.equals=" + UPDATED_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusedIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualused in DEFAULT_CASUALUSED or UPDATED_CASUALUSED
        defaultLeavesCopyShouldBeFound("casualused.in=" + DEFAULT_CASUALUSED + "," + UPDATED_CASUALUSED);

        // Get all the leavesCopyList where casualused equals to UPDATED_CASUALUSED
        defaultLeavesCopyShouldNotBeFound("casualused.in=" + UPDATED_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualused is not null
        defaultLeavesCopyShouldBeFound("casualused.specified=true");

        // Get all the leavesCopyList where casualused is null
        defaultLeavesCopyShouldNotBeFound("casualused.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualused is greater than or equal to DEFAULT_CASUALUSED
        defaultLeavesCopyShouldBeFound("casualused.greaterThanOrEqual=" + DEFAULT_CASUALUSED);

        // Get all the leavesCopyList where casualused is greater than or equal to UPDATED_CASUALUSED
        defaultLeavesCopyShouldNotBeFound("casualused.greaterThanOrEqual=" + UPDATED_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualused is less than or equal to DEFAULT_CASUALUSED
        defaultLeavesCopyShouldBeFound("casualused.lessThanOrEqual=" + DEFAULT_CASUALUSED);

        // Get all the leavesCopyList where casualused is less than or equal to SMALLER_CASUALUSED
        defaultLeavesCopyShouldNotBeFound("casualused.lessThanOrEqual=" + SMALLER_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusedIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualused is less than DEFAULT_CASUALUSED
        defaultLeavesCopyShouldNotBeFound("casualused.lessThan=" + DEFAULT_CASUALUSED);

        // Get all the leavesCopyList where casualused is less than UPDATED_CASUALUSED
        defaultLeavesCopyShouldBeFound("casualused.lessThan=" + UPDATED_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualused is greater than DEFAULT_CASUALUSED
        defaultLeavesCopyShouldNotBeFound("casualused.greaterThan=" + DEFAULT_CASUALUSED);

        // Get all the leavesCopyList where casualused is greater than SMALLER_CASUALUSED
        defaultLeavesCopyShouldBeFound("casualused.greaterThan=" + SMALLER_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotal equals to DEFAULT_SICKTOTAL
        defaultLeavesCopyShouldBeFound("sicktotal.equals=" + DEFAULT_SICKTOTAL);

        // Get all the leavesCopyList where sicktotal equals to UPDATED_SICKTOTAL
        defaultLeavesCopyShouldNotBeFound("sicktotal.equals=" + UPDATED_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotal in DEFAULT_SICKTOTAL or UPDATED_SICKTOTAL
        defaultLeavesCopyShouldBeFound("sicktotal.in=" + DEFAULT_SICKTOTAL + "," + UPDATED_SICKTOTAL);

        // Get all the leavesCopyList where sicktotal equals to UPDATED_SICKTOTAL
        defaultLeavesCopyShouldNotBeFound("sicktotal.in=" + UPDATED_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotal is not null
        defaultLeavesCopyShouldBeFound("sicktotal.specified=true");

        // Get all the leavesCopyList where sicktotal is null
        defaultLeavesCopyShouldNotBeFound("sicktotal.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotal is greater than or equal to DEFAULT_SICKTOTAL
        defaultLeavesCopyShouldBeFound("sicktotal.greaterThanOrEqual=" + DEFAULT_SICKTOTAL);

        // Get all the leavesCopyList where sicktotal is greater than or equal to UPDATED_SICKTOTAL
        defaultLeavesCopyShouldNotBeFound("sicktotal.greaterThanOrEqual=" + UPDATED_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotal is less than or equal to DEFAULT_SICKTOTAL
        defaultLeavesCopyShouldBeFound("sicktotal.lessThanOrEqual=" + DEFAULT_SICKTOTAL);

        // Get all the leavesCopyList where sicktotal is less than or equal to SMALLER_SICKTOTAL
        defaultLeavesCopyShouldNotBeFound("sicktotal.lessThanOrEqual=" + SMALLER_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotal is less than DEFAULT_SICKTOTAL
        defaultLeavesCopyShouldNotBeFound("sicktotal.lessThan=" + DEFAULT_SICKTOTAL);

        // Get all the leavesCopyList where sicktotal is less than UPDATED_SICKTOTAL
        defaultLeavesCopyShouldBeFound("sicktotal.lessThan=" + UPDATED_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotal is greater than DEFAULT_SICKTOTAL
        defaultLeavesCopyShouldNotBeFound("sicktotal.greaterThan=" + DEFAULT_SICKTOTAL);

        // Get all the leavesCopyList where sicktotal is greater than SMALLER_SICKTOTAL
        defaultLeavesCopyShouldBeFound("sicktotal.greaterThan=" + SMALLER_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusedIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickused equals to DEFAULT_SICKUSED
        defaultLeavesCopyShouldBeFound("sickused.equals=" + DEFAULT_SICKUSED);

        // Get all the leavesCopyList where sickused equals to UPDATED_SICKUSED
        defaultLeavesCopyShouldNotBeFound("sickused.equals=" + UPDATED_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusedIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickused in DEFAULT_SICKUSED or UPDATED_SICKUSED
        defaultLeavesCopyShouldBeFound("sickused.in=" + DEFAULT_SICKUSED + "," + UPDATED_SICKUSED);

        // Get all the leavesCopyList where sickused equals to UPDATED_SICKUSED
        defaultLeavesCopyShouldNotBeFound("sickused.in=" + UPDATED_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickused is not null
        defaultLeavesCopyShouldBeFound("sickused.specified=true");

        // Get all the leavesCopyList where sickused is null
        defaultLeavesCopyShouldNotBeFound("sickused.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickused is greater than or equal to DEFAULT_SICKUSED
        defaultLeavesCopyShouldBeFound("sickused.greaterThanOrEqual=" + DEFAULT_SICKUSED);

        // Get all the leavesCopyList where sickused is greater than or equal to UPDATED_SICKUSED
        defaultLeavesCopyShouldNotBeFound("sickused.greaterThanOrEqual=" + UPDATED_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickused is less than or equal to DEFAULT_SICKUSED
        defaultLeavesCopyShouldBeFound("sickused.lessThanOrEqual=" + DEFAULT_SICKUSED);

        // Get all the leavesCopyList where sickused is less than or equal to SMALLER_SICKUSED
        defaultLeavesCopyShouldNotBeFound("sickused.lessThanOrEqual=" + SMALLER_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusedIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickused is less than DEFAULT_SICKUSED
        defaultLeavesCopyShouldNotBeFound("sickused.lessThan=" + DEFAULT_SICKUSED);

        // Get all the leavesCopyList where sickused is less than UPDATED_SICKUSED
        defaultLeavesCopyShouldBeFound("sickused.lessThan=" + UPDATED_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickused is greater than DEFAULT_SICKUSED
        defaultLeavesCopyShouldNotBeFound("sickused.greaterThan=" + DEFAULT_SICKUSED);

        // Get all the leavesCopyList where sickused is greater than SMALLER_SICKUSED
        defaultLeavesCopyShouldBeFound("sickused.greaterThan=" + SMALLER_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalnextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotalnextyear equals to DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualtotalnextyear.equals=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where annualtotalnextyear equals to UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualtotalnextyear.equals=" + UPDATED_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalnextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotalnextyear in DEFAULT_ANNUALTOTALNEXTYEAR or UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualtotalnextyear.in=" + DEFAULT_ANNUALTOTALNEXTYEAR + "," + UPDATED_ANNUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where annualtotalnextyear equals to UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualtotalnextyear.in=" + UPDATED_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalnextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotalnextyear is not null
        defaultLeavesCopyShouldBeFound("annualtotalnextyear.specified=true");

        // Get all the leavesCopyList where annualtotalnextyear is null
        defaultLeavesCopyShouldNotBeFound("annualtotalnextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalnextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotalnextyear is greater than or equal to DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualtotalnextyear.greaterThanOrEqual=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where annualtotalnextyear is greater than or equal to UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualtotalnextyear.greaterThanOrEqual=" + UPDATED_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalnextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotalnextyear is less than or equal to DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualtotalnextyear.lessThanOrEqual=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where annualtotalnextyear is less than or equal to SMALLER_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualtotalnextyear.lessThanOrEqual=" + SMALLER_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalnextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotalnextyear is less than DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualtotalnextyear.lessThan=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where annualtotalnextyear is less than UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualtotalnextyear.lessThan=" + UPDATED_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualtotalnextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualtotalnextyear is greater than DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualtotalnextyear.greaterThan=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where annualtotalnextyear is greater than SMALLER_ANNUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualtotalnextyear.greaterThan=" + SMALLER_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusednextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualusednextyear equals to DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualusednextyear.equals=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where annualusednextyear equals to UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualusednextyear.equals=" + UPDATED_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusednextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualusednextyear in DEFAULT_ANNUALUSEDNEXTYEAR or UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualusednextyear.in=" + DEFAULT_ANNUALUSEDNEXTYEAR + "," + UPDATED_ANNUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where annualusednextyear equals to UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualusednextyear.in=" + UPDATED_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusednextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualusednextyear is not null
        defaultLeavesCopyShouldBeFound("annualusednextyear.specified=true");

        // Get all the leavesCopyList where annualusednextyear is null
        defaultLeavesCopyShouldNotBeFound("annualusednextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusednextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualusednextyear is greater than or equal to DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualusednextyear.greaterThanOrEqual=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where annualusednextyear is greater than or equal to UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualusednextyear.greaterThanOrEqual=" + UPDATED_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusednextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualusednextyear is less than or equal to DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualusednextyear.lessThanOrEqual=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where annualusednextyear is less than or equal to SMALLER_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualusednextyear.lessThanOrEqual=" + SMALLER_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusednextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualusednextyear is less than DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualusednextyear.lessThan=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where annualusednextyear is less than UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualusednextyear.lessThan=" + UPDATED_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByAnnualusednextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where annualusednextyear is greater than DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("annualusednextyear.greaterThan=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where annualusednextyear is greater than SMALLER_ANNUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("annualusednextyear.greaterThan=" + SMALLER_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalnextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotalnextyear equals to DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualtotalnextyear.equals=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where casualtotalnextyear equals to UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualtotalnextyear.equals=" + UPDATED_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalnextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotalnextyear in DEFAULT_CASUALTOTALNEXTYEAR or UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualtotalnextyear.in=" + DEFAULT_CASUALTOTALNEXTYEAR + "," + UPDATED_CASUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where casualtotalnextyear equals to UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualtotalnextyear.in=" + UPDATED_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalnextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotalnextyear is not null
        defaultLeavesCopyShouldBeFound("casualtotalnextyear.specified=true");

        // Get all the leavesCopyList where casualtotalnextyear is null
        defaultLeavesCopyShouldNotBeFound("casualtotalnextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalnextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotalnextyear is greater than or equal to DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualtotalnextyear.greaterThanOrEqual=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where casualtotalnextyear is greater than or equal to UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualtotalnextyear.greaterThanOrEqual=" + UPDATED_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalnextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotalnextyear is less than or equal to DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualtotalnextyear.lessThanOrEqual=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where casualtotalnextyear is less than or equal to SMALLER_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualtotalnextyear.lessThanOrEqual=" + SMALLER_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalnextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotalnextyear is less than DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualtotalnextyear.lessThan=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where casualtotalnextyear is less than UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualtotalnextyear.lessThan=" + UPDATED_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualtotalnextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualtotalnextyear is greater than DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualtotalnextyear.greaterThan=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesCopyList where casualtotalnextyear is greater than SMALLER_CASUALTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualtotalnextyear.greaterThan=" + SMALLER_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusednextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualusednextyear equals to DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualusednextyear.equals=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where casualusednextyear equals to UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualusednextyear.equals=" + UPDATED_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusednextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualusednextyear in DEFAULT_CASUALUSEDNEXTYEAR or UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualusednextyear.in=" + DEFAULT_CASUALUSEDNEXTYEAR + "," + UPDATED_CASUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where casualusednextyear equals to UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualusednextyear.in=" + UPDATED_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusednextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualusednextyear is not null
        defaultLeavesCopyShouldBeFound("casualusednextyear.specified=true");

        // Get all the leavesCopyList where casualusednextyear is null
        defaultLeavesCopyShouldNotBeFound("casualusednextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusednextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualusednextyear is greater than or equal to DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualusednextyear.greaterThanOrEqual=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where casualusednextyear is greater than or equal to UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualusednextyear.greaterThanOrEqual=" + UPDATED_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusednextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualusednextyear is less than or equal to DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualusednextyear.lessThanOrEqual=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where casualusednextyear is less than or equal to SMALLER_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualusednextyear.lessThanOrEqual=" + SMALLER_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusednextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualusednextyear is less than DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualusednextyear.lessThan=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where casualusednextyear is less than UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualusednextyear.lessThan=" + UPDATED_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCasualusednextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where casualusednextyear is greater than DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("casualusednextyear.greaterThan=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesCopyList where casualusednextyear is greater than SMALLER_CASUALUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("casualusednextyear.greaterThan=" + SMALLER_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalnextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotalnextyear equals to DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("sicktotalnextyear.equals=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesCopyList where sicktotalnextyear equals to UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sicktotalnextyear.equals=" + UPDATED_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalnextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotalnextyear in DEFAULT_SICKTOTALNEXTYEAR or UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("sicktotalnextyear.in=" + DEFAULT_SICKTOTALNEXTYEAR + "," + UPDATED_SICKTOTALNEXTYEAR);

        // Get all the leavesCopyList where sicktotalnextyear equals to UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sicktotalnextyear.in=" + UPDATED_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalnextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotalnextyear is not null
        defaultLeavesCopyShouldBeFound("sicktotalnextyear.specified=true");

        // Get all the leavesCopyList where sicktotalnextyear is null
        defaultLeavesCopyShouldNotBeFound("sicktotalnextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalnextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotalnextyear is greater than or equal to DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("sicktotalnextyear.greaterThanOrEqual=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesCopyList where sicktotalnextyear is greater than or equal to UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sicktotalnextyear.greaterThanOrEqual=" + UPDATED_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalnextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotalnextyear is less than or equal to DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("sicktotalnextyear.lessThanOrEqual=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesCopyList where sicktotalnextyear is less than or equal to SMALLER_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sicktotalnextyear.lessThanOrEqual=" + SMALLER_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalnextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotalnextyear is less than DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sicktotalnextyear.lessThan=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesCopyList where sicktotalnextyear is less than UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("sicktotalnextyear.lessThan=" + UPDATED_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySicktotalnextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sicktotalnextyear is greater than DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sicktotalnextyear.greaterThan=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesCopyList where sicktotalnextyear is greater than SMALLER_SICKTOTALNEXTYEAR
        defaultLeavesCopyShouldBeFound("sicktotalnextyear.greaterThan=" + SMALLER_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusednextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickusednextyear equals to DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("sickusednextyear.equals=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesCopyList where sickusednextyear equals to UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sickusednextyear.equals=" + UPDATED_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusednextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickusednextyear in DEFAULT_SICKUSEDNEXTYEAR or UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("sickusednextyear.in=" + DEFAULT_SICKUSEDNEXTYEAR + "," + UPDATED_SICKUSEDNEXTYEAR);

        // Get all the leavesCopyList where sickusednextyear equals to UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sickusednextyear.in=" + UPDATED_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusednextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickusednextyear is not null
        defaultLeavesCopyShouldBeFound("sickusednextyear.specified=true");

        // Get all the leavesCopyList where sickusednextyear is null
        defaultLeavesCopyShouldNotBeFound("sickusednextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusednextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickusednextyear is greater than or equal to DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("sickusednextyear.greaterThanOrEqual=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesCopyList where sickusednextyear is greater than or equal to UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sickusednextyear.greaterThanOrEqual=" + UPDATED_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusednextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickusednextyear is less than or equal to DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("sickusednextyear.lessThanOrEqual=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesCopyList where sickusednextyear is less than or equal to SMALLER_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sickusednextyear.lessThanOrEqual=" + SMALLER_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusednextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickusednextyear is less than DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sickusednextyear.lessThan=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesCopyList where sickusednextyear is less than UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("sickusednextyear.lessThan=" + UPDATED_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesBySickusednextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where sickusednextyear is greater than DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldNotBeFound("sickusednextyear.greaterThan=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesCopyList where sickusednextyear is greater than SMALLER_SICKUSEDNEXTYEAR
        defaultLeavesCopyShouldBeFound("sickusednextyear.greaterThan=" + SMALLER_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCarryforwardIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where carryforward equals to DEFAULT_CARRYFORWARD
        defaultLeavesCopyShouldBeFound("carryforward.equals=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesCopyList where carryforward equals to UPDATED_CARRYFORWARD
        defaultLeavesCopyShouldNotBeFound("carryforward.equals=" + UPDATED_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCarryforwardIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where carryforward in DEFAULT_CARRYFORWARD or UPDATED_CARRYFORWARD
        defaultLeavesCopyShouldBeFound("carryforward.in=" + DEFAULT_CARRYFORWARD + "," + UPDATED_CARRYFORWARD);

        // Get all the leavesCopyList where carryforward equals to UPDATED_CARRYFORWARD
        defaultLeavesCopyShouldNotBeFound("carryforward.in=" + UPDATED_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCarryforwardIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where carryforward is not null
        defaultLeavesCopyShouldBeFound("carryforward.specified=true");

        // Get all the leavesCopyList where carryforward is null
        defaultLeavesCopyShouldNotBeFound("carryforward.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCarryforwardIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where carryforward is greater than or equal to DEFAULT_CARRYFORWARD
        defaultLeavesCopyShouldBeFound("carryforward.greaterThanOrEqual=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesCopyList where carryforward is greater than or equal to UPDATED_CARRYFORWARD
        defaultLeavesCopyShouldNotBeFound("carryforward.greaterThanOrEqual=" + UPDATED_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCarryforwardIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where carryforward is less than or equal to DEFAULT_CARRYFORWARD
        defaultLeavesCopyShouldBeFound("carryforward.lessThanOrEqual=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesCopyList where carryforward is less than or equal to SMALLER_CARRYFORWARD
        defaultLeavesCopyShouldNotBeFound("carryforward.lessThanOrEqual=" + SMALLER_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCarryforwardIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where carryforward is less than DEFAULT_CARRYFORWARD
        defaultLeavesCopyShouldNotBeFound("carryforward.lessThan=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesCopyList where carryforward is less than UPDATED_CARRYFORWARD
        defaultLeavesCopyShouldBeFound("carryforward.lessThan=" + UPDATED_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCarryforwardIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where carryforward is greater than DEFAULT_CARRYFORWARD
        defaultLeavesCopyShouldNotBeFound("carryforward.greaterThan=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesCopyList where carryforward is greater than SMALLER_CARRYFORWARD
        defaultLeavesCopyShouldBeFound("carryforward.greaterThan=" + SMALLER_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where createdat equals to DEFAULT_CREATEDAT
        defaultLeavesCopyShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the leavesCopyList where createdat equals to UPDATED_CREATEDAT
        defaultLeavesCopyShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultLeavesCopyShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the leavesCopyList where createdat equals to UPDATED_CREATEDAT
        defaultLeavesCopyShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where createdat is not null
        defaultLeavesCopyShouldBeFound("createdat.specified=true");

        // Get all the leavesCopyList where createdat is null
        defaultLeavesCopyShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where updatedat equals to DEFAULT_UPDATEDAT
        defaultLeavesCopyShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the leavesCopyList where updatedat equals to UPDATED_UPDATEDAT
        defaultLeavesCopyShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultLeavesCopyShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the leavesCopyList where updatedat equals to UPDATED_UPDATEDAT
        defaultLeavesCopyShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLeavesCopiesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        // Get all the leavesCopyList where updatedat is not null
        defaultLeavesCopyShouldBeFound("updatedat.specified=true");

        // Get all the leavesCopyList where updatedat is null
        defaultLeavesCopyShouldNotBeFound("updatedat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeavesCopyShouldBeFound(String filter) throws Exception {
        restLeavesCopyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leavesCopy.getId().intValue())))
            .andExpect(jsonPath("$.[*].annualtotal").value(hasItem(sameNumber(DEFAULT_ANNUALTOTAL))))
            .andExpect(jsonPath("$.[*].annualused").value(hasItem(sameNumber(DEFAULT_ANNUALUSED))))
            .andExpect(jsonPath("$.[*].annualadjustments").value(hasItem(sameNumber(DEFAULT_ANNUALADJUSTMENTS))))
            .andExpect(jsonPath("$.[*].casualtotal").value(hasItem(sameNumber(DEFAULT_CASUALTOTAL))))
            .andExpect(jsonPath("$.[*].casualused").value(hasItem(sameNumber(DEFAULT_CASUALUSED))))
            .andExpect(jsonPath("$.[*].sicktotal").value(hasItem(sameNumber(DEFAULT_SICKTOTAL))))
            .andExpect(jsonPath("$.[*].sickused").value(hasItem(sameNumber(DEFAULT_SICKUSED))))
            .andExpect(jsonPath("$.[*].annualtotalnextyear").value(hasItem(sameNumber(DEFAULT_ANNUALTOTALNEXTYEAR))))
            .andExpect(jsonPath("$.[*].annualusednextyear").value(hasItem(sameNumber(DEFAULT_ANNUALUSEDNEXTYEAR))))
            .andExpect(jsonPath("$.[*].casualtotalnextyear").value(hasItem(sameNumber(DEFAULT_CASUALTOTALNEXTYEAR))))
            .andExpect(jsonPath("$.[*].casualusednextyear").value(hasItem(sameNumber(DEFAULT_CASUALUSEDNEXTYEAR))))
            .andExpect(jsonPath("$.[*].sicktotalnextyear").value(hasItem(sameNumber(DEFAULT_SICKTOTALNEXTYEAR))))
            .andExpect(jsonPath("$.[*].sickusednextyear").value(hasItem(sameNumber(DEFAULT_SICKUSEDNEXTYEAR))))
            .andExpect(jsonPath("$.[*].carryforward").value(hasItem(sameNumber(DEFAULT_CARRYFORWARD))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restLeavesCopyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeavesCopyShouldNotBeFound(String filter) throws Exception {
        restLeavesCopyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeavesCopyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeavesCopy() throws Exception {
        // Get the leavesCopy
        restLeavesCopyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeavesCopy() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();

        // Update the leavesCopy
        LeavesCopy updatedLeavesCopy = leavesCopyRepository.findById(leavesCopy.getId()).get();
        // Disconnect from session so that the updates on updatedLeavesCopy are not directly saved in db
        em.detach(updatedLeavesCopy);
        updatedLeavesCopy
            .annualtotal(UPDATED_ANNUALTOTAL)
            .annualused(UPDATED_ANNUALUSED)
            .annualadjustments(UPDATED_ANNUALADJUSTMENTS)
            .casualtotal(UPDATED_CASUALTOTAL)
            .casualused(UPDATED_CASUALUSED)
            .sicktotal(UPDATED_SICKTOTAL)
            .sickused(UPDATED_SICKUSED)
            .annualtotalnextyear(UPDATED_ANNUALTOTALNEXTYEAR)
            .annualusednextyear(UPDATED_ANNUALUSEDNEXTYEAR)
            .casualtotalnextyear(UPDATED_CASUALTOTALNEXTYEAR)
            .casualusednextyear(UPDATED_CASUALUSEDNEXTYEAR)
            .sicktotalnextyear(UPDATED_SICKTOTALNEXTYEAR)
            .sickusednextyear(UPDATED_SICKUSEDNEXTYEAR)
            .carryforward(UPDATED_CARRYFORWARD)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restLeavesCopyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeavesCopy.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeavesCopy))
            )
            .andExpect(status().isOk());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
        LeavesCopy testLeavesCopy = leavesCopyList.get(leavesCopyList.size() - 1);
        assertThat(testLeavesCopy.getAnnualtotal()).isEqualByComparingTo(UPDATED_ANNUALTOTAL);
        assertThat(testLeavesCopy.getAnnualused()).isEqualByComparingTo(UPDATED_ANNUALUSED);
        assertThat(testLeavesCopy.getAnnualadjustments()).isEqualByComparingTo(UPDATED_ANNUALADJUSTMENTS);
        assertThat(testLeavesCopy.getCasualtotal()).isEqualByComparingTo(UPDATED_CASUALTOTAL);
        assertThat(testLeavesCopy.getCasualused()).isEqualByComparingTo(UPDATED_CASUALUSED);
        assertThat(testLeavesCopy.getSicktotal()).isEqualByComparingTo(UPDATED_SICKTOTAL);
        assertThat(testLeavesCopy.getSickused()).isEqualByComparingTo(UPDATED_SICKUSED);
        assertThat(testLeavesCopy.getAnnualtotalnextyear()).isEqualByComparingTo(UPDATED_ANNUALTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getAnnualusednextyear()).isEqualByComparingTo(UPDATED_ANNUALUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getCasualtotalnextyear()).isEqualByComparingTo(UPDATED_CASUALTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getCasualusednextyear()).isEqualByComparingTo(UPDATED_CASUALUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getSicktotalnextyear()).isEqualByComparingTo(UPDATED_SICKTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getSickusednextyear()).isEqualByComparingTo(UPDATED_SICKUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getCarryforward()).isEqualByComparingTo(UPDATED_CARRYFORWARD);
        assertThat(testLeavesCopy.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeavesCopy.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingLeavesCopy() throws Exception {
        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();
        leavesCopy.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeavesCopyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leavesCopy.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeavesCopy() throws Exception {
        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();
        leavesCopy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesCopyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeavesCopy() throws Exception {
        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();
        leavesCopy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesCopyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeavesCopyWithPatch() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();

        // Update the leavesCopy using partial update
        LeavesCopy partialUpdatedLeavesCopy = new LeavesCopy();
        partialUpdatedLeavesCopy.setId(leavesCopy.getId());

        partialUpdatedLeavesCopy
            .annualtotal(UPDATED_ANNUALTOTAL)
            .annualused(UPDATED_ANNUALUSED)
            .casualtotal(UPDATED_CASUALTOTAL)
            .casualused(UPDATED_CASUALUSED)
            .sicktotal(UPDATED_SICKTOTAL)
            .sickused(UPDATED_SICKUSED)
            .casualtotalnextyear(UPDATED_CASUALTOTALNEXTYEAR)
            .sicktotalnextyear(UPDATED_SICKTOTALNEXTYEAR)
            .sickusednextyear(UPDATED_SICKUSEDNEXTYEAR)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restLeavesCopyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeavesCopy.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeavesCopy))
            )
            .andExpect(status().isOk());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
        LeavesCopy testLeavesCopy = leavesCopyList.get(leavesCopyList.size() - 1);
        assertThat(testLeavesCopy.getAnnualtotal()).isEqualByComparingTo(UPDATED_ANNUALTOTAL);
        assertThat(testLeavesCopy.getAnnualused()).isEqualByComparingTo(UPDATED_ANNUALUSED);
        assertThat(testLeavesCopy.getAnnualadjustments()).isEqualByComparingTo(DEFAULT_ANNUALADJUSTMENTS);
        assertThat(testLeavesCopy.getCasualtotal()).isEqualByComparingTo(UPDATED_CASUALTOTAL);
        assertThat(testLeavesCopy.getCasualused()).isEqualByComparingTo(UPDATED_CASUALUSED);
        assertThat(testLeavesCopy.getSicktotal()).isEqualByComparingTo(UPDATED_SICKTOTAL);
        assertThat(testLeavesCopy.getSickused()).isEqualByComparingTo(UPDATED_SICKUSED);
        assertThat(testLeavesCopy.getAnnualtotalnextyear()).isEqualByComparingTo(DEFAULT_ANNUALTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getAnnualusednextyear()).isEqualByComparingTo(DEFAULT_ANNUALUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getCasualtotalnextyear()).isEqualByComparingTo(UPDATED_CASUALTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getCasualusednextyear()).isEqualByComparingTo(DEFAULT_CASUALUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getSicktotalnextyear()).isEqualByComparingTo(UPDATED_SICKTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getSickusednextyear()).isEqualByComparingTo(UPDATED_SICKUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getCarryforward()).isEqualByComparingTo(DEFAULT_CARRYFORWARD);
        assertThat(testLeavesCopy.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeavesCopy.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateLeavesCopyWithPatch() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();

        // Update the leavesCopy using partial update
        LeavesCopy partialUpdatedLeavesCopy = new LeavesCopy();
        partialUpdatedLeavesCopy.setId(leavesCopy.getId());

        partialUpdatedLeavesCopy
            .annualtotal(UPDATED_ANNUALTOTAL)
            .annualused(UPDATED_ANNUALUSED)
            .annualadjustments(UPDATED_ANNUALADJUSTMENTS)
            .casualtotal(UPDATED_CASUALTOTAL)
            .casualused(UPDATED_CASUALUSED)
            .sicktotal(UPDATED_SICKTOTAL)
            .sickused(UPDATED_SICKUSED)
            .annualtotalnextyear(UPDATED_ANNUALTOTALNEXTYEAR)
            .annualusednextyear(UPDATED_ANNUALUSEDNEXTYEAR)
            .casualtotalnextyear(UPDATED_CASUALTOTALNEXTYEAR)
            .casualusednextyear(UPDATED_CASUALUSEDNEXTYEAR)
            .sicktotalnextyear(UPDATED_SICKTOTALNEXTYEAR)
            .sickusednextyear(UPDATED_SICKUSEDNEXTYEAR)
            .carryforward(UPDATED_CARRYFORWARD)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restLeavesCopyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeavesCopy.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeavesCopy))
            )
            .andExpect(status().isOk());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
        LeavesCopy testLeavesCopy = leavesCopyList.get(leavesCopyList.size() - 1);
        assertThat(testLeavesCopy.getAnnualtotal()).isEqualByComparingTo(UPDATED_ANNUALTOTAL);
        assertThat(testLeavesCopy.getAnnualused()).isEqualByComparingTo(UPDATED_ANNUALUSED);
        assertThat(testLeavesCopy.getAnnualadjustments()).isEqualByComparingTo(UPDATED_ANNUALADJUSTMENTS);
        assertThat(testLeavesCopy.getCasualtotal()).isEqualByComparingTo(UPDATED_CASUALTOTAL);
        assertThat(testLeavesCopy.getCasualused()).isEqualByComparingTo(UPDATED_CASUALUSED);
        assertThat(testLeavesCopy.getSicktotal()).isEqualByComparingTo(UPDATED_SICKTOTAL);
        assertThat(testLeavesCopy.getSickused()).isEqualByComparingTo(UPDATED_SICKUSED);
        assertThat(testLeavesCopy.getAnnualtotalnextyear()).isEqualByComparingTo(UPDATED_ANNUALTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getAnnualusednextyear()).isEqualByComparingTo(UPDATED_ANNUALUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getCasualtotalnextyear()).isEqualByComparingTo(UPDATED_CASUALTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getCasualusednextyear()).isEqualByComparingTo(UPDATED_CASUALUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getSicktotalnextyear()).isEqualByComparingTo(UPDATED_SICKTOTALNEXTYEAR);
        assertThat(testLeavesCopy.getSickusednextyear()).isEqualByComparingTo(UPDATED_SICKUSEDNEXTYEAR);
        assertThat(testLeavesCopy.getCarryforward()).isEqualByComparingTo(UPDATED_CARRYFORWARD);
        assertThat(testLeavesCopy.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeavesCopy.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingLeavesCopy() throws Exception {
        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();
        leavesCopy.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeavesCopyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leavesCopy.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeavesCopy() throws Exception {
        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();
        leavesCopy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesCopyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeavesCopy() throws Exception {
        int databaseSizeBeforeUpdate = leavesCopyRepository.findAll().size();
        leavesCopy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesCopyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leavesCopy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeavesCopy in the database
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeavesCopy() throws Exception {
        // Initialize the database
        leavesCopyRepository.saveAndFlush(leavesCopy);

        int databaseSizeBeforeDelete = leavesCopyRepository.findAll().size();

        // Delete the leavesCopy
        restLeavesCopyMockMvc
            .perform(delete(ENTITY_API_URL_ID, leavesCopy.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeavesCopy> leavesCopyList = leavesCopyRepository.findAll();
        assertThat(leavesCopyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
