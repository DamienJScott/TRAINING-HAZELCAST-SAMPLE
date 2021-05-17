package com.hc.generated.web.rest;

import com.hc.generated.HcSampleApp;
import com.hc.generated.domain.Station;
import com.hc.generated.domain.Powerbank;
import com.hc.generated.repository.StationRepository;
import com.hc.generated.service.StationService;
import com.hc.generated.service.dto.StationDTO;
import com.hc.generated.service.mapper.StationMapper;
import com.hc.generated.service.dto.StationCriteria;
import com.hc.generated.service.StationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StationResource} REST controller.
 */
@SpringBootTest(classes = HcSampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_POSTION = "AAAAAAAAAA";
    private static final String UPDATED_POSTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private StationService stationService;

    @Autowired
    private StationQueryService stationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStationMockMvc;

    private Station station;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createEntity(EntityManager em) {
        Station station = new Station()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .postion(DEFAULT_POSTION)
            .enabled(DEFAULT_ENABLED);
        return station;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createUpdatedEntity(EntityManager em) {
        Station station = new Station()
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .postion(UPDATED_POSTION)
            .enabled(UPDATED_ENABLED);
        return station;
    }

    @BeforeEach
    public void initTest() {
        station = createEntity(em);
    }

    @Test
    @Transactional
    public void createStation() throws Exception {
        int databaseSizeBeforeCreate = stationRepository.findAll().size();
        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);
        restStationMockMvc.perform(post("/api/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isCreated());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate + 1);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStation.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testStation.getPostion()).isEqualTo(DEFAULT_POSTION);
        assertThat(testStation.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createStationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stationRepository.findAll().size();

        // Create the Station with an existing ID
        station.setId(1L);
        StationDTO stationDTO = stationMapper.toDto(station);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStationMockMvc.perform(post("/api/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stationRepository.findAll().size();
        // set the field null
        station.setName(null);

        // Create the Station, which fails.
        StationDTO stationDTO = stationMapper.toDto(station);


        restStationMockMvc.perform(post("/api/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isBadRequest());

        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostionIsRequired() throws Exception {
        int databaseSizeBeforeTest = stationRepository.findAll().size();
        // set the field null
        station.setPostion(null);

        // Create the Station, which fails.
        StationDTO stationDTO = stationMapper.toDto(station);


        restStationMockMvc.perform(post("/api/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isBadRequest());

        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = stationRepository.findAll().size();
        // set the field null
        station.setEnabled(null);

        // Create the Station, which fails.
        StationDTO stationDTO = stationMapper.toDto(station);


        restStationMockMvc.perform(post("/api/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isBadRequest());

        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStations() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList
        restStationMockMvc.perform(get("/api/stations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(station.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].postion").value(hasItem(DEFAULT_POSTION)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get the station
        restStationMockMvc.perform(get("/api/stations/{id}", station.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(station.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.postion").value(DEFAULT_POSTION))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }


    @Test
    @Transactional
    public void getStationsByIdFiltering() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        Long id = station.getId();

        defaultStationShouldBeFound("id.equals=" + id);
        defaultStationShouldNotBeFound("id.notEquals=" + id);

        defaultStationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStationShouldNotBeFound("id.greaterThan=" + id);

        defaultStationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where name equals to DEFAULT_NAME
        defaultStationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stationList where name equals to UPDATED_NAME
        defaultStationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where name not equals to DEFAULT_NAME
        defaultStationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stationList where name not equals to UPDATED_NAME
        defaultStationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stationList where name equals to UPDATED_NAME
        defaultStationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where name is not null
        defaultStationShouldBeFound("name.specified=true");

        // Get all the stationList where name is null
        defaultStationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStationsByNameContainsSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where name contains DEFAULT_NAME
        defaultStationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stationList where name contains UPDATED_NAME
        defaultStationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where name does not contain DEFAULT_NAME
        defaultStationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stationList where name does not contain UPDATED_NAME
        defaultStationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStationsByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where desc equals to DEFAULT_DESC
        defaultStationShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the stationList where desc equals to UPDATED_DESC
        defaultStationShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllStationsByDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where desc not equals to DEFAULT_DESC
        defaultStationShouldNotBeFound("desc.notEquals=" + DEFAULT_DESC);

        // Get all the stationList where desc not equals to UPDATED_DESC
        defaultStationShouldBeFound("desc.notEquals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllStationsByDescIsInShouldWork() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultStationShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the stationList where desc equals to UPDATED_DESC
        defaultStationShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllStationsByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where desc is not null
        defaultStationShouldBeFound("desc.specified=true");

        // Get all the stationList where desc is null
        defaultStationShouldNotBeFound("desc.specified=false");
    }
                @Test
    @Transactional
    public void getAllStationsByDescContainsSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where desc contains DEFAULT_DESC
        defaultStationShouldBeFound("desc.contains=" + DEFAULT_DESC);

        // Get all the stationList where desc contains UPDATED_DESC
        defaultStationShouldNotBeFound("desc.contains=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllStationsByDescNotContainsSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where desc does not contain DEFAULT_DESC
        defaultStationShouldNotBeFound("desc.doesNotContain=" + DEFAULT_DESC);

        // Get all the stationList where desc does not contain UPDATED_DESC
        defaultStationShouldBeFound("desc.doesNotContain=" + UPDATED_DESC);
    }


    @Test
    @Transactional
    public void getAllStationsByPostionIsEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where postion equals to DEFAULT_POSTION
        defaultStationShouldBeFound("postion.equals=" + DEFAULT_POSTION);

        // Get all the stationList where postion equals to UPDATED_POSTION
        defaultStationShouldNotBeFound("postion.equals=" + UPDATED_POSTION);
    }

    @Test
    @Transactional
    public void getAllStationsByPostionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where postion not equals to DEFAULT_POSTION
        defaultStationShouldNotBeFound("postion.notEquals=" + DEFAULT_POSTION);

        // Get all the stationList where postion not equals to UPDATED_POSTION
        defaultStationShouldBeFound("postion.notEquals=" + UPDATED_POSTION);
    }

    @Test
    @Transactional
    public void getAllStationsByPostionIsInShouldWork() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where postion in DEFAULT_POSTION or UPDATED_POSTION
        defaultStationShouldBeFound("postion.in=" + DEFAULT_POSTION + "," + UPDATED_POSTION);

        // Get all the stationList where postion equals to UPDATED_POSTION
        defaultStationShouldNotBeFound("postion.in=" + UPDATED_POSTION);
    }

    @Test
    @Transactional
    public void getAllStationsByPostionIsNullOrNotNull() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where postion is not null
        defaultStationShouldBeFound("postion.specified=true");

        // Get all the stationList where postion is null
        defaultStationShouldNotBeFound("postion.specified=false");
    }
                @Test
    @Transactional
    public void getAllStationsByPostionContainsSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where postion contains DEFAULT_POSTION
        defaultStationShouldBeFound("postion.contains=" + DEFAULT_POSTION);

        // Get all the stationList where postion contains UPDATED_POSTION
        defaultStationShouldNotBeFound("postion.contains=" + UPDATED_POSTION);
    }

    @Test
    @Transactional
    public void getAllStationsByPostionNotContainsSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where postion does not contain DEFAULT_POSTION
        defaultStationShouldNotBeFound("postion.doesNotContain=" + DEFAULT_POSTION);

        // Get all the stationList where postion does not contain UPDATED_POSTION
        defaultStationShouldBeFound("postion.doesNotContain=" + UPDATED_POSTION);
    }


    @Test
    @Transactional
    public void getAllStationsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where enabled equals to DEFAULT_ENABLED
        defaultStationShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the stationList where enabled equals to UPDATED_ENABLED
        defaultStationShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllStationsByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where enabled not equals to DEFAULT_ENABLED
        defaultStationShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the stationList where enabled not equals to UPDATED_ENABLED
        defaultStationShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllStationsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultStationShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the stationList where enabled equals to UPDATED_ENABLED
        defaultStationShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllStationsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList where enabled is not null
        defaultStationShouldBeFound("enabled.specified=true");

        // Get all the stationList where enabled is null
        defaultStationShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllStationsByPowerbankIsEqualToSomething() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);
        Powerbank powerbank = PowerbankResourceIT.createEntity(em);
        em.persist(powerbank);
        em.flush();
        station.addPowerbank(powerbank);
        stationRepository.saveAndFlush(station);
        Long powerbankId = powerbank.getId();

        // Get all the stationList where powerbank equals to powerbankId
        defaultStationShouldBeFound("powerbankId.equals=" + powerbankId);

        // Get all the stationList where powerbank equals to powerbankId + 1
        defaultStationShouldNotBeFound("powerbankId.equals=" + (powerbankId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStationShouldBeFound(String filter) throws Exception {
        restStationMockMvc.perform(get("/api/stations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(station.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].postion").value(hasItem(DEFAULT_POSTION)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));

        // Check, that the count call also returns 1
        restStationMockMvc.perform(get("/api/stations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStationShouldNotBeFound(String filter) throws Exception {
        restStationMockMvc.perform(get("/api/stations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStationMockMvc.perform(get("/api/stations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStation() throws Exception {
        // Get the station
        restStationMockMvc.perform(get("/api/stations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station
        Station updatedStation = stationRepository.findById(station.getId()).get();
        // Disconnect from session so that the updates on updatedStation are not directly saved in db
        em.detach(updatedStation);
        updatedStation
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .postion(UPDATED_POSTION)
            .enabled(UPDATED_ENABLED);
        StationDTO stationDTO = stationMapper.toDto(updatedStation);

        restStationMockMvc.perform(put("/api/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStation.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testStation.getPostion()).isEqualTo(UPDATED_POSTION);
        assertThat(testStation.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStationMockMvc.perform(put("/api/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeDelete = stationRepository.findAll().size();

        // Delete the station
        restStationMockMvc.perform(delete("/api/stations/{id}", station.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
