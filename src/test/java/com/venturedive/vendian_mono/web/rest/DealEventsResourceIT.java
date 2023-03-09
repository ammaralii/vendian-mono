package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.DealEvents;
import com.venturedive.vendian_mono.repository.DealEventsRepository;
import com.venturedive.vendian_mono.service.criteria.DealEventsCriteria;
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
 * Integration tests for the {@link DealEventsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealEventsResourceIT {

    private static final String DEFAULT_EVENTTYPE = "AAAAAAA";
    private static final String UPDATED_EVENTTYPE = "BBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/deal-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealEventsRepository dealEventsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealEventsMockMvc;

    private DealEvents dealEvents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealEvents createEntity(EntityManager em) {
        DealEvents dealEvents = new DealEvents().eventtype(DEFAULT_EVENTTYPE).createdat(DEFAULT_CREATEDAT);
        return dealEvents;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealEvents createUpdatedEntity(EntityManager em) {
        DealEvents dealEvents = new DealEvents().eventtype(UPDATED_EVENTTYPE).createdat(UPDATED_CREATEDAT);
        return dealEvents;
    }

    @BeforeEach
    public void initTest() {
        dealEvents = createEntity(em);
    }

    @Test
    @Transactional
    void createDealEvents() throws Exception {
        int databaseSizeBeforeCreate = dealEventsRepository.findAll().size();
        // Create the DealEvents
        restDealEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isCreated());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeCreate + 1);
        DealEvents testDealEvents = dealEventsList.get(dealEventsList.size() - 1);
        assertThat(testDealEvents.getEventtype()).isEqualTo(DEFAULT_EVENTTYPE);
        assertThat(testDealEvents.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
    }

    @Test
    @Transactional
    void createDealEventsWithExistingId() throws Exception {
        // Create the DealEvents with an existing ID
        dealEvents.setId(1L);

        int databaseSizeBeforeCreate = dealEventsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEventtypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealEventsRepository.findAll().size();
        // set the field null
        dealEvents.setEventtype(null);

        // Create the DealEvents, which fails.

        restDealEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isBadRequest());

        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealEventsRepository.findAll().size();
        // set the field null
        dealEvents.setCreatedat(null);

        // Create the DealEvents, which fails.

        restDealEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isBadRequest());

        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDealEvents() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList
        restDealEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealEvents.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventtype").value(hasItem(DEFAULT_EVENTTYPE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())));
    }

    @Test
    @Transactional
    void getDealEvents() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get the dealEvents
        restDealEventsMockMvc
            .perform(get(ENTITY_API_URL_ID, dealEvents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealEvents.getId().intValue()))
            .andExpect(jsonPath("$.eventtype").value(DEFAULT_EVENTTYPE))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()));
    }

    @Test
    @Transactional
    void getDealEventsByIdFiltering() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        Long id = dealEvents.getId();

        defaultDealEventsShouldBeFound("id.equals=" + id);
        defaultDealEventsShouldNotBeFound("id.notEquals=" + id);

        defaultDealEventsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealEventsShouldNotBeFound("id.greaterThan=" + id);

        defaultDealEventsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealEventsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealEventsByEventtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList where eventtype equals to DEFAULT_EVENTTYPE
        defaultDealEventsShouldBeFound("eventtype.equals=" + DEFAULT_EVENTTYPE);

        // Get all the dealEventsList where eventtype equals to UPDATED_EVENTTYPE
        defaultDealEventsShouldNotBeFound("eventtype.equals=" + UPDATED_EVENTTYPE);
    }

    @Test
    @Transactional
    void getAllDealEventsByEventtypeIsInShouldWork() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList where eventtype in DEFAULT_EVENTTYPE or UPDATED_EVENTTYPE
        defaultDealEventsShouldBeFound("eventtype.in=" + DEFAULT_EVENTTYPE + "," + UPDATED_EVENTTYPE);

        // Get all the dealEventsList where eventtype equals to UPDATED_EVENTTYPE
        defaultDealEventsShouldNotBeFound("eventtype.in=" + UPDATED_EVENTTYPE);
    }

    @Test
    @Transactional
    void getAllDealEventsByEventtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList where eventtype is not null
        defaultDealEventsShouldBeFound("eventtype.specified=true");

        // Get all the dealEventsList where eventtype is null
        defaultDealEventsShouldNotBeFound("eventtype.specified=false");
    }

    @Test
    @Transactional
    void getAllDealEventsByEventtypeContainsSomething() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList where eventtype contains DEFAULT_EVENTTYPE
        defaultDealEventsShouldBeFound("eventtype.contains=" + DEFAULT_EVENTTYPE);

        // Get all the dealEventsList where eventtype contains UPDATED_EVENTTYPE
        defaultDealEventsShouldNotBeFound("eventtype.contains=" + UPDATED_EVENTTYPE);
    }

    @Test
    @Transactional
    void getAllDealEventsByEventtypeNotContainsSomething() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList where eventtype does not contain DEFAULT_EVENTTYPE
        defaultDealEventsShouldNotBeFound("eventtype.doesNotContain=" + DEFAULT_EVENTTYPE);

        // Get all the dealEventsList where eventtype does not contain UPDATED_EVENTTYPE
        defaultDealEventsShouldBeFound("eventtype.doesNotContain=" + UPDATED_EVENTTYPE);
    }

    @Test
    @Transactional
    void getAllDealEventsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList where createdat equals to DEFAULT_CREATEDAT
        defaultDealEventsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the dealEventsList where createdat equals to UPDATED_CREATEDAT
        defaultDealEventsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealEventsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultDealEventsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the dealEventsList where createdat equals to UPDATED_CREATEDAT
        defaultDealEventsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllDealEventsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        // Get all the dealEventsList where createdat is not null
        defaultDealEventsShouldBeFound("createdat.specified=true");

        // Get all the dealEventsList where createdat is null
        defaultDealEventsShouldNotBeFound("createdat.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealEventsShouldBeFound(String filter) throws Exception {
        restDealEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealEvents.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventtype").value(hasItem(DEFAULT_EVENTTYPE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())));

        // Check, that the count call also returns 1
        restDealEventsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealEventsShouldNotBeFound(String filter) throws Exception {
        restDealEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealEventsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDealEvents() throws Exception {
        // Get the dealEvents
        restDealEventsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDealEvents() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();

        // Update the dealEvents
        DealEvents updatedDealEvents = dealEventsRepository.findById(dealEvents.getId()).get();
        // Disconnect from session so that the updates on updatedDealEvents are not directly saved in db
        em.detach(updatedDealEvents);
        updatedDealEvents.eventtype(UPDATED_EVENTTYPE).createdat(UPDATED_CREATEDAT);

        restDealEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealEvents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealEvents))
            )
            .andExpect(status().isOk());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
        DealEvents testDealEvents = dealEventsList.get(dealEventsList.size() - 1);
        assertThat(testDealEvents.getEventtype()).isEqualTo(UPDATED_EVENTTYPE);
        assertThat(testDealEvents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingDealEvents() throws Exception {
        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();
        dealEvents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealEvents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealEvents() throws Exception {
        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();
        dealEvents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealEvents() throws Exception {
        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();
        dealEvents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealEventsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealEventsWithPatch() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();

        // Update the dealEvents using partial update
        DealEvents partialUpdatedDealEvents = new DealEvents();
        partialUpdatedDealEvents.setId(dealEvents.getId());

        partialUpdatedDealEvents.eventtype(UPDATED_EVENTTYPE).createdat(UPDATED_CREATEDAT);

        restDealEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealEvents))
            )
            .andExpect(status().isOk());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
        DealEvents testDealEvents = dealEventsList.get(dealEventsList.size() - 1);
        assertThat(testDealEvents.getEventtype()).isEqualTo(UPDATED_EVENTTYPE);
        assertThat(testDealEvents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateDealEventsWithPatch() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();

        // Update the dealEvents using partial update
        DealEvents partialUpdatedDealEvents = new DealEvents();
        partialUpdatedDealEvents.setId(dealEvents.getId());

        partialUpdatedDealEvents.eventtype(UPDATED_EVENTTYPE).createdat(UPDATED_CREATEDAT);

        restDealEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealEvents))
            )
            .andExpect(status().isOk());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
        DealEvents testDealEvents = dealEventsList.get(dealEventsList.size() - 1);
        assertThat(testDealEvents.getEventtype()).isEqualTo(UPDATED_EVENTTYPE);
        assertThat(testDealEvents.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingDealEvents() throws Exception {
        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();
        dealEvents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealEvents() throws Exception {
        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();
        dealEvents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealEvents() throws Exception {
        int databaseSizeBeforeUpdate = dealEventsRepository.findAll().size();
        dealEvents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealEventsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealEvents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealEvents in the database
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealEvents() throws Exception {
        // Initialize the database
        dealEventsRepository.saveAndFlush(dealEvents);

        int databaseSizeBeforeDelete = dealEventsRepository.findAll().size();

        // Delete the dealEvents
        restDealEventsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealEvents.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealEvents> dealEventsList = dealEventsRepository.findAll();
        assertThat(dealEventsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
