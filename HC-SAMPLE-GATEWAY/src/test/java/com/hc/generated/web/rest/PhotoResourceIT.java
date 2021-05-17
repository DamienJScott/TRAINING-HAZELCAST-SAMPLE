package com.hc.generated.web.rest;

import com.hc.generated.HcSampleApp;
import com.hc.generated.domain.Photo;
import com.hc.generated.domain.Album;
import com.hc.generated.repository.PhotoRepository;
import com.hc.generated.service.PhotoService;
import com.hc.generated.service.dto.PhotoDTO;
import com.hc.generated.service.mapper.PhotoMapper;
import com.hc.generated.service.dto.PhotoCriteria;
import com.hc.generated.service.PhotoQueryService;

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
 * Integration tests for the {@link PhotoResource} REST controller.
 */
@SpringBootTest(classes = HcSampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhotoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_SRC = "AAAAAAAAAA";
    private static final String UPDATED_SRC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PRIVATE = false;
    private static final Boolean UPDATED_IS_PRIVATE = true;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoQueryService photoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhotoMockMvc;

    private Photo photo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photo createEntity(EntityManager em) {
        Photo photo = new Photo()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .src(DEFAULT_SRC)
            .isPrivate(DEFAULT_IS_PRIVATE);
        return photo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photo createUpdatedEntity(EntityManager em) {
        Photo photo = new Photo()
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .src(UPDATED_SRC)
            .isPrivate(UPDATED_IS_PRIVATE);
        return photo;
    }

    @BeforeEach
    public void initTest() {
        photo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoto() throws Exception {
        int databaseSizeBeforeCreate = photoRepository.findAll().size();
        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);
        restPhotoMockMvc.perform(post("/api/photos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isCreated());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeCreate + 1);
        Photo testPhoto = photoList.get(photoList.size() - 1);
        assertThat(testPhoto.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPhoto.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPhoto.getSrc()).isEqualTo(DEFAULT_SRC);
        assertThat(testPhoto.isIsPrivate()).isEqualTo(DEFAULT_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void createPhotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = photoRepository.findAll().size();

        // Create the Photo with an existing ID
        photo.setId(1L);
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotoMockMvc.perform(post("/api/photos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = photoRepository.findAll().size();
        // set the field null
        photo.setName(null);

        // Create the Photo, which fails.
        PhotoDTO photoDTO = photoMapper.toDto(photo);


        restPhotoMockMvc.perform(post("/api/photos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isBadRequest());

        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSrcIsRequired() throws Exception {
        int databaseSizeBeforeTest = photoRepository.findAll().size();
        // set the field null
        photo.setSrc(null);

        // Create the Photo, which fails.
        PhotoDTO photoDTO = photoMapper.toDto(photo);


        restPhotoMockMvc.perform(post("/api/photos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isBadRequest());

        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhotos() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList
        restPhotoMockMvc.perform(get("/api/photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].src").value(hasItem(DEFAULT_SRC)))
            .andExpect(jsonPath("$.[*].isPrivate").value(hasItem(DEFAULT_IS_PRIVATE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPhoto() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get the photo
        restPhotoMockMvc.perform(get("/api/photos/{id}", photo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(photo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.src").value(DEFAULT_SRC))
            .andExpect(jsonPath("$.isPrivate").value(DEFAULT_IS_PRIVATE.booleanValue()));
    }


    @Test
    @Transactional
    public void getPhotosByIdFiltering() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        Long id = photo.getId();

        defaultPhotoShouldBeFound("id.equals=" + id);
        defaultPhotoShouldNotBeFound("id.notEquals=" + id);

        defaultPhotoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPhotoShouldNotBeFound("id.greaterThan=" + id);

        defaultPhotoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPhotoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPhotosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where name equals to DEFAULT_NAME
        defaultPhotoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the photoList where name equals to UPDATED_NAME
        defaultPhotoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where name not equals to DEFAULT_NAME
        defaultPhotoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the photoList where name not equals to UPDATED_NAME
        defaultPhotoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPhotoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the photoList where name equals to UPDATED_NAME
        defaultPhotoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where name is not null
        defaultPhotoShouldBeFound("name.specified=true");

        // Get all the photoList where name is null
        defaultPhotoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByNameContainsSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where name contains DEFAULT_NAME
        defaultPhotoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the photoList where name contains UPDATED_NAME
        defaultPhotoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where name does not contain DEFAULT_NAME
        defaultPhotoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the photoList where name does not contain UPDATED_NAME
        defaultPhotoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPhotosByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where desc equals to DEFAULT_DESC
        defaultPhotoShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the photoList where desc equals to UPDATED_DESC
        defaultPhotoShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllPhotosByDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where desc not equals to DEFAULT_DESC
        defaultPhotoShouldNotBeFound("desc.notEquals=" + DEFAULT_DESC);

        // Get all the photoList where desc not equals to UPDATED_DESC
        defaultPhotoShouldBeFound("desc.notEquals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllPhotosByDescIsInShouldWork() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultPhotoShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the photoList where desc equals to UPDATED_DESC
        defaultPhotoShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllPhotosByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where desc is not null
        defaultPhotoShouldBeFound("desc.specified=true");

        // Get all the photoList where desc is null
        defaultPhotoShouldNotBeFound("desc.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByDescContainsSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where desc contains DEFAULT_DESC
        defaultPhotoShouldBeFound("desc.contains=" + DEFAULT_DESC);

        // Get all the photoList where desc contains UPDATED_DESC
        defaultPhotoShouldNotBeFound("desc.contains=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllPhotosByDescNotContainsSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where desc does not contain DEFAULT_DESC
        defaultPhotoShouldNotBeFound("desc.doesNotContain=" + DEFAULT_DESC);

        // Get all the photoList where desc does not contain UPDATED_DESC
        defaultPhotoShouldBeFound("desc.doesNotContain=" + UPDATED_DESC);
    }


    @Test
    @Transactional
    public void getAllPhotosBySrcIsEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where src equals to DEFAULT_SRC
        defaultPhotoShouldBeFound("src.equals=" + DEFAULT_SRC);

        // Get all the photoList where src equals to UPDATED_SRC
        defaultPhotoShouldNotBeFound("src.equals=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    public void getAllPhotosBySrcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where src not equals to DEFAULT_SRC
        defaultPhotoShouldNotBeFound("src.notEquals=" + DEFAULT_SRC);

        // Get all the photoList where src not equals to UPDATED_SRC
        defaultPhotoShouldBeFound("src.notEquals=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    public void getAllPhotosBySrcIsInShouldWork() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where src in DEFAULT_SRC or UPDATED_SRC
        defaultPhotoShouldBeFound("src.in=" + DEFAULT_SRC + "," + UPDATED_SRC);

        // Get all the photoList where src equals to UPDATED_SRC
        defaultPhotoShouldNotBeFound("src.in=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    public void getAllPhotosBySrcIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where src is not null
        defaultPhotoShouldBeFound("src.specified=true");

        // Get all the photoList where src is null
        defaultPhotoShouldNotBeFound("src.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosBySrcContainsSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where src contains DEFAULT_SRC
        defaultPhotoShouldBeFound("src.contains=" + DEFAULT_SRC);

        // Get all the photoList where src contains UPDATED_SRC
        defaultPhotoShouldNotBeFound("src.contains=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    public void getAllPhotosBySrcNotContainsSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where src does not contain DEFAULT_SRC
        defaultPhotoShouldNotBeFound("src.doesNotContain=" + DEFAULT_SRC);

        // Get all the photoList where src does not contain UPDATED_SRC
        defaultPhotoShouldBeFound("src.doesNotContain=" + UPDATED_SRC);
    }


    @Test
    @Transactional
    public void getAllPhotosByIsPrivateIsEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where isPrivate equals to DEFAULT_IS_PRIVATE
        defaultPhotoShouldBeFound("isPrivate.equals=" + DEFAULT_IS_PRIVATE);

        // Get all the photoList where isPrivate equals to UPDATED_IS_PRIVATE
        defaultPhotoShouldNotBeFound("isPrivate.equals=" + UPDATED_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void getAllPhotosByIsPrivateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where isPrivate not equals to DEFAULT_IS_PRIVATE
        defaultPhotoShouldNotBeFound("isPrivate.notEquals=" + DEFAULT_IS_PRIVATE);

        // Get all the photoList where isPrivate not equals to UPDATED_IS_PRIVATE
        defaultPhotoShouldBeFound("isPrivate.notEquals=" + UPDATED_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void getAllPhotosByIsPrivateIsInShouldWork() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where isPrivate in DEFAULT_IS_PRIVATE or UPDATED_IS_PRIVATE
        defaultPhotoShouldBeFound("isPrivate.in=" + DEFAULT_IS_PRIVATE + "," + UPDATED_IS_PRIVATE);

        // Get all the photoList where isPrivate equals to UPDATED_IS_PRIVATE
        defaultPhotoShouldNotBeFound("isPrivate.in=" + UPDATED_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void getAllPhotosByIsPrivateIsNullOrNotNull() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photoList where isPrivate is not null
        defaultPhotoShouldBeFound("isPrivate.specified=true");

        // Get all the photoList where isPrivate is null
        defaultPhotoShouldNotBeFound("isPrivate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosByAlbumIsEqualToSomething() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);
        Album album = AlbumResourceIT.createEntity(em);
        em.persist(album);
        em.flush();
        photo.setAlbum(album);
        photoRepository.saveAndFlush(photo);
        Long albumId = album.getId();

        // Get all the photoList where album equals to albumId
        defaultPhotoShouldBeFound("albumId.equals=" + albumId);

        // Get all the photoList where album equals to albumId + 1
        defaultPhotoShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhotoShouldBeFound(String filter) throws Exception {
        restPhotoMockMvc.perform(get("/api/photos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].src").value(hasItem(DEFAULT_SRC)))
            .andExpect(jsonPath("$.[*].isPrivate").value(hasItem(DEFAULT_IS_PRIVATE.booleanValue())));

        // Check, that the count call also returns 1
        restPhotoMockMvc.perform(get("/api/photos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhotoShouldNotBeFound(String filter) throws Exception {
        restPhotoMockMvc.perform(get("/api/photos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhotoMockMvc.perform(get("/api/photos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPhoto() throws Exception {
        // Get the photo
        restPhotoMockMvc.perform(get("/api/photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoto() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        int databaseSizeBeforeUpdate = photoRepository.findAll().size();

        // Update the photo
        Photo updatedPhoto = photoRepository.findById(photo.getId()).get();
        // Disconnect from session so that the updates on updatedPhoto are not directly saved in db
        em.detach(updatedPhoto);
        updatedPhoto
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .src(UPDATED_SRC)
            .isPrivate(UPDATED_IS_PRIVATE);
        PhotoDTO photoDTO = photoMapper.toDto(updatedPhoto);

        restPhotoMockMvc.perform(put("/api/photos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isOk());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
        Photo testPhoto = photoList.get(photoList.size() - 1);
        assertThat(testPhoto.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPhoto.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPhoto.getSrc()).isEqualTo(UPDATED_SRC);
        assertThat(testPhoto.isIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoto() throws Exception {
        int databaseSizeBeforeUpdate = photoRepository.findAll().size();

        // Create the Photo
        PhotoDTO photoDTO = photoMapper.toDto(photo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotoMockMvc.perform(put("/api/photos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photo in the database
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhoto() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        int databaseSizeBeforeDelete = photoRepository.findAll().size();

        // Delete the photo
        restPhotoMockMvc.perform(delete("/api/photos/{id}", photo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Photo> photoList = photoRepository.findAll();
        assertThat(photoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
