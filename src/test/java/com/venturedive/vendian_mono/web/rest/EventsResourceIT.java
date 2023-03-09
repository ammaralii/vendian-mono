package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Events;
import com.venturedive.vendian_mono.domain.ZEmployeeProjects;
import com.venturedive.vendian_mono.repository.EventsRepository;
import com.venturedive.vendian_mono.service.criteria.EventsCriteria;
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
 * Integration tests for the {@link EventsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventsResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventsMockMvc;

    private Events events;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Events createEntity(EntityManager em) {
        Events events = new Events().type(DEFAULT_TYPE);
        return events;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Events createUpdatedEntity(EntityManager em) {
        Events events = new Events().type(UPDATED_TYPE);
        return events;
    }

    @BeforeEach
    public void initTest() {
        events = createEntity(em);
    }

    @Test
    @Transactional
    void createEvents() throws Exception {
        int databaseSizeBeforeCreate = eventsRepository.findAll().size();
        // Create the Events
        restEventsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(events))
            )
            .andExpect(status().isCreated());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeCreate + 1);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createEventsWithExistingId() throws Exception {
        // Create the Events with an existing ID
        events.setId(1L);

        int databaseSizeBeforeCreate = eventsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventsMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(events))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get all the eventsList
        restEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(events.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get the events
        restEventsMockMvc
            .perform(get(ENTITY_API_URL_ID, events.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(events.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getEventsByIdFiltering() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        Long id = events.getId();

        defaultEventsShouldBeFound("id.equals=" + id);
        defaultEventsShouldNotBeFound("id.notEquals=" + id);

        defaultEventsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventsShouldNotBeFound("id.greaterThan=" + id);

        defaultEventsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get all the eventsList where type equals to DEFAULT_TYPE
        defaultEventsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the eventsList where type equals to UPDATED_TYPE
        defaultEventsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get all the eventsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEventsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the eventsList where type equals to UPDATED_TYPE
        defaultEventsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get all the eventsList where type is not null
        defaultEventsShouldBeFound("type.specified=true");

        // Get all the eventsList where type is null
        defaultEventsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByTypeContainsSomething() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get all the eventsList where type contains DEFAULT_TYPE
        defaultEventsShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the eventsList where type contains UPDATED_TYPE
        defaultEventsShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get all the eventsList where type does not contain DEFAULT_TYPE
        defaultEventsShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the eventsList where type does not contain UPDATED_TYPE
        defaultEventsShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByZemployeeprojectsEventIsEqualToSomething() throws Exception {
        ZEmployeeProjects zemployeeprojectsEvent;
        if (TestUtil.findAll(em, ZEmployeeProjects.class).isEmpty()) {
            eventsRepository.saveAndFlush(events);
            zemployeeprojectsEvent = ZEmployeeProjectsResourceIT.createEntity(em);
        } else {
            zemployeeprojectsEvent = TestUtil.findAll(em, ZEmployeeProjects.class).get(0);
        }
        em.persist(zemployeeprojectsEvent);
        em.flush();
        events.addZemployeeprojectsEvent(zemployeeprojectsEvent);
        eventsRepository.saveAndFlush(events);
        Long zemployeeprojectsEventId = zemployeeprojectsEvent.getId();

        // Get all the eventsList where zemployeeprojectsEvent equals to zemployeeprojectsEventId
        defaultEventsShouldBeFound("zemployeeprojectsEventId.equals=" + zemployeeprojectsEventId);

        // Get all the eventsList where zemployeeprojectsEvent equals to (zemployeeprojectsEventId + 1)
        defaultEventsShouldNotBeFound("zemployeeprojectsEventId.equals=" + (zemployeeprojectsEventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventsShouldBeFound(String filter) throws Exception {
        restEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(events.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restEventsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventsShouldNotBeFound(String filter) throws Exception {
        restEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEvents() throws Exception {
        // Get the events
        restEventsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();

        // Update the events
        Events updatedEvents = eventsRepository.findById(events.getId()).get();
        // Disconnect from session so that the updates on updatedEvents are not directly saved in db
        em.detach(updatedEvents);
        updatedEvents.type(UPDATED_TYPE);

        restEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEvents.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEvents))
            )
            .andExpect(status().isOk());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        events.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, events.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(events))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        events.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(events))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        events.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(events))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventsWithPatch() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();

        // Update the events using partial update
        Events partialUpdatedEvents = new Events();
        partialUpdatedEvents.setId(events.getId());

        partialUpdatedEvents.type(UPDATED_TYPE);

        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvents))
            )
            .andExpect(status().isOk());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEventsWithPatch() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();

        // Update the events using partial update
        Events partialUpdatedEvents = new Events();
        partialUpdatedEvents.setId(events.getId());

        partialUpdatedEvents.type(UPDATED_TYPE);

        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvents))
            )
            .andExpect(status().isOk());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        events.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, events.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(events))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        events.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(events))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        events.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(events))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        int databaseSizeBeforeDelete = eventsRepository.findAll().size();

        // Delete the events
        restEventsMockMvc
            .perform(delete(ENTITY_API_URL_ID, events.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
