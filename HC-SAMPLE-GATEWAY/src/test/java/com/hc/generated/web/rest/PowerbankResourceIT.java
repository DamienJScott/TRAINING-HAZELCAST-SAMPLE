package com.hc.generated.web.rest;

import com.hc.generated.HcSampleApp;
import com.hc.generated.domain.Powerbank;
import com.hc.generated.domain.Station;
import com.hc.generated.repository.PowerbankRepository;
import com.hc.generated.service.PowerbankService;
import com.hc.generated.service.dto.PowerbankDTO;
import com.hc.generated.service.mapper.PowerbankMapper;
import com.hc.generated.service.dto.PowerbankCriteria;
import com.hc.generated.service.PowerbankQueryService;

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
 * Integration tests for the {@link PowerbankResource} REST controller.
 */
@SpringBootTest(classes = HcSampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PowerbankResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_USING = false;
    private static final Boolean UPDATED_IS_USING = true;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private PowerbankRepository powerbankRepository;

    @Autowired
    private PowerbankMapper powerbankMapper;

    @Autowired
    private PowerbankService powerbankService;

    @Autowired
    private PowerbankQueryService powerbankQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPowerbankMockMvc;

    private Powerbank powerbank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Powerbank createEntity(EntityManager em) {
        Powerbank powerbank = new Powerbank()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .type(DEFAULT_TYPE)
            .position(DEFAULT_POSITION)
            .isUsing(DEFAULT_IS_USING)
            .enabled(DEFAULT_ENABLED);
        return powerbank;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Powerbank createUpdatedEntity(EntityManager em) {
        Powerbank powerbank = new Powerbank()
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .type(UPDATED_TYPE)
            .position(UPDATED_POSITION)
            .isUsing(UPDATED_IS_USING)
            .enabled(UPDATED_ENABLED);
        return powerbank;
    }

    @BeforeEach
    public void initTest() {
        powerbank = createEntity(em);
    }

    @Test
    @Transactional
    public void createPowerbank() throws Exception {
        int databaseSizeBeforeCreate = powerbankRepository.findAll().size();
        // Create the Powerbank
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(powerbank);
        restPowerbankMockMvc.perform(post("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isCreated());

        // Validate the Powerbank in the database
        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeCreate + 1);
        Powerbank testPowerbank = powerbankList.get(powerbankList.size() - 1);
        assertThat(testPowerbank.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPowerbank.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPowerbank.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPowerbank.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testPowerbank.isIsUsing()).isEqualTo(DEFAULT_IS_USING);
        assertThat(testPowerbank.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createPowerbankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = powerbankRepository.findAll().size();

        // Create the Powerbank with an existing ID
        powerbank.setId(1L);
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(powerbank);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPowerbankMockMvc.perform(post("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Powerbank in the database
        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerbankRepository.findAll().size();
        // set the field null
        powerbank.setName(null);

        // Create the Powerbank, which fails.
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(powerbank);


        restPowerbankMockMvc.perform(post("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isBadRequest());

        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerbankRepository.findAll().size();
        // set the field null
        powerbank.setType(null);

        // Create the Powerbank, which fails.
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(powerbank);


        restPowerbankMockMvc.perform(post("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isBadRequest());

        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerbankRepository.findAll().size();
        // set the field null
        powerbank.setPosition(null);

        // Create the Powerbank, which fails.
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(powerbank);


        restPowerbankMockMvc.perform(post("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isBadRequest());

        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsUsingIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerbankRepository.findAll().size();
        // set the field null
        powerbank.setIsUsing(null);

        // Create the Powerbank, which fails.
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(powerbank);


        restPowerbankMockMvc.perform(post("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isBadRequest());

        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerbankRepository.findAll().size();
        // set the field null
        powerbank.setEnabled(null);

        // Create the Powerbank, which fails.
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(powerbank);


        restPowerbankMockMvc.perform(post("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isBadRequest());

        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPowerbanks() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList
        restPowerbankMockMvc.perform(get("/api/powerbanks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerbank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].isUsing").value(hasItem(DEFAULT_IS_USING.booleanValue())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPowerbank() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get the powerbank
        restPowerbankMockMvc.perform(get("/api/powerbanks/{id}", powerbank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(powerbank.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.isUsing").value(DEFAULT_IS_USING.booleanValue()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }


    @Test
    @Transactional
    public void getPowerbanksByIdFiltering() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        Long id = powerbank.getId();

        defaultPowerbankShouldBeFound("id.equals=" + id);
        defaultPowerbankShouldNotBeFound("id.notEquals=" + id);

        defaultPowerbankShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPowerbankShouldNotBeFound("id.greaterThan=" + id);

        defaultPowerbankShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPowerbankShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPowerbanksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where name equals to DEFAULT_NAME
        defaultPowerbankShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the powerbankList where name equals to UPDATED_NAME
        defaultPowerbankShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where name not equals to DEFAULT_NAME
        defaultPowerbankShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the powerbankList where name not equals to UPDATED_NAME
        defaultPowerbankShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPowerbankShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the powerbankList where name equals to UPDATED_NAME
        defaultPowerbankShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where name is not null
        defaultPowerbankShouldBeFound("name.specified=true");

        // Get all the powerbankList where name is null
        defaultPowerbankShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerbanksByNameContainsSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where name contains DEFAULT_NAME
        defaultPowerbankShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the powerbankList where name contains UPDATED_NAME
        defaultPowerbankShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where name does not contain DEFAULT_NAME
        defaultPowerbankShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the powerbankList where name does not contain UPDATED_NAME
        defaultPowerbankShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPowerbanksByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where desc equals to DEFAULT_DESC
        defaultPowerbankShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the powerbankList where desc equals to UPDATED_DESC
        defaultPowerbankShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where desc not equals to DEFAULT_DESC
        defaultPowerbankShouldNotBeFound("desc.notEquals=" + DEFAULT_DESC);

        // Get all the powerbankList where desc not equals to UPDATED_DESC
        defaultPowerbankShouldBeFound("desc.notEquals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByDescIsInShouldWork() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultPowerbankShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the powerbankList where desc equals to UPDATED_DESC
        defaultPowerbankShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where desc is not null
        defaultPowerbankShouldBeFound("desc.specified=true");

        // Get all the powerbankList where desc is null
        defaultPowerbankShouldNotBeFound("desc.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerbanksByDescContainsSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where desc contains DEFAULT_DESC
        defaultPowerbankShouldBeFound("desc.contains=" + DEFAULT_DESC);

        // Get all the powerbankList where desc contains UPDATED_DESC
        defaultPowerbankShouldNotBeFound("desc.contains=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByDescNotContainsSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where desc does not contain DEFAULT_DESC
        defaultPowerbankShouldNotBeFound("desc.doesNotContain=" + DEFAULT_DESC);

        // Get all the powerbankList where desc does not contain UPDATED_DESC
        defaultPowerbankShouldBeFound("desc.doesNotContain=" + UPDATED_DESC);
    }


    @Test
    @Transactional
    public void getAllPowerbanksByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where type equals to DEFAULT_TYPE
        defaultPowerbankShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the powerbankList where type equals to UPDATED_TYPE
        defaultPowerbankShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where type not equals to DEFAULT_TYPE
        defaultPowerbankShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the powerbankList where type not equals to UPDATED_TYPE
        defaultPowerbankShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPowerbankShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the powerbankList where type equals to UPDATED_TYPE
        defaultPowerbankShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where type is not null
        defaultPowerbankShouldBeFound("type.specified=true");

        // Get all the powerbankList where type is null
        defaultPowerbankShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerbanksByTypeContainsSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where type contains DEFAULT_TYPE
        defaultPowerbankShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the powerbankList where type contains UPDATED_TYPE
        defaultPowerbankShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where type does not contain DEFAULT_TYPE
        defaultPowerbankShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the powerbankList where type does not contain UPDATED_TYPE
        defaultPowerbankShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllPowerbanksByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where position equals to DEFAULT_POSITION
        defaultPowerbankShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the powerbankList where position equals to UPDATED_POSITION
        defaultPowerbankShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where position not equals to DEFAULT_POSITION
        defaultPowerbankShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the powerbankList where position not equals to UPDATED_POSITION
        defaultPowerbankShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultPowerbankShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the powerbankList where position equals to UPDATED_POSITION
        defaultPowerbankShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where position is not null
        defaultPowerbankShouldBeFound("position.specified=true");

        // Get all the powerbankList where position is null
        defaultPowerbankShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerbanksByPositionContainsSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where position contains DEFAULT_POSITION
        defaultPowerbankShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the powerbankList where position contains UPDATED_POSITION
        defaultPowerbankShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where position does not contain DEFAULT_POSITION
        defaultPowerbankShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the powerbankList where position does not contain UPDATED_POSITION
        defaultPowerbankShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllPowerbanksByIsUsingIsEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where isUsing equals to DEFAULT_IS_USING
        defaultPowerbankShouldBeFound("isUsing.equals=" + DEFAULT_IS_USING);

        // Get all the powerbankList where isUsing equals to UPDATED_IS_USING
        defaultPowerbankShouldNotBeFound("isUsing.equals=" + UPDATED_IS_USING);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByIsUsingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where isUsing not equals to DEFAULT_IS_USING
        defaultPowerbankShouldNotBeFound("isUsing.notEquals=" + DEFAULT_IS_USING);

        // Get all the powerbankList where isUsing not equals to UPDATED_IS_USING
        defaultPowerbankShouldBeFound("isUsing.notEquals=" + UPDATED_IS_USING);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByIsUsingIsInShouldWork() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where isUsing in DEFAULT_IS_USING or UPDATED_IS_USING
        defaultPowerbankShouldBeFound("isUsing.in=" + DEFAULT_IS_USING + "," + UPDATED_IS_USING);

        // Get all the powerbankList where isUsing equals to UPDATED_IS_USING
        defaultPowerbankShouldNotBeFound("isUsing.in=" + UPDATED_IS_USING);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByIsUsingIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where isUsing is not null
        defaultPowerbankShouldBeFound("isUsing.specified=true");

        // Get all the powerbankList where isUsing is null
        defaultPowerbankShouldNotBeFound("isUsing.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerbanksByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where enabled equals to DEFAULT_ENABLED
        defaultPowerbankShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the powerbankList where enabled equals to UPDATED_ENABLED
        defaultPowerbankShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where enabled not equals to DEFAULT_ENABLED
        defaultPowerbankShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the powerbankList where enabled not equals to UPDATED_ENABLED
        defaultPowerbankShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultPowerbankShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the powerbankList where enabled equals to UPDATED_ENABLED
        defaultPowerbankShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllPowerbanksByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        // Get all the powerbankList where enabled is not null
        defaultPowerbankShouldBeFound("enabled.specified=true");

        // Get all the powerbankList where enabled is null
        defaultPowerbankShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerbanksByStationIsEqualToSomething() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);
        Station station = StationResourceIT.createEntity(em);
        em.persist(station);
        em.flush();
        powerbank.setStation(station);
        powerbankRepository.saveAndFlush(powerbank);
        Long stationId = station.getId();

        // Get all the powerbankList where station equals to stationId
        defaultPowerbankShouldBeFound("stationId.equals=" + stationId);

        // Get all the powerbankList where station equals to stationId + 1
        defaultPowerbankShouldNotBeFound("stationId.equals=" + (stationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPowerbankShouldBeFound(String filter) throws Exception {
        restPowerbankMockMvc.perform(get("/api/powerbanks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerbank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].isUsing").value(hasItem(DEFAULT_IS_USING.booleanValue())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));

        // Check, that the count call also returns 1
        restPowerbankMockMvc.perform(get("/api/powerbanks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPowerbankShouldNotBeFound(String filter) throws Exception {
        restPowerbankMockMvc.perform(get("/api/powerbanks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPowerbankMockMvc.perform(get("/api/powerbanks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPowerbank() throws Exception {
        // Get the powerbank
        restPowerbankMockMvc.perform(get("/api/powerbanks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePowerbank() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        int databaseSizeBeforeUpdate = powerbankRepository.findAll().size();

        // Update the powerbank
        Powerbank updatedPowerbank = powerbankRepository.findById(powerbank.getId()).get();
        // Disconnect from session so that the updates on updatedPowerbank are not directly saved in db
        em.detach(updatedPowerbank);
        updatedPowerbank
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .type(UPDATED_TYPE)
            .position(UPDATED_POSITION)
            .isUsing(UPDATED_IS_USING)
            .enabled(UPDATED_ENABLED);
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(updatedPowerbank);

        restPowerbankMockMvc.perform(put("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isOk());

        // Validate the Powerbank in the database
        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeUpdate);
        Powerbank testPowerbank = powerbankList.get(powerbankList.size() - 1);
        assertThat(testPowerbank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPowerbank.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPowerbank.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPowerbank.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testPowerbank.isIsUsing()).isEqualTo(UPDATED_IS_USING);
        assertThat(testPowerbank.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingPowerbank() throws Exception {
        int databaseSizeBeforeUpdate = powerbankRepository.findAll().size();

        // Create the Powerbank
        PowerbankDTO powerbankDTO = powerbankMapper.toDto(powerbank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerbankMockMvc.perform(put("/api/powerbanks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerbankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Powerbank in the database
        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePowerbank() throws Exception {
        // Initialize the database
        powerbankRepository.saveAndFlush(powerbank);

        int databaseSizeBeforeDelete = powerbankRepository.findAll().size();

        // Delete the powerbank
        restPowerbankMockMvc.perform(delete("/api/powerbanks/{id}", powerbank.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Powerbank> powerbankList = powerbankRepository.findAll();
        assertThat(powerbankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
