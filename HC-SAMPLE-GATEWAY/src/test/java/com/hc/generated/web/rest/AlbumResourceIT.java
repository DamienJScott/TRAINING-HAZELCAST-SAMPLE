package com.hc.generated.web.rest;

import com.hc.generated.HcSampleApp;
import com.hc.generated.domain.Album;
import com.hc.generated.domain.Photo;
import com.hc.generated.repository.AlbumRepository;
import com.hc.generated.service.AlbumService;
import com.hc.generated.service.dto.AlbumDTO;
import com.hc.generated.service.mapper.AlbumMapper;
import com.hc.generated.service.dto.AlbumCriteria;
import com.hc.generated.service.AlbumQueryService;

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
 * Integration tests for the {@link AlbumResource} REST controller.
 */
@SpringBootTest(classes = HcSampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlbumResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumQueryService albumQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlbumMockMvc;

    private Album album;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createEntity(EntityManager em) {
        Album album = new Album()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .detail(DEFAULT_DETAIL);
        return album;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createUpdatedEntity(EntityManager em) {
        Album album = new Album()
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .detail(UPDATED_DETAIL);
        return album;
    }

    @BeforeEach
    public void initTest() {
        album = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbum() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();
        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate + 1);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlbum.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testAlbum.getDetail()).isEqualTo(DEFAULT_DETAIL);
    }

    @Test
    @Transactional
    public void createAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album with an existing ID
        album.setId(1L);
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = albumRepository.findAll().size();
        // set the field null
        album.setName(null);

        // Create the Album, which fails.
        AlbumDTO albumDTO = albumMapper.toDto(album);


        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlbums() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)));
    }
    
    @Test
    @Transactional
    public void getAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", album.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(album.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL));
    }


    @Test
    @Transactional
    public void getAlbumsByIdFiltering() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        Long id = album.getId();

        defaultAlbumShouldBeFound("id.equals=" + id);
        defaultAlbumShouldNotBeFound("id.notEquals=" + id);

        defaultAlbumShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlbumShouldNotBeFound("id.greaterThan=" + id);

        defaultAlbumShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlbumShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlbumsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name equals to DEFAULT_NAME
        defaultAlbumShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the albumList where name equals to UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name not equals to DEFAULT_NAME
        defaultAlbumShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the albumList where name not equals to UPDATED_NAME
        defaultAlbumShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAlbumShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the albumList where name equals to UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name is not null
        defaultAlbumShouldBeFound("name.specified=true");

        // Get all the albumList where name is null
        defaultAlbumShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByNameContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name contains DEFAULT_NAME
        defaultAlbumShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the albumList where name contains UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name does not contain DEFAULT_NAME
        defaultAlbumShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the albumList where name does not contain UPDATED_NAME
        defaultAlbumShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAlbumsByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where desc equals to DEFAULT_DESC
        defaultAlbumShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the albumList where desc equals to UPDATED_DESC
        defaultAlbumShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where desc not equals to DEFAULT_DESC
        defaultAlbumShouldNotBeFound("desc.notEquals=" + DEFAULT_DESC);

        // Get all the albumList where desc not equals to UPDATED_DESC
        defaultAlbumShouldBeFound("desc.notEquals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDescIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultAlbumShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the albumList where desc equals to UPDATED_DESC
        defaultAlbumShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where desc is not null
        defaultAlbumShouldBeFound("desc.specified=true");

        // Get all the albumList where desc is null
        defaultAlbumShouldNotBeFound("desc.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByDescContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where desc contains DEFAULT_DESC
        defaultAlbumShouldBeFound("desc.contains=" + DEFAULT_DESC);

        // Get all the albumList where desc contains UPDATED_DESC
        defaultAlbumShouldNotBeFound("desc.contains=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDescNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where desc does not contain DEFAULT_DESC
        defaultAlbumShouldNotBeFound("desc.doesNotContain=" + DEFAULT_DESC);

        // Get all the albumList where desc does not contain UPDATED_DESC
        defaultAlbumShouldBeFound("desc.doesNotContain=" + UPDATED_DESC);
    }


    @Test
    @Transactional
    public void getAllAlbumsByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where detail equals to DEFAULT_DETAIL
        defaultAlbumShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the albumList where detail equals to UPDATED_DETAIL
        defaultAlbumShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDetailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where detail not equals to DEFAULT_DETAIL
        defaultAlbumShouldNotBeFound("detail.notEquals=" + DEFAULT_DETAIL);

        // Get all the albumList where detail not equals to UPDATED_DETAIL
        defaultAlbumShouldBeFound("detail.notEquals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultAlbumShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the albumList where detail equals to UPDATED_DETAIL
        defaultAlbumShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where detail is not null
        defaultAlbumShouldBeFound("detail.specified=true");

        // Get all the albumList where detail is null
        defaultAlbumShouldNotBeFound("detail.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByDetailContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where detail contains DEFAULT_DETAIL
        defaultAlbumShouldBeFound("detail.contains=" + DEFAULT_DETAIL);

        // Get all the albumList where detail contains UPDATED_DETAIL
        defaultAlbumShouldNotBeFound("detail.contains=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDetailNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where detail does not contain DEFAULT_DETAIL
        defaultAlbumShouldNotBeFound("detail.doesNotContain=" + DEFAULT_DETAIL);

        // Get all the albumList where detail does not contain UPDATED_DETAIL
        defaultAlbumShouldBeFound("detail.doesNotContain=" + UPDATED_DETAIL);
    }


    @Test
    @Transactional
    public void getAllAlbumsByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);
        Photo photo = PhotoResourceIT.createEntity(em);
        em.persist(photo);
        em.flush();
        album.addPhoto(photo);
        albumRepository.saveAndFlush(album);
        Long photoId = photo.getId();

        // Get all the albumList where photo equals to photoId
        defaultAlbumShouldBeFound("photoId.equals=" + photoId);

        // Get all the albumList where photo equals to photoId + 1
        defaultAlbumShouldNotBeFound("photoId.equals=" + (photoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlbumShouldBeFound(String filter) throws Exception {
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)));

        // Check, that the count call also returns 1
        restAlbumMockMvc.perform(get("/api/albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlbumShouldNotBeFound(String filter) throws Exception {
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlbumMockMvc.perform(get("/api/albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlbum() throws Exception {
        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Update the album
        Album updatedAlbum = albumRepository.findById(album.getId()).get();
        // Disconnect from session so that the updates on updatedAlbum are not directly saved in db
        em.detach(updatedAlbum);
        updatedAlbum
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .detail(UPDATED_DETAIL);
        AlbumDTO albumDTO = albumMapper.toDto(updatedAlbum);

        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isOk());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlbum.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testAlbum.getDetail()).isEqualTo(UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbum() throws Exception {
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeDelete = albumRepository.findAll().size();

        // Delete the album
        restAlbumMockMvc.perform(delete("/api/albums/{id}", album.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
