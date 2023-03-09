package com.venturedive.vendian_mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.venturedive.vendian_mono.IntegrationTest;
import com.venturedive.vendian_mono.domain.Addresses;
import com.venturedive.vendian_mono.domain.EmployeeAddresses;
import com.venturedive.vendian_mono.repository.AddressesRepository;
import com.venturedive.vendian_mono.service.criteria.AddressesCriteria;
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
 * Integration tests for the {@link AddressesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressesResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTALCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTALCODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISDELETED = false;
    private static final Boolean UPDATED_ISDELETED = true;

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressesMockMvc;

    private Addresses addresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createEntity(EntityManager em) {
        Addresses addresses = new Addresses()
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .province(DEFAULT_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .postalcode(DEFAULT_POSTALCODE)
            .isdeleted(DEFAULT_ISDELETED)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return addresses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createUpdatedEntity(EntityManager em) {
        Addresses addresses = new Addresses()
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postalcode(UPDATED_POSTALCODE)
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        return addresses;
    }

    @BeforeEach
    public void initTest() {
        addresses = createEntity(em);
    }

    @Test
    @Transactional
    void createAddresses() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();
        // Create the Addresses
        restAddressesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isCreated());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate + 1);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAddresses.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddresses.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testAddresses.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddresses.getPostalcode()).isEqualTo(DEFAULT_POSTALCODE);
        assertThat(testAddresses.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testAddresses.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testAddresses.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void createAddressesWithExistingId() throws Exception {
        // Create the Addresses with an existing ID
        addresses.setId(1L);

        int databaseSizeBeforeCreate = addressesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressesRepository.findAll().size();
        // set the field null
        addresses.setAddress(null);

        // Create the Addresses, which fails.

        restAddressesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].postalcode").value(hasItem(DEFAULT_POSTALCODE)))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));
    }

    @Test
    @Transactional
    void getAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get the addresses
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL_ID, addresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addresses.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.postalcode").value(DEFAULT_POSTALCODE))
            .andExpect(jsonPath("$.isdeleted").value(DEFAULT_ISDELETED.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()));
    }

    @Test
    @Transactional
    void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        Long id = addresses.getId();

        defaultAddressesShouldBeFound("id.equals=" + id);
        defaultAddressesShouldNotBeFound("id.notEquals=" + id);

        defaultAddressesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address equals to DEFAULT_ADDRESS
        defaultAddressesShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the addressesList where address equals to UPDATED_ADDRESS
        defaultAddressesShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultAddressesShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the addressesList where address equals to UPDATED_ADDRESS
        defaultAddressesShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address is not null
        defaultAddressesShouldBeFound("address.specified=true");

        // Get all the addressesList where address is null
        defaultAddressesShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddressContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address contains DEFAULT_ADDRESS
        defaultAddressesShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the addressesList where address contains UPDATED_ADDRESS
        defaultAddressesShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where address does not contain DEFAULT_ADDRESS
        defaultAddressesShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the addressesList where address does not contain UPDATED_ADDRESS
        defaultAddressesShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city equals to DEFAULT_CITY
        defaultAddressesShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the addressesList where city equals to UPDATED_CITY
        defaultAddressesShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllAddressesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city in DEFAULT_CITY or UPDATED_CITY
        defaultAddressesShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the addressesList where city equals to UPDATED_CITY
        defaultAddressesShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllAddressesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city is not null
        defaultAddressesShouldBeFound("city.specified=true");

        // Get all the addressesList where city is null
        defaultAddressesShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByCityContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city contains DEFAULT_CITY
        defaultAddressesShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the addressesList where city contains UPDATED_CITY
        defaultAddressesShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllAddressesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city does not contain DEFAULT_CITY
        defaultAddressesShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the addressesList where city does not contain UPDATED_CITY
        defaultAddressesShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllAddressesByProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where province equals to DEFAULT_PROVINCE
        defaultAddressesShouldBeFound("province.equals=" + DEFAULT_PROVINCE);

        // Get all the addressesList where province equals to UPDATED_PROVINCE
        defaultAddressesShouldNotBeFound("province.equals=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllAddressesByProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where province in DEFAULT_PROVINCE or UPDATED_PROVINCE
        defaultAddressesShouldBeFound("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE);

        // Get all the addressesList where province equals to UPDATED_PROVINCE
        defaultAddressesShouldNotBeFound("province.in=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllAddressesByProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where province is not null
        defaultAddressesShouldBeFound("province.specified=true");

        // Get all the addressesList where province is null
        defaultAddressesShouldNotBeFound("province.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByProvinceContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where province contains DEFAULT_PROVINCE
        defaultAddressesShouldBeFound("province.contains=" + DEFAULT_PROVINCE);

        // Get all the addressesList where province contains UPDATED_PROVINCE
        defaultAddressesShouldNotBeFound("province.contains=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllAddressesByProvinceNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where province does not contain DEFAULT_PROVINCE
        defaultAddressesShouldNotBeFound("province.doesNotContain=" + DEFAULT_PROVINCE);

        // Get all the addressesList where province does not contain UPDATED_PROVINCE
        defaultAddressesShouldBeFound("province.doesNotContain=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllAddressesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where country equals to DEFAULT_COUNTRY
        defaultAddressesShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the addressesList where country equals to UPDATED_COUNTRY
        defaultAddressesShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllAddressesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultAddressesShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the addressesList where country equals to UPDATED_COUNTRY
        defaultAddressesShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllAddressesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where country is not null
        defaultAddressesShouldBeFound("country.specified=true");

        // Get all the addressesList where country is null
        defaultAddressesShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByCountryContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where country contains DEFAULT_COUNTRY
        defaultAddressesShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the addressesList where country contains UPDATED_COUNTRY
        defaultAddressesShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllAddressesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where country does not contain DEFAULT_COUNTRY
        defaultAddressesShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the addressesList where country does not contain UPDATED_COUNTRY
        defaultAddressesShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllAddressesByPostalcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalcode equals to DEFAULT_POSTALCODE
        defaultAddressesShouldBeFound("postalcode.equals=" + DEFAULT_POSTALCODE);

        // Get all the addressesList where postalcode equals to UPDATED_POSTALCODE
        defaultAddressesShouldNotBeFound("postalcode.equals=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPostalcodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalcode in DEFAULT_POSTALCODE or UPDATED_POSTALCODE
        defaultAddressesShouldBeFound("postalcode.in=" + DEFAULT_POSTALCODE + "," + UPDATED_POSTALCODE);

        // Get all the addressesList where postalcode equals to UPDATED_POSTALCODE
        defaultAddressesShouldNotBeFound("postalcode.in=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPostalcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalcode is not null
        defaultAddressesShouldBeFound("postalcode.specified=true");

        // Get all the addressesList where postalcode is null
        defaultAddressesShouldNotBeFound("postalcode.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByPostalcodeContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalcode contains DEFAULT_POSTALCODE
        defaultAddressesShouldBeFound("postalcode.contains=" + DEFAULT_POSTALCODE);

        // Get all the addressesList where postalcode contains UPDATED_POSTALCODE
        defaultAddressesShouldNotBeFound("postalcode.contains=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPostalcodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalcode does not contain DEFAULT_POSTALCODE
        defaultAddressesShouldNotBeFound("postalcode.doesNotContain=" + DEFAULT_POSTALCODE);

        // Get all the addressesList where postalcode does not contain UPDATED_POSTALCODE
        defaultAddressesShouldBeFound("postalcode.doesNotContain=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByIsdeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where isdeleted equals to DEFAULT_ISDELETED
        defaultAddressesShouldBeFound("isdeleted.equals=" + DEFAULT_ISDELETED);

        // Get all the addressesList where isdeleted equals to UPDATED_ISDELETED
        defaultAddressesShouldNotBeFound("isdeleted.equals=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllAddressesByIsdeletedIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where isdeleted in DEFAULT_ISDELETED or UPDATED_ISDELETED
        defaultAddressesShouldBeFound("isdeleted.in=" + DEFAULT_ISDELETED + "," + UPDATED_ISDELETED);

        // Get all the addressesList where isdeleted equals to UPDATED_ISDELETED
        defaultAddressesShouldNotBeFound("isdeleted.in=" + UPDATED_ISDELETED);
    }

    @Test
    @Transactional
    void getAllAddressesByIsdeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where isdeleted is not null
        defaultAddressesShouldBeFound("isdeleted.specified=true");

        // Get all the addressesList where isdeleted is null
        defaultAddressesShouldNotBeFound("isdeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where createdat equals to DEFAULT_CREATEDAT
        defaultAddressesShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the addressesList where createdat equals to UPDATED_CREATEDAT
        defaultAddressesShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultAddressesShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the addressesList where createdat equals to UPDATED_CREATEDAT
        defaultAddressesShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where createdat is not null
        defaultAddressesShouldBeFound("createdat.specified=true");

        // Get all the addressesList where createdat is null
        defaultAddressesShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where updatedat equals to DEFAULT_UPDATEDAT
        defaultAddressesShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the addressesList where updatedat equals to UPDATED_UPDATEDAT
        defaultAddressesShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllAddressesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultAddressesShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the addressesList where updatedat equals to UPDATED_UPDATEDAT
        defaultAddressesShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllAddressesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where updatedat is not null
        defaultAddressesShouldBeFound("updatedat.specified=true");

        // Get all the addressesList where updatedat is null
        defaultAddressesShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByEmployeeaddressesAddressIsEqualToSomething() throws Exception {
        EmployeeAddresses employeeaddressesAddress;
        if (TestUtil.findAll(em, EmployeeAddresses.class).isEmpty()) {
            addressesRepository.saveAndFlush(addresses);
            employeeaddressesAddress = EmployeeAddressesResourceIT.createEntity(em);
        } else {
            employeeaddressesAddress = TestUtil.findAll(em, EmployeeAddresses.class).get(0);
        }
        em.persist(employeeaddressesAddress);
        em.flush();
        addresses.addEmployeeaddressesAddress(employeeaddressesAddress);
        addressesRepository.saveAndFlush(addresses);
        Long employeeaddressesAddressId = employeeaddressesAddress.getId();

        // Get all the addressesList where employeeaddressesAddress equals to employeeaddressesAddressId
        defaultAddressesShouldBeFound("employeeaddressesAddressId.equals=" + employeeaddressesAddressId);

        // Get all the addressesList where employeeaddressesAddress equals to (employeeaddressesAddressId + 1)
        defaultAddressesShouldNotBeFound("employeeaddressesAddressId.equals=" + (employeeaddressesAddressId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressesShouldBeFound(String filter) throws Exception {
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].postalcode").value(hasItem(DEFAULT_POSTALCODE)))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())));

        // Check, that the count call also returns 1
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressesShouldNotBeFound(String filter) throws Exception {
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAddresses() throws Exception {
        // Get the addresses
        restAddressesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses
        Addresses updatedAddresses = addressesRepository.findById(addresses.getId()).get();
        // Disconnect from session so that the updates on updatedAddresses are not directly saved in db
        em.detach(updatedAddresses);
        updatedAddresses
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postalcode(UPDATED_POSTALCODE)
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAddresses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAddresses))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAddresses.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddresses.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testAddresses.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddresses.getPostalcode()).isEqualTo(UPDATED_POSTALCODE);
        assertThat(testAddresses.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testAddresses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testAddresses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void putNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addresses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressesWithPatch() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses using partial update
        Addresses partialUpdatedAddresses = new Addresses();
        partialUpdatedAddresses.setId(addresses.getId());

        partialUpdatedAddresses
            .address(UPDATED_ADDRESS)
            .province(UPDATED_PROVINCE)
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT);

        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddresses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddresses))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAddresses.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddresses.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testAddresses.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddresses.getPostalcode()).isEqualTo(DEFAULT_POSTALCODE);
        assertThat(testAddresses.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testAddresses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testAddresses.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
    }

    @Test
    @Transactional
    void fullUpdateAddressesWithPatch() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses using partial update
        Addresses partialUpdatedAddresses = new Addresses();
        partialUpdatedAddresses.setId(addresses.getId());

        partialUpdatedAddresses
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postalcode(UPDATED_POSTALCODE)
            .isdeleted(UPDATED_ISDELETED)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);

        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddresses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddresses))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAddresses.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddresses.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testAddresses.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddresses.getPostalcode()).isEqualTo(UPDATED_POSTALCODE);
        assertThat(testAddresses.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testAddresses.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testAddresses.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void patchNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addresses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addresses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeDelete = addressesRepository.findAll().size();

        // Delete the addresses
        restAddressesMockMvc
            .perform(delete(ENTITY_API_URL_ID, addresses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
