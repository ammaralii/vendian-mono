package com.venturedive.vendian_mono.web.rest;

import static com.venturedive.vendian_mono.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.LeavesOlds;
import com.venturedive.vendian_mono.repository.LeavesOldsRepository;
import com.venturedive.vendian_mono.service.criteria.LeavesOldsCriteria;
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
 * Integration tests for the {@link LeavesOldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeavesOldsResourceIT {

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

    private static final String ENTITY_API_URL = "/api/leaves-olds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeavesOldsRepository leavesOldsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeavesOldsMockMvc;

    private LeavesOlds leavesOlds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeavesOlds createEntity(EntityManager em) {
        LeavesOlds leavesOlds = new LeavesOlds()
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
        return leavesOlds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeavesOlds createUpdatedEntity(EntityManager em) {
        LeavesOlds leavesOlds = new LeavesOlds()
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
        return leavesOlds;
    }

    @BeforeEach
    public void initTest() {
        leavesOlds = createEntity(em);
    }

    @Test
    @Transactional
    void createLeavesOlds() throws Exception {
        int databaseSizeBeforeCreate = leavesOldsRepository.findAll().size();
        // Create the LeavesOlds
        restLeavesOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isCreated());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeCreate + 1);
        LeavesOlds testLeavesOlds = leavesOldsList.get(leavesOldsList.size() - 1);
        assertThat(testLeavesOlds.getAnnualtotal()).isEqualByComparingTo(DEFAULT_ANNUALTOTAL);
        assertThat(testLeavesOlds.getAnnualused()).isEqualByComparingTo(DEFAULT_ANNUALUSED);
        assertThat(testLeavesOlds.getAnnualadjustments()).isEqualByComparingTo(DEFAULT_ANNUALADJUSTMENTS);
        assertThat(testLeavesOlds.getCasualtotal()).isEqualByComparingTo(DEFAULT_CASUALTOTAL);
        assertThat(testLeavesOlds.getCasualused()).isEqualByComparingTo(DEFAULT_CASUALUSED);
        assertThat(testLeavesOlds.getSicktotal()).isEqualByComparingTo(DEFAULT_SICKTOTAL);
        assertThat(testLeavesOlds.getSickused()).isEqualByComparingTo(DEFAULT_SICKUSED);
        assertThat(testLeavesOlds.getAnnualtotalnextyear()).isEqualByComparingTo(DEFAULT_ANNUALTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getAnnualusednextyear()).isEqualByComparingTo(DEFAULT_ANNUALUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getCasualtotalnextyear()).isEqualByComparingTo(DEFAULT_CASUALTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getCasualusednextyear()).isEqualByComparingTo(DEFAULT_CASUALUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getSicktotalnextyear()).isEqualByComparingTo(DEFAULT_SICKTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getSickusednextyear()).isEqualByComparingTo(DEFAULT_SICKUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getCarryforward()).isEqualByComparingTo(DEFAULT_CARRYFORWARD);
        assertThat(testLeavesOlds.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testLeavesOlds.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createLeavesOldsWithExistingId() throws Exception {
        // Create the LeavesOlds with an existing ID
        leavesOlds.setId(1L);

        int databaseSizeBeforeCreate = leavesOldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeavesOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = leavesOldsRepository.findAll().size();
        // set the field null
        leavesOlds.setCreatedat(null);

        // Create the LeavesOlds, which fails.

        restLeavesOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isBadRequest());

        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = leavesOldsRepository.findAll().size();
        // set the field null
        leavesOlds.setUpdatedat(null);

        // Create the LeavesOlds, which fails.

        restLeavesOldsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isBadRequest());

        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeavesOlds() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList
        restLeavesOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leavesOlds.getId().intValue())))
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
    void getLeavesOlds() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get the leavesOlds
        restLeavesOldsMockMvc
            .perform(get(ENTITY_API_URL_ID, leavesOlds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leavesOlds.getId().intValue()))
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
    void getLeavesOldsByIdFiltering() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        Long id = leavesOlds.getId();

        defaultLeavesOldsShouldBeFound("id.equals=" + id);
        defaultLeavesOldsShouldNotBeFound("id.notEquals=" + id);

        defaultLeavesOldsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeavesOldsShouldNotBeFound("id.greaterThan=" + id);

        defaultLeavesOldsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeavesOldsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotal equals to DEFAULT_ANNUALTOTAL
        defaultLeavesOldsShouldBeFound("annualtotal.equals=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesOldsList where annualtotal equals to UPDATED_ANNUALTOTAL
        defaultLeavesOldsShouldNotBeFound("annualtotal.equals=" + UPDATED_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotal in DEFAULT_ANNUALTOTAL or UPDATED_ANNUALTOTAL
        defaultLeavesOldsShouldBeFound("annualtotal.in=" + DEFAULT_ANNUALTOTAL + "," + UPDATED_ANNUALTOTAL);

        // Get all the leavesOldsList where annualtotal equals to UPDATED_ANNUALTOTAL
        defaultLeavesOldsShouldNotBeFound("annualtotal.in=" + UPDATED_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotal is not null
        defaultLeavesOldsShouldBeFound("annualtotal.specified=true");

        // Get all the leavesOldsList where annualtotal is null
        defaultLeavesOldsShouldNotBeFound("annualtotal.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotal is greater than or equal to DEFAULT_ANNUALTOTAL
        defaultLeavesOldsShouldBeFound("annualtotal.greaterThanOrEqual=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesOldsList where annualtotal is greater than or equal to UPDATED_ANNUALTOTAL
        defaultLeavesOldsShouldNotBeFound("annualtotal.greaterThanOrEqual=" + UPDATED_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotal is less than or equal to DEFAULT_ANNUALTOTAL
        defaultLeavesOldsShouldBeFound("annualtotal.lessThanOrEqual=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesOldsList where annualtotal is less than or equal to SMALLER_ANNUALTOTAL
        defaultLeavesOldsShouldNotBeFound("annualtotal.lessThanOrEqual=" + SMALLER_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotal is less than DEFAULT_ANNUALTOTAL
        defaultLeavesOldsShouldNotBeFound("annualtotal.lessThan=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesOldsList where annualtotal is less than UPDATED_ANNUALTOTAL
        defaultLeavesOldsShouldBeFound("annualtotal.lessThan=" + UPDATED_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotal is greater than DEFAULT_ANNUALTOTAL
        defaultLeavesOldsShouldNotBeFound("annualtotal.greaterThan=" + DEFAULT_ANNUALTOTAL);

        // Get all the leavesOldsList where annualtotal is greater than SMALLER_ANNUALTOTAL
        defaultLeavesOldsShouldBeFound("annualtotal.greaterThan=" + SMALLER_ANNUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusedIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualused equals to DEFAULT_ANNUALUSED
        defaultLeavesOldsShouldBeFound("annualused.equals=" + DEFAULT_ANNUALUSED);

        // Get all the leavesOldsList where annualused equals to UPDATED_ANNUALUSED
        defaultLeavesOldsShouldNotBeFound("annualused.equals=" + UPDATED_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusedIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualused in DEFAULT_ANNUALUSED or UPDATED_ANNUALUSED
        defaultLeavesOldsShouldBeFound("annualused.in=" + DEFAULT_ANNUALUSED + "," + UPDATED_ANNUALUSED);

        // Get all the leavesOldsList where annualused equals to UPDATED_ANNUALUSED
        defaultLeavesOldsShouldNotBeFound("annualused.in=" + UPDATED_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualused is not null
        defaultLeavesOldsShouldBeFound("annualused.specified=true");

        // Get all the leavesOldsList where annualused is null
        defaultLeavesOldsShouldNotBeFound("annualused.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualused is greater than or equal to DEFAULT_ANNUALUSED
        defaultLeavesOldsShouldBeFound("annualused.greaterThanOrEqual=" + DEFAULT_ANNUALUSED);

        // Get all the leavesOldsList where annualused is greater than or equal to UPDATED_ANNUALUSED
        defaultLeavesOldsShouldNotBeFound("annualused.greaterThanOrEqual=" + UPDATED_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualused is less than or equal to DEFAULT_ANNUALUSED
        defaultLeavesOldsShouldBeFound("annualused.lessThanOrEqual=" + DEFAULT_ANNUALUSED);

        // Get all the leavesOldsList where annualused is less than or equal to SMALLER_ANNUALUSED
        defaultLeavesOldsShouldNotBeFound("annualused.lessThanOrEqual=" + SMALLER_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusedIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualused is less than DEFAULT_ANNUALUSED
        defaultLeavesOldsShouldNotBeFound("annualused.lessThan=" + DEFAULT_ANNUALUSED);

        // Get all the leavesOldsList where annualused is less than UPDATED_ANNUALUSED
        defaultLeavesOldsShouldBeFound("annualused.lessThan=" + UPDATED_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualused is greater than DEFAULT_ANNUALUSED
        defaultLeavesOldsShouldNotBeFound("annualused.greaterThan=" + DEFAULT_ANNUALUSED);

        // Get all the leavesOldsList where annualused is greater than SMALLER_ANNUALUSED
        defaultLeavesOldsShouldBeFound("annualused.greaterThan=" + SMALLER_ANNUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualadjustmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualadjustments equals to DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldBeFound("annualadjustments.equals=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesOldsList where annualadjustments equals to UPDATED_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldNotBeFound("annualadjustments.equals=" + UPDATED_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualadjustmentsIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualadjustments in DEFAULT_ANNUALADJUSTMENTS or UPDATED_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldBeFound("annualadjustments.in=" + DEFAULT_ANNUALADJUSTMENTS + "," + UPDATED_ANNUALADJUSTMENTS);

        // Get all the leavesOldsList where annualadjustments equals to UPDATED_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldNotBeFound("annualadjustments.in=" + UPDATED_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualadjustmentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualadjustments is not null
        defaultLeavesOldsShouldBeFound("annualadjustments.specified=true");

        // Get all the leavesOldsList where annualadjustments is null
        defaultLeavesOldsShouldNotBeFound("annualadjustments.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualadjustmentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualadjustments is greater than or equal to DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldBeFound("annualadjustments.greaterThanOrEqual=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesOldsList where annualadjustments is greater than or equal to UPDATED_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldNotBeFound("annualadjustments.greaterThanOrEqual=" + UPDATED_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualadjustmentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualadjustments is less than or equal to DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldBeFound("annualadjustments.lessThanOrEqual=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesOldsList where annualadjustments is less than or equal to SMALLER_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldNotBeFound("annualadjustments.lessThanOrEqual=" + SMALLER_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualadjustmentsIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualadjustments is less than DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldNotBeFound("annualadjustments.lessThan=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesOldsList where annualadjustments is less than UPDATED_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldBeFound("annualadjustments.lessThan=" + UPDATED_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualadjustmentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualadjustments is greater than DEFAULT_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldNotBeFound("annualadjustments.greaterThan=" + DEFAULT_ANNUALADJUSTMENTS);

        // Get all the leavesOldsList where annualadjustments is greater than SMALLER_ANNUALADJUSTMENTS
        defaultLeavesOldsShouldBeFound("annualadjustments.greaterThan=" + SMALLER_ANNUALADJUSTMENTS);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotal equals to DEFAULT_CASUALTOTAL
        defaultLeavesOldsShouldBeFound("casualtotal.equals=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesOldsList where casualtotal equals to UPDATED_CASUALTOTAL
        defaultLeavesOldsShouldNotBeFound("casualtotal.equals=" + UPDATED_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotal in DEFAULT_CASUALTOTAL or UPDATED_CASUALTOTAL
        defaultLeavesOldsShouldBeFound("casualtotal.in=" + DEFAULT_CASUALTOTAL + "," + UPDATED_CASUALTOTAL);

        // Get all the leavesOldsList where casualtotal equals to UPDATED_CASUALTOTAL
        defaultLeavesOldsShouldNotBeFound("casualtotal.in=" + UPDATED_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotal is not null
        defaultLeavesOldsShouldBeFound("casualtotal.specified=true");

        // Get all the leavesOldsList where casualtotal is null
        defaultLeavesOldsShouldNotBeFound("casualtotal.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotal is greater than or equal to DEFAULT_CASUALTOTAL
        defaultLeavesOldsShouldBeFound("casualtotal.greaterThanOrEqual=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesOldsList where casualtotal is greater than or equal to UPDATED_CASUALTOTAL
        defaultLeavesOldsShouldNotBeFound("casualtotal.greaterThanOrEqual=" + UPDATED_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotal is less than or equal to DEFAULT_CASUALTOTAL
        defaultLeavesOldsShouldBeFound("casualtotal.lessThanOrEqual=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesOldsList where casualtotal is less than or equal to SMALLER_CASUALTOTAL
        defaultLeavesOldsShouldNotBeFound("casualtotal.lessThanOrEqual=" + SMALLER_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotal is less than DEFAULT_CASUALTOTAL
        defaultLeavesOldsShouldNotBeFound("casualtotal.lessThan=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesOldsList where casualtotal is less than UPDATED_CASUALTOTAL
        defaultLeavesOldsShouldBeFound("casualtotal.lessThan=" + UPDATED_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotal is greater than DEFAULT_CASUALTOTAL
        defaultLeavesOldsShouldNotBeFound("casualtotal.greaterThan=" + DEFAULT_CASUALTOTAL);

        // Get all the leavesOldsList where casualtotal is greater than SMALLER_CASUALTOTAL
        defaultLeavesOldsShouldBeFound("casualtotal.greaterThan=" + SMALLER_CASUALTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusedIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualused equals to DEFAULT_CASUALUSED
        defaultLeavesOldsShouldBeFound("casualused.equals=" + DEFAULT_CASUALUSED);

        // Get all the leavesOldsList where casualused equals to UPDATED_CASUALUSED
        defaultLeavesOldsShouldNotBeFound("casualused.equals=" + UPDATED_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusedIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualused in DEFAULT_CASUALUSED or UPDATED_CASUALUSED
        defaultLeavesOldsShouldBeFound("casualused.in=" + DEFAULT_CASUALUSED + "," + UPDATED_CASUALUSED);

        // Get all the leavesOldsList where casualused equals to UPDATED_CASUALUSED
        defaultLeavesOldsShouldNotBeFound("casualused.in=" + UPDATED_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualused is not null
        defaultLeavesOldsShouldBeFound("casualused.specified=true");

        // Get all the leavesOldsList where casualused is null
        defaultLeavesOldsShouldNotBeFound("casualused.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualused is greater than or equal to DEFAULT_CASUALUSED
        defaultLeavesOldsShouldBeFound("casualused.greaterThanOrEqual=" + DEFAULT_CASUALUSED);

        // Get all the leavesOldsList where casualused is greater than or equal to UPDATED_CASUALUSED
        defaultLeavesOldsShouldNotBeFound("casualused.greaterThanOrEqual=" + UPDATED_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualused is less than or equal to DEFAULT_CASUALUSED
        defaultLeavesOldsShouldBeFound("casualused.lessThanOrEqual=" + DEFAULT_CASUALUSED);

        // Get all the leavesOldsList where casualused is less than or equal to SMALLER_CASUALUSED
        defaultLeavesOldsShouldNotBeFound("casualused.lessThanOrEqual=" + SMALLER_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusedIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualused is less than DEFAULT_CASUALUSED
        defaultLeavesOldsShouldNotBeFound("casualused.lessThan=" + DEFAULT_CASUALUSED);

        // Get all the leavesOldsList where casualused is less than UPDATED_CASUALUSED
        defaultLeavesOldsShouldBeFound("casualused.lessThan=" + UPDATED_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualused is greater than DEFAULT_CASUALUSED
        defaultLeavesOldsShouldNotBeFound("casualused.greaterThan=" + DEFAULT_CASUALUSED);

        // Get all the leavesOldsList where casualused is greater than SMALLER_CASUALUSED
        defaultLeavesOldsShouldBeFound("casualused.greaterThan=" + SMALLER_CASUALUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotal equals to DEFAULT_SICKTOTAL
        defaultLeavesOldsShouldBeFound("sicktotal.equals=" + DEFAULT_SICKTOTAL);

        // Get all the leavesOldsList where sicktotal equals to UPDATED_SICKTOTAL
        defaultLeavesOldsShouldNotBeFound("sicktotal.equals=" + UPDATED_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotal in DEFAULT_SICKTOTAL or UPDATED_SICKTOTAL
        defaultLeavesOldsShouldBeFound("sicktotal.in=" + DEFAULT_SICKTOTAL + "," + UPDATED_SICKTOTAL);

        // Get all the leavesOldsList where sicktotal equals to UPDATED_SICKTOTAL
        defaultLeavesOldsShouldNotBeFound("sicktotal.in=" + UPDATED_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotal is not null
        defaultLeavesOldsShouldBeFound("sicktotal.specified=true");

        // Get all the leavesOldsList where sicktotal is null
        defaultLeavesOldsShouldNotBeFound("sicktotal.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotal is greater than or equal to DEFAULT_SICKTOTAL
        defaultLeavesOldsShouldBeFound("sicktotal.greaterThanOrEqual=" + DEFAULT_SICKTOTAL);

        // Get all the leavesOldsList where sicktotal is greater than or equal to UPDATED_SICKTOTAL
        defaultLeavesOldsShouldNotBeFound("sicktotal.greaterThanOrEqual=" + UPDATED_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotal is less than or equal to DEFAULT_SICKTOTAL
        defaultLeavesOldsShouldBeFound("sicktotal.lessThanOrEqual=" + DEFAULT_SICKTOTAL);

        // Get all the leavesOldsList where sicktotal is less than or equal to SMALLER_SICKTOTAL
        defaultLeavesOldsShouldNotBeFound("sicktotal.lessThanOrEqual=" + SMALLER_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotal is less than DEFAULT_SICKTOTAL
        defaultLeavesOldsShouldNotBeFound("sicktotal.lessThan=" + DEFAULT_SICKTOTAL);

        // Get all the leavesOldsList where sicktotal is less than UPDATED_SICKTOTAL
        defaultLeavesOldsShouldBeFound("sicktotal.lessThan=" + UPDATED_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotal is greater than DEFAULT_SICKTOTAL
        defaultLeavesOldsShouldNotBeFound("sicktotal.greaterThan=" + DEFAULT_SICKTOTAL);

        // Get all the leavesOldsList where sicktotal is greater than SMALLER_SICKTOTAL
        defaultLeavesOldsShouldBeFound("sicktotal.greaterThan=" + SMALLER_SICKTOTAL);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusedIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickused equals to DEFAULT_SICKUSED
        defaultLeavesOldsShouldBeFound("sickused.equals=" + DEFAULT_SICKUSED);

        // Get all the leavesOldsList where sickused equals to UPDATED_SICKUSED
        defaultLeavesOldsShouldNotBeFound("sickused.equals=" + UPDATED_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusedIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickused in DEFAULT_SICKUSED or UPDATED_SICKUSED
        defaultLeavesOldsShouldBeFound("sickused.in=" + DEFAULT_SICKUSED + "," + UPDATED_SICKUSED);

        // Get all the leavesOldsList where sickused equals to UPDATED_SICKUSED
        defaultLeavesOldsShouldNotBeFound("sickused.in=" + UPDATED_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickused is not null
        defaultLeavesOldsShouldBeFound("sickused.specified=true");

        // Get all the leavesOldsList where sickused is null
        defaultLeavesOldsShouldNotBeFound("sickused.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickused is greater than or equal to DEFAULT_SICKUSED
        defaultLeavesOldsShouldBeFound("sickused.greaterThanOrEqual=" + DEFAULT_SICKUSED);

        // Get all the leavesOldsList where sickused is greater than or equal to UPDATED_SICKUSED
        defaultLeavesOldsShouldNotBeFound("sickused.greaterThanOrEqual=" + UPDATED_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickused is less than or equal to DEFAULT_SICKUSED
        defaultLeavesOldsShouldBeFound("sickused.lessThanOrEqual=" + DEFAULT_SICKUSED);

        // Get all the leavesOldsList where sickused is less than or equal to SMALLER_SICKUSED
        defaultLeavesOldsShouldNotBeFound("sickused.lessThanOrEqual=" + SMALLER_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusedIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickused is less than DEFAULT_SICKUSED
        defaultLeavesOldsShouldNotBeFound("sickused.lessThan=" + DEFAULT_SICKUSED);

        // Get all the leavesOldsList where sickused is less than UPDATED_SICKUSED
        defaultLeavesOldsShouldBeFound("sickused.lessThan=" + UPDATED_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickused is greater than DEFAULT_SICKUSED
        defaultLeavesOldsShouldNotBeFound("sickused.greaterThan=" + DEFAULT_SICKUSED);

        // Get all the leavesOldsList where sickused is greater than SMALLER_SICKUSED
        defaultLeavesOldsShouldBeFound("sickused.greaterThan=" + SMALLER_SICKUSED);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalnextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotalnextyear equals to DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualtotalnextyear.equals=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where annualtotalnextyear equals to UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualtotalnextyear.equals=" + UPDATED_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalnextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotalnextyear in DEFAULT_ANNUALTOTALNEXTYEAR or UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualtotalnextyear.in=" + DEFAULT_ANNUALTOTALNEXTYEAR + "," + UPDATED_ANNUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where annualtotalnextyear equals to UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualtotalnextyear.in=" + UPDATED_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalnextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotalnextyear is not null
        defaultLeavesOldsShouldBeFound("annualtotalnextyear.specified=true");

        // Get all the leavesOldsList where annualtotalnextyear is null
        defaultLeavesOldsShouldNotBeFound("annualtotalnextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalnextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotalnextyear is greater than or equal to DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualtotalnextyear.greaterThanOrEqual=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where annualtotalnextyear is greater than or equal to UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualtotalnextyear.greaterThanOrEqual=" + UPDATED_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalnextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotalnextyear is less than or equal to DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualtotalnextyear.lessThanOrEqual=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where annualtotalnextyear is less than or equal to SMALLER_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualtotalnextyear.lessThanOrEqual=" + SMALLER_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalnextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotalnextyear is less than DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualtotalnextyear.lessThan=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where annualtotalnextyear is less than UPDATED_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualtotalnextyear.lessThan=" + UPDATED_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualtotalnextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualtotalnextyear is greater than DEFAULT_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualtotalnextyear.greaterThan=" + DEFAULT_ANNUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where annualtotalnextyear is greater than SMALLER_ANNUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualtotalnextyear.greaterThan=" + SMALLER_ANNUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusednextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualusednextyear equals to DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualusednextyear.equals=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where annualusednextyear equals to UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualusednextyear.equals=" + UPDATED_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusednextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualusednextyear in DEFAULT_ANNUALUSEDNEXTYEAR or UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualusednextyear.in=" + DEFAULT_ANNUALUSEDNEXTYEAR + "," + UPDATED_ANNUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where annualusednextyear equals to UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualusednextyear.in=" + UPDATED_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusednextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualusednextyear is not null
        defaultLeavesOldsShouldBeFound("annualusednextyear.specified=true");

        // Get all the leavesOldsList where annualusednextyear is null
        defaultLeavesOldsShouldNotBeFound("annualusednextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusednextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualusednextyear is greater than or equal to DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualusednextyear.greaterThanOrEqual=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where annualusednextyear is greater than or equal to UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualusednextyear.greaterThanOrEqual=" + UPDATED_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusednextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualusednextyear is less than or equal to DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualusednextyear.lessThanOrEqual=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where annualusednextyear is less than or equal to SMALLER_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualusednextyear.lessThanOrEqual=" + SMALLER_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusednextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualusednextyear is less than DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualusednextyear.lessThan=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where annualusednextyear is less than UPDATED_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualusednextyear.lessThan=" + UPDATED_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByAnnualusednextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where annualusednextyear is greater than DEFAULT_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("annualusednextyear.greaterThan=" + DEFAULT_ANNUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where annualusednextyear is greater than SMALLER_ANNUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("annualusednextyear.greaterThan=" + SMALLER_ANNUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalnextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotalnextyear equals to DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualtotalnextyear.equals=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where casualtotalnextyear equals to UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualtotalnextyear.equals=" + UPDATED_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalnextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotalnextyear in DEFAULT_CASUALTOTALNEXTYEAR or UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualtotalnextyear.in=" + DEFAULT_CASUALTOTALNEXTYEAR + "," + UPDATED_CASUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where casualtotalnextyear equals to UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualtotalnextyear.in=" + UPDATED_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalnextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotalnextyear is not null
        defaultLeavesOldsShouldBeFound("casualtotalnextyear.specified=true");

        // Get all the leavesOldsList where casualtotalnextyear is null
        defaultLeavesOldsShouldNotBeFound("casualtotalnextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalnextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotalnextyear is greater than or equal to DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualtotalnextyear.greaterThanOrEqual=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where casualtotalnextyear is greater than or equal to UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualtotalnextyear.greaterThanOrEqual=" + UPDATED_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalnextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotalnextyear is less than or equal to DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualtotalnextyear.lessThanOrEqual=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where casualtotalnextyear is less than or equal to SMALLER_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualtotalnextyear.lessThanOrEqual=" + SMALLER_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalnextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotalnextyear is less than DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualtotalnextyear.lessThan=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where casualtotalnextyear is less than UPDATED_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualtotalnextyear.lessThan=" + UPDATED_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualtotalnextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualtotalnextyear is greater than DEFAULT_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualtotalnextyear.greaterThan=" + DEFAULT_CASUALTOTALNEXTYEAR);

        // Get all the leavesOldsList where casualtotalnextyear is greater than SMALLER_CASUALTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualtotalnextyear.greaterThan=" + SMALLER_CASUALTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusednextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualusednextyear equals to DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualusednextyear.equals=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where casualusednextyear equals to UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualusednextyear.equals=" + UPDATED_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusednextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualusednextyear in DEFAULT_CASUALUSEDNEXTYEAR or UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualusednextyear.in=" + DEFAULT_CASUALUSEDNEXTYEAR + "," + UPDATED_CASUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where casualusednextyear equals to UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualusednextyear.in=" + UPDATED_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusednextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualusednextyear is not null
        defaultLeavesOldsShouldBeFound("casualusednextyear.specified=true");

        // Get all the leavesOldsList where casualusednextyear is null
        defaultLeavesOldsShouldNotBeFound("casualusednextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusednextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualusednextyear is greater than or equal to DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualusednextyear.greaterThanOrEqual=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where casualusednextyear is greater than or equal to UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualusednextyear.greaterThanOrEqual=" + UPDATED_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusednextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualusednextyear is less than or equal to DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualusednextyear.lessThanOrEqual=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where casualusednextyear is less than or equal to SMALLER_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualusednextyear.lessThanOrEqual=" + SMALLER_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusednextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualusednextyear is less than DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualusednextyear.lessThan=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where casualusednextyear is less than UPDATED_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualusednextyear.lessThan=" + UPDATED_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCasualusednextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where casualusednextyear is greater than DEFAULT_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("casualusednextyear.greaterThan=" + DEFAULT_CASUALUSEDNEXTYEAR);

        // Get all the leavesOldsList where casualusednextyear is greater than SMALLER_CASUALUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("casualusednextyear.greaterThan=" + SMALLER_CASUALUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalnextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotalnextyear equals to DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("sicktotalnextyear.equals=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesOldsList where sicktotalnextyear equals to UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sicktotalnextyear.equals=" + UPDATED_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalnextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotalnextyear in DEFAULT_SICKTOTALNEXTYEAR or UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("sicktotalnextyear.in=" + DEFAULT_SICKTOTALNEXTYEAR + "," + UPDATED_SICKTOTALNEXTYEAR);

        // Get all the leavesOldsList where sicktotalnextyear equals to UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sicktotalnextyear.in=" + UPDATED_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalnextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotalnextyear is not null
        defaultLeavesOldsShouldBeFound("sicktotalnextyear.specified=true");

        // Get all the leavesOldsList where sicktotalnextyear is null
        defaultLeavesOldsShouldNotBeFound("sicktotalnextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalnextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotalnextyear is greater than or equal to DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("sicktotalnextyear.greaterThanOrEqual=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesOldsList where sicktotalnextyear is greater than or equal to UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sicktotalnextyear.greaterThanOrEqual=" + UPDATED_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalnextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotalnextyear is less than or equal to DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("sicktotalnextyear.lessThanOrEqual=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesOldsList where sicktotalnextyear is less than or equal to SMALLER_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sicktotalnextyear.lessThanOrEqual=" + SMALLER_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalnextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotalnextyear is less than DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sicktotalnextyear.lessThan=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesOldsList where sicktotalnextyear is less than UPDATED_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("sicktotalnextyear.lessThan=" + UPDATED_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySicktotalnextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sicktotalnextyear is greater than DEFAULT_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sicktotalnextyear.greaterThan=" + DEFAULT_SICKTOTALNEXTYEAR);

        // Get all the leavesOldsList where sicktotalnextyear is greater than SMALLER_SICKTOTALNEXTYEAR
        defaultLeavesOldsShouldBeFound("sicktotalnextyear.greaterThan=" + SMALLER_SICKTOTALNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusednextyearIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickusednextyear equals to DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("sickusednextyear.equals=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesOldsList where sickusednextyear equals to UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sickusednextyear.equals=" + UPDATED_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusednextyearIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickusednextyear in DEFAULT_SICKUSEDNEXTYEAR or UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("sickusednextyear.in=" + DEFAULT_SICKUSEDNEXTYEAR + "," + UPDATED_SICKUSEDNEXTYEAR);

        // Get all the leavesOldsList where sickusednextyear equals to UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sickusednextyear.in=" + UPDATED_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusednextyearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickusednextyear is not null
        defaultLeavesOldsShouldBeFound("sickusednextyear.specified=true");

        // Get all the leavesOldsList where sickusednextyear is null
        defaultLeavesOldsShouldNotBeFound("sickusednextyear.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusednextyearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickusednextyear is greater than or equal to DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("sickusednextyear.greaterThanOrEqual=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesOldsList where sickusednextyear is greater than or equal to UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sickusednextyear.greaterThanOrEqual=" + UPDATED_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusednextyearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickusednextyear is less than or equal to DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("sickusednextyear.lessThanOrEqual=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesOldsList where sickusednextyear is less than or equal to SMALLER_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sickusednextyear.lessThanOrEqual=" + SMALLER_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusednextyearIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickusednextyear is less than DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sickusednextyear.lessThan=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesOldsList where sickusednextyear is less than UPDATED_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("sickusednextyear.lessThan=" + UPDATED_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsBySickusednextyearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where sickusednextyear is greater than DEFAULT_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldNotBeFound("sickusednextyear.greaterThan=" + DEFAULT_SICKUSEDNEXTYEAR);

        // Get all the leavesOldsList where sickusednextyear is greater than SMALLER_SICKUSEDNEXTYEAR
        defaultLeavesOldsShouldBeFound("sickusednextyear.greaterThan=" + SMALLER_SICKUSEDNEXTYEAR);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCarryforwardIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where carryforward equals to DEFAULT_CARRYFORWARD
        defaultLeavesOldsShouldBeFound("carryforward.equals=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesOldsList where carryforward equals to UPDATED_CARRYFORWARD
        defaultLeavesOldsShouldNotBeFound("carryforward.equals=" + UPDATED_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCarryforwardIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where carryforward in DEFAULT_CARRYFORWARD or UPDATED_CARRYFORWARD
        defaultLeavesOldsShouldBeFound("carryforward.in=" + DEFAULT_CARRYFORWARD + "," + UPDATED_CARRYFORWARD);

        // Get all the leavesOldsList where carryforward equals to UPDATED_CARRYFORWARD
        defaultLeavesOldsShouldNotBeFound("carryforward.in=" + UPDATED_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCarryforwardIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where carryforward is not null
        defaultLeavesOldsShouldBeFound("carryforward.specified=true");

        // Get all the leavesOldsList where carryforward is null
        defaultLeavesOldsShouldNotBeFound("carryforward.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCarryforwardIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where carryforward is greater than or equal to DEFAULT_CARRYFORWARD
        defaultLeavesOldsShouldBeFound("carryforward.greaterThanOrEqual=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesOldsList where carryforward is greater than or equal to UPDATED_CARRYFORWARD
        defaultLeavesOldsShouldNotBeFound("carryforward.greaterThanOrEqual=" + UPDATED_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCarryforwardIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where carryforward is less than or equal to DEFAULT_CARRYFORWARD
        defaultLeavesOldsShouldBeFound("carryforward.lessThanOrEqual=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesOldsList where carryforward is less than or equal to SMALLER_CARRYFORWARD
        defaultLeavesOldsShouldNotBeFound("carryforward.lessThanOrEqual=" + SMALLER_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCarryforwardIsLessThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where carryforward is less than DEFAULT_CARRYFORWARD
        defaultLeavesOldsShouldNotBeFound("carryforward.lessThan=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesOldsList where carryforward is less than UPDATED_CARRYFORWARD
        defaultLeavesOldsShouldBeFound("carryforward.lessThan=" + UPDATED_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCarryforwardIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where carryforward is greater than DEFAULT_CARRYFORWARD
        defaultLeavesOldsShouldNotBeFound("carryforward.greaterThan=" + DEFAULT_CARRYFORWARD);

        // Get all the leavesOldsList where carryforward is greater than SMALLER_CARRYFORWARD
        defaultLeavesOldsShouldBeFound("carryforward.greaterThan=" + SMALLER_CARRYFORWARD);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where createdat equals to DEFAULT_CREATEDAT
        defaultLeavesOldsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the leavesOldsList where createdat equals to UPDATED_CREATEDAT
        defaultLeavesOldsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultLeavesOldsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the leavesOldsList where createdat equals to UPDATED_CREATEDAT
        defaultLeavesOldsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where createdat is not null
        defaultLeavesOldsShouldBeFound("createdat.specified=true");

        // Get all the leavesOldsList where createdat is null
        defaultLeavesOldsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultLeavesOldsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the leavesOldsList where updatedat equals to UPDATED_UPDATEDAT
        defaultLeavesOldsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultLeavesOldsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the leavesOldsList where updatedat equals to UPDATED_UPDATEDAT
        defaultLeavesOldsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLeavesOldsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        // Get all the leavesOldsList where updatedat is not null
        defaultLeavesOldsShouldBeFound("updatedat.specified=true");

        // Get all the leavesOldsList where updatedat is null
        defaultLeavesOldsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavesOldsByEmployeesLeaveIsEqualToSomething() throws Exception {
        Employees employeesLeave;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            leavesOldsRepository.saveAndFlush(leavesOlds);
            employeesLeave = EmployeesResourceIT.createEntity(em);
        } else {
            employeesLeave = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesLeave);
        em.flush();
        leavesOlds.addEmployeesLeave(employeesLeave);
        leavesOldsRepository.saveAndFlush(leavesOlds);
        Long employeesLeaveId = employeesLeave.getId();

        // Get all the leavesOldsList where employeesLeave equals to employeesLeaveId
        defaultLeavesOldsShouldBeFound("employeesLeaveId.equals=" + employeesLeaveId);

        // Get all the leavesOldsList where employeesLeave equals to (employeesLeaveId + 1)
        defaultLeavesOldsShouldNotBeFound("employeesLeaveId.equals=" + (employeesLeaveId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeavesOldsShouldBeFound(String filter) throws Exception {
        restLeavesOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leavesOlds.getId().intValue())))
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
        restLeavesOldsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeavesOldsShouldNotBeFound(String filter) throws Exception {
        restLeavesOldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeavesOldsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeavesOlds() throws Exception {
        // Get the leavesOlds
        restLeavesOldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeavesOlds() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();

        // Update the leavesOlds
        LeavesOlds updatedLeavesOlds = leavesOldsRepository.findById(leavesOlds.getId()).get();
        // Disconnect from session so that the updates on updatedLeavesOlds are not directly saved in db
        em.detach(updatedLeavesOlds);
        updatedLeavesOlds
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

        restLeavesOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeavesOlds.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeavesOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
        LeavesOlds testLeavesOlds = leavesOldsList.get(leavesOldsList.size() - 1);
        assertThat(testLeavesOlds.getAnnualtotal()).isEqualByComparingTo(UPDATED_ANNUALTOTAL);
        assertThat(testLeavesOlds.getAnnualused()).isEqualByComparingTo(UPDATED_ANNUALUSED);
        assertThat(testLeavesOlds.getAnnualadjustments()).isEqualByComparingTo(UPDATED_ANNUALADJUSTMENTS);
        assertThat(testLeavesOlds.getCasualtotal()).isEqualByComparingTo(UPDATED_CASUALTOTAL);
        assertThat(testLeavesOlds.getCasualused()).isEqualByComparingTo(UPDATED_CASUALUSED);
        assertThat(testLeavesOlds.getSicktotal()).isEqualByComparingTo(UPDATED_SICKTOTAL);
        assertThat(testLeavesOlds.getSickused()).isEqualByComparingTo(UPDATED_SICKUSED);
        assertThat(testLeavesOlds.getAnnualtotalnextyear()).isEqualByComparingTo(UPDATED_ANNUALTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getAnnualusednextyear()).isEqualByComparingTo(UPDATED_ANNUALUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getCasualtotalnextyear()).isEqualByComparingTo(UPDATED_CASUALTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getCasualusednextyear()).isEqualByComparingTo(UPDATED_CASUALUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getSicktotalnextyear()).isEqualByComparingTo(UPDATED_SICKTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getSickusednextyear()).isEqualByComparingTo(UPDATED_SICKUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getCarryforward()).isEqualByComparingTo(UPDATED_CARRYFORWARD);
        assertThat(testLeavesOlds.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeavesOlds.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingLeavesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();
        leavesOlds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeavesOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leavesOlds.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeavesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();
        leavesOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesOldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeavesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();
        leavesOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesOldsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeavesOldsWithPatch() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();

        // Update the leavesOlds using partial update
        LeavesOlds partialUpdatedLeavesOlds = new LeavesOlds();
        partialUpdatedLeavesOlds.setId(leavesOlds.getId());

        partialUpdatedLeavesOlds
            .annualtotal(UPDATED_ANNUALTOTAL)
            .annualadjustments(UPDATED_ANNUALADJUSTMENTS)
            .casualtotal(UPDATED_CASUALTOTAL)
            .casualused(UPDATED_CASUALUSED)
            .sicktotal(UPDATED_SICKTOTAL)
            .sickused(UPDATED_SICKUSED)
            .annualtotalnextyear(UPDATED_ANNUALTOTALNEXTYEAR)
            .casualtotalnextyear(UPDATED_CASUALTOTALNEXTYEAR)
            .sicktotalnextyear(UPDATED_SICKTOTALNEXTYEAR)
            .carryforward(UPDATED_CARRYFORWARD)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restLeavesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeavesOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeavesOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
        LeavesOlds testLeavesOlds = leavesOldsList.get(leavesOldsList.size() - 1);
        assertThat(testLeavesOlds.getAnnualtotal()).isEqualByComparingTo(UPDATED_ANNUALTOTAL);
        assertThat(testLeavesOlds.getAnnualused()).isEqualByComparingTo(DEFAULT_ANNUALUSED);
        assertThat(testLeavesOlds.getAnnualadjustments()).isEqualByComparingTo(UPDATED_ANNUALADJUSTMENTS);
        assertThat(testLeavesOlds.getCasualtotal()).isEqualByComparingTo(UPDATED_CASUALTOTAL);
        assertThat(testLeavesOlds.getCasualused()).isEqualByComparingTo(UPDATED_CASUALUSED);
        assertThat(testLeavesOlds.getSicktotal()).isEqualByComparingTo(UPDATED_SICKTOTAL);
        assertThat(testLeavesOlds.getSickused()).isEqualByComparingTo(UPDATED_SICKUSED);
        assertThat(testLeavesOlds.getAnnualtotalnextyear()).isEqualByComparingTo(UPDATED_ANNUALTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getAnnualusednextyear()).isEqualByComparingTo(DEFAULT_ANNUALUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getCasualtotalnextyear()).isEqualByComparingTo(UPDATED_CASUALTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getCasualusednextyear()).isEqualByComparingTo(DEFAULT_CASUALUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getSicktotalnextyear()).isEqualByComparingTo(UPDATED_SICKTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getSickusednextyear()).isEqualByComparingTo(DEFAULT_SICKUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getCarryforward()).isEqualByComparingTo(UPDATED_CARRYFORWARD);
        assertThat(testLeavesOlds.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeavesOlds.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateLeavesOldsWithPatch() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();

        // Update the leavesOlds using partial update
        LeavesOlds partialUpdatedLeavesOlds = new LeavesOlds();
        partialUpdatedLeavesOlds.setId(leavesOlds.getId());

        partialUpdatedLeavesOlds
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

        restLeavesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeavesOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeavesOlds))
            )
            .andExpect(status().isOk());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
        LeavesOlds testLeavesOlds = leavesOldsList.get(leavesOldsList.size() - 1);
        assertThat(testLeavesOlds.getAnnualtotal()).isEqualByComparingTo(UPDATED_ANNUALTOTAL);
        assertThat(testLeavesOlds.getAnnualused()).isEqualByComparingTo(UPDATED_ANNUALUSED);
        assertThat(testLeavesOlds.getAnnualadjustments()).isEqualByComparingTo(UPDATED_ANNUALADJUSTMENTS);
        assertThat(testLeavesOlds.getCasualtotal()).isEqualByComparingTo(UPDATED_CASUALTOTAL);
        assertThat(testLeavesOlds.getCasualused()).isEqualByComparingTo(UPDATED_CASUALUSED);
        assertThat(testLeavesOlds.getSicktotal()).isEqualByComparingTo(UPDATED_SICKTOTAL);
        assertThat(testLeavesOlds.getSickused()).isEqualByComparingTo(UPDATED_SICKUSED);
        assertThat(testLeavesOlds.getAnnualtotalnextyear()).isEqualByComparingTo(UPDATED_ANNUALTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getAnnualusednextyear()).isEqualByComparingTo(UPDATED_ANNUALUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getCasualtotalnextyear()).isEqualByComparingTo(UPDATED_CASUALTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getCasualusednextyear()).isEqualByComparingTo(UPDATED_CASUALUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getSicktotalnextyear()).isEqualByComparingTo(UPDATED_SICKTOTALNEXTYEAR);
        assertThat(testLeavesOlds.getSickusednextyear()).isEqualByComparingTo(UPDATED_SICKUSEDNEXTYEAR);
        assertThat(testLeavesOlds.getCarryforward()).isEqualByComparingTo(UPDATED_CARRYFORWARD);
        assertThat(testLeavesOlds.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLeavesOlds.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingLeavesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();
        leavesOlds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeavesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leavesOlds.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeavesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();
        leavesOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeavesOlds() throws Exception {
        int databaseSizeBeforeUpdate = leavesOldsRepository.findAll().size();
        leavesOlds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavesOldsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leavesOlds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeavesOlds in the database
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeavesOlds() throws Exception {
        // Initialize the database
        leavesOldsRepository.saveAndFlush(leavesOlds);

        int databaseSizeBeforeDelete = leavesOldsRepository.findAll().size();

        // Delete the leavesOlds
        restLeavesOldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, leavesOlds.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeavesOlds> leavesOldsList = leavesOldsRepository.findAll();
        assertThat(leavesOldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
