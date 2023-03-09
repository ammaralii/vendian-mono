package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Employees;
import com.venturedive.vendian_mono.domain.Locations;
import com.venturedive.vendian_mono.repository.LocationsRepository;
import com.venturedive.vendian_mono.service.criteria.LocationsCriteria;
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
 * Integration tests for the {@link LocationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocationsRepository locationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationsMockMvc;

    private Locations locations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locations createEntity(EntityManager em) {
        Locations locations = new Locations()
            .name(DEFAULT_NAME)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return locations;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locations createUpdatedEntity(EntityManager em) {
        Locations locations = new Locations()
            .name(UPDATED_NAME)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return locations;
    }

    @BeforeEach
    public void initTest() {
        locations = createEntity(em);
    }

    @Test
    @Transactional
    void createLocations() throws Exception {
        int databaseSizeBeforeCreate = locationsRepository.findAll().size();
        // Create the Locations
        restLocationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isCreated());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeCreate + 1);
        Locations testLocations = locationsList.get(locationsList.size() - 1);
        assertThat(testLocations.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLocations.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testLocations.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testLocations.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    @Transactional
    void createLocationsWithExistingId() throws Exception {
        // Create the Locations with an existing ID
        locations.setId(1L);

        int databaseSizeBeforeCreate = locationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationsRepository.findAll().size();
        // set the field null
        locations.setName(null);

        // Create the Locations, which fails.

        restLocationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isBadRequest());

        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationsRepository.findAll().size();
        // set the field null
        locations.setCreatedat(null);

        // Create the Locations, which fails.

        restLocationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isBadRequest());

        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationsRepository.findAll().size();
        // set the field null
        locations.setUpdatedat(null);

        // Create the Locations, which fails.

        restLocationsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isBadRequest());

        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList
        restLocationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    @Transactional
    void getLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get the locations
        restLocationsMockMvc
            .perform(get(ENTITY_API_URL_ID, locations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locations.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    @Transactional
    void getLocationsByIdFiltering() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        Long id = locations.getId();

        defaultLocationsShouldBeFound("id.equals=" + id);
        defaultLocationsShouldNotBeFound("id.notEquals=" + id);

        defaultLocationsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLocationsShouldNotBeFound("id.greaterThan=" + id);

        defaultLocationsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLocationsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLocationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where name equals to DEFAULT_NAME
        defaultLocationsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the locationsList where name equals to UPDATED_NAME
        defaultLocationsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLocationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLocationsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the locationsList where name equals to UPDATED_NAME
        defaultLocationsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLocationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where name is not null
        defaultLocationsShouldBeFound("name.specified=true");

        // Get all the locationsList where name is null
        defaultLocationsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByNameContainsSomething() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where name contains DEFAULT_NAME
        defaultLocationsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the locationsList where name contains UPDATED_NAME
        defaultLocationsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLocationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where name does not contain DEFAULT_NAME
        defaultLocationsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the locationsList where name does not contain UPDATED_NAME
        defaultLocationsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where createdat equals to DEFAULT_CREATEDAT
        defaultLocationsShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the locationsList where createdat equals to UPDATED_CREATEDAT
        defaultLocationsShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultLocationsShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the locationsList where createdat equals to UPDATED_CREATEDAT
        defaultLocationsShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where createdat is not null
        defaultLocationsShouldBeFound("createdat.specified=true");

        // Get all the locationsList where createdat is null
        defaultLocationsShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where updatedat equals to DEFAULT_UPDATEDAT
        defaultLocationsShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the locationsList where updatedat equals to UPDATED_UPDATEDAT
        defaultLocationsShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLocationsByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultLocationsShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the locationsList where updatedat equals to UPDATED_UPDATEDAT
        defaultLocationsShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllLocationsByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where updatedat is not null
        defaultLocationsShouldBeFound("updatedat.specified=true");

        // Get all the locationsList where updatedat is null
        defaultLocationsShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByDeletedatIsEqualToSomething() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where deletedat equals to DEFAULT_DELETEDAT
        defaultLocationsShouldBeFound("deletedat.equals=" + DEFAULT_DELETEDAT);

        // Get all the locationsList where deletedat equals to UPDATED_DELETEDAT
        defaultLocationsShouldNotBeFound("deletedat.equals=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllLocationsByDeletedatIsInShouldWork() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where deletedat in DEFAULT_DELETEDAT or UPDATED_DELETEDAT
        defaultLocationsShouldBeFound("deletedat.in=" + DEFAULT_DELETEDAT + "," + UPDATED_DELETEDAT);

        // Get all the locationsList where deletedat equals to UPDATED_DELETEDAT
        defaultLocationsShouldNotBeFound("deletedat.in=" + UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void getAllLocationsByDeletedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList where deletedat is not null
        defaultLocationsShouldBeFound("deletedat.specified=true");

        // Get all the locationsList where deletedat is null
        defaultLocationsShouldNotBeFound("deletedat.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByEmployeesLocationIsEqualToSomething() throws Exception {
        Employees employeesLocation;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            locationsRepository.saveAndFlush(locations);
            employeesLocation = EmployeesResourceIT.createEntity(em);
        } else {
            employeesLocation = TestUtil.findAll(em, Employees.class).get(0);
        }
        em.persist(employeesLocation);
        em.flush();
        locations.addEmployeesLocation(employeesLocation);
        locationsRepository.saveAndFlush(locations);
        Long employeesLocationId = employeesLocation.getId();

        // Get all the locationsList where employeesLocation equals to employeesLocationId
        defaultLocationsShouldBeFound("employeesLocationId.equals=" + employeesLocationId);

        // Get all the locationsList where employeesLocation equals to (employeesLocationId + 1)
        defaultLocationsShouldNotBeFound("employeesLocationId.equals=" + (employeesLocationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationsShouldBeFound(String filter) throws Exception {
        restLocationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));

        // Check, that the count call also returns 1
        restLocationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationsShouldNotBeFound(String filter) throws Exception {
        restLocationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLocations() throws Exception {
        // Get the locations
        restLocationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();

        // Update the locations
        Locations updatedLocations = locationsRepository.findById(locations.getId()).get();
        // Disconnect from session so that the updates on updatedLocations are not directly saved in db
        em.detach(updatedLocations);
        updatedLocations.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).deletedat(UPDATED_DELETEDAT);

        restLocationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocations))
            )
            .andExpect(status().isOk());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
        Locations testLocations = locationsList.get(locationsList.size() - 1);
        assertThat(testLocations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocations.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLocations.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testLocations.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void putNonExistingLocations() throws Exception {
        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();
        locations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locations.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocations() throws Exception {
        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();
        locations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocations() throws Exception {
        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();
        locations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationsWithPatch() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();

        // Update the locations using partial update
        Locations partialUpdatedLocations = new Locations();
        partialUpdatedLocations.setId(locations.getId());

        partialUpdatedLocations.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).deletedat(UPDATED_DELETEDAT);

        restLocationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocations))
            )
            .andExpect(status().isOk());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
        Locations testLocations = locationsList.get(locationsList.size() - 1);
        assertThat(testLocations.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLocations.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLocations.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testLocations.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void fullUpdateLocationsWithPatch() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();

        // Update the locations using partial update
        Locations partialUpdatedLocations = new Locations();
        partialUpdatedLocations.setId(locations.getId());

        partialUpdatedLocations.name(UPDATED_NAME).createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).deletedat(UPDATED_DELETEDAT);

        restLocationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocations))
            )
            .andExpect(status().isOk());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
        Locations testLocations = locationsList.get(locationsList.size() - 1);
        assertThat(testLocations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocations.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testLocations.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testLocations.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingLocations() throws Exception {
        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();
        locations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locations.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocations() throws Exception {
        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();
        locations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocations() throws Exception {
        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();
        locations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        int databaseSizeBeforeDelete = locationsRepository.findAll().size();

        // Delete the locations
        restLocationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, locations.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
