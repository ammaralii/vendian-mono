package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Benefits;
import com.venturedive.vendian_mono.domain.CompensationBenefits;
import com.venturedive.vendian_mono.repository.BenefitsRepository;
import com.venturedive.vendian_mono.service.criteria.BenefitsCriteria;
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
 * Integration tests for the {@link BenefitsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenefitsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISDELETED = false;
    private static final Boolean UPDATED_ISDELETED = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/benefits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BenefitsRepository benefitsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenefitsMockMvc;

    private Benefits benefits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benefits createEntity(EntityManager em) {
        Benefits benefits = new Benefits()
            .name(DEFAULT_NAME)
            .isdeleted(DEFAULT_ISDELETED)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return benefits;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benefits createUpdatedEntity(EntityManager em) {
        Benefits benefits = new Benefits()
            .name(UPDATED_NAME)
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return benefits;
    }

    @BeforeEach
    public void initTest() {
        benefits = createEntity(em);
    }

    @Test
    @Transactional
    void createBenefits() throws Exception {
        int databaseSizeBeforeCreate = benefitsRepository.findAll().size();
        // Create the Benefits
        restBenefitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isCreated());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeCreate + 1);
        Benefits testBenefits = benefitsList.get(benefitsList.size() - 1);
        assertThat(testBenefits.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefits.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testBenefits.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testBenefits.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createBenefitsWithExistingId() throws Exception {
        // Create the Benefits with an existing ID
        benefits.setId(1L);

        int databaseSizeBeforeCreate = benefitsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitsRepository.findAll().size();
        // set the field null
        benefits.setName(null);

        // Create the Benefits, which fails.

        restBenefitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isBadRequest());

        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList
        restBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefits.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get the benefits
        restBenefitsMockMvc
            .perform(get(ENTITY_API_URL_ID, benefits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benefits.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isdeleted").value(DEFAULT_ISDELETED.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getBenefitsByIdFiltering() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        Long id = benefits.getId();

        defaultBenefitsShouldBeFound("id.equals=" + id);
        defaultBenefitsShouldNotBeFound("id.notEquals=" + id);

        defaultBenefitsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBenefitsShouldNotBeFound("id.greaterThan=" + id);

        defaultBenefitsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBenefitsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBenefitsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where name equals to DEFAULT_NAME
        defaultBenefitsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the benefitsList where name equals to UPDATED_NAME
        defaultBenefitsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBenefitsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBenefitsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the benefitsList where name equals to UPDATED_NAME
        defaultBenefitsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBenefitsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where name is not null
        defaultBenefitsShouldBeFound("name.specified=true");

        // Get all the benefitsList where name is null
        defaultBenefitsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBenefitsByNameContainsSomething() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where name contains DEFAULT_NAME
        defaultBenefitsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the benefitsList where name contains UPDATED_NAME
        defaultBenefitsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBenefitsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where name does not contain DEFAULT_NAME
        defaultBenefitsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the benefitsList where name does not contain UPDATED_NAME
        defaultBenefitsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBenefitsByIsdeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where isdeleted equals to DEFAULT_ISDELETED
        defaultBenefitsShouldBeFound("isdeleted.equals=" + DEFAULT_ISDELETED);

        // Get all the benefitsList where isdeleted equals to UPDATED_ISDELETED
        defaultBenefitsShouldNotBeFound("isdeleted.equals=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllBenefitsByIsdeletedIsInShouldWork() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where isdeleted in DEFAULT_ISDELETED or UPDATED_ISDELETED
        defaultBenefitsShouldBeFound("isdeleted.in=" + DEFAULT_ISDELETED + "," + UPDATED_ISDELETED);

        // Get all the benefitsList where isdeleted equals to UPDATED_ISDELETED
        defaultBenefitsShouldNotBeFound("isdeleted.in=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllBenefitsByIsdeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where isdeleted is not null
        defaultBenefitsShouldBeFound("isdeleted.specified=true");

        // Get all the benefitsList where isdeleted is null
        defaultBenefitsShouldNotBeFound("isdeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllBenefitsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where createdat equals to DEFAULT_CREATEDAT
        defaultBenefitsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the benefitsList where createdat equals to UPDATED_CREATEDAT
        defaultBenefitsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllBenefitsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultBenefitsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the benefitsList where createdat equals to UPDATED_CREATEDAT
        defaultBenefitsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllBenefitsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where createdat is not null
        defaultBenefitsShouldBeFound("createdat.specified=true");

        // Get all the benefitsList where createdat is null
        defaultBenefitsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllBenefitsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultBenefitsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the benefitsList where updatedat equals to UPDATED_UPDATEDAT
        defaultBenefitsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllBenefitsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultBenefitsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the benefitsList where updatedat equals to UPDATED_UPDATEDAT
        defaultBenefitsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllBenefitsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList where updatedat is not null
        defaultBenefitsShouldBeFound("updatedat.specified=true");

        // Get all the benefitsList where updatedat is null
        defaultBenefitsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllBenefitsByCompensationbenefitsBenefitIsEqualToSomething() throws Exception {
        CompensationBenefits compensationbenefitsBenefit;
        if (TestUtil.findAll(em, CompensationBenefits.class).isEmpty()) {
            benefitsRepository.saveAndFlush(benefits);
            compensationbenefitsBenefit = CompensationBenefitsResourceIT.createEntity(em);
        } else {
            compensationbenefitsBenefit = TestUtil.findAll(em, CompensationBenefits.class).get(0);
        }
        em.persist(compensationbenefitsBenefit);
        em.flush();
        benefits.addCompensationbenefitsBenefit(compensationbenefitsBenefit);
        benefitsRepository.saveAndFlush(benefits);
        Long compensationbenefitsBenefitId = compensationbenefitsBenefit.getId();

        // Get all the benefitsList where compensationbenefitsBenefit equals to compensationbenefitsBenefitId
        defaultBenefitsShouldBeFound("compensationbenefitsBenefitId.equals=" + compensationbenefitsBenefitId);

        // Get all the benefitsList where compensationbenefitsBenefit equals to (compensationbenefitsBenefitId + 1)
        defaultBenefitsShouldNotBeFound("compensationbenefitsBenefitId.equals=" + (compensationbenefitsBenefitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBenefitsShouldBeFound(String filter) throws Exception {
        restBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefits.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBenefitsShouldNotBeFound(String filter) throws Exception {
        restBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBenefitsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBenefits() throws Exception {
        // Get the benefits
        restBenefitsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();

        // Update the benefits
        Benefits updatedBenefits = benefitsRepository.findById(benefits.getId()).get();
        // Disconnect from session so that the updates on updatedBenefits are not directly saved in db
        em.detach(updatedBenefits);
        updatedBenefits.name(UPDATED_NAME).isdeleted(UPDATED_ISDELETED).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restBenefitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBenefits.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBenefits))
            )
            .andExpect(status().isOk());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
        Benefits testBenefits = benefitsList.get(benefitsList.size() - 1);
        assertThat(testBenefits.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefits.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testBenefits.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testBenefits.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingBenefits() throws Exception {
        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();
        benefits.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benefits.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenefits() throws Exception {
        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();
        benefits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenefits() throws Exception {
        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();
        benefits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenefitsWithPatch() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();

        // Update the benefits using partial update
        Benefits partialUpdatedBenefits = new Benefits();
        partialUpdatedBenefits.setId(benefits.getId());

        partialUpdatedBenefits.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefits))
            )
            .andExpect(status().isOk());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
        Benefits testBenefits = benefitsList.get(benefitsList.size() - 1);
        assertThat(testBenefits.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefits.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testBenefits.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testBenefits.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateBenefitsWithPatch() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();

        // Update the benefits using partial update
        Benefits partialUpdatedBenefits = new Benefits();
        partialUpdatedBenefits.setId(benefits.getId());

        partialUpdatedBenefits.name(UPDATED_NAME).isdeleted(UPDATED_ISDELETED).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT);

        restBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefits))
            )
            .andExpect(status().isOk());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
        Benefits testBenefits = benefitsList.get(benefitsList.size() - 1);
        assertThat(testBenefits.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefits.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testBenefits.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testBenefits.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingBenefits() throws Exception {
        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();
        benefits.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benefits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenefits() throws Exception {
        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();
        benefits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenefits() throws Exception {
        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();
        benefits.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefits))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        int databaseSizeBeforeDelete = benefitsRepository.findAll().size();

        // Delete the benefits
        restBenefitsMockMvc
            .perform(delete(ENTITY_API_URL_ID, benefits.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
