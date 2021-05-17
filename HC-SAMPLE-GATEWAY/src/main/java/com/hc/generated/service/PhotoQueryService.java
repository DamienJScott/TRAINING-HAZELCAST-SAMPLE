package com.hc.generated.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hc.generated.domain.Photo;
import com.hc.generated.domain.*; // for static metamodels
import com.hc.generated.repository.PhotoRepository;
import com.hc.generated.service.dto.PhotoCriteria;
import com.hc.generated.service.dto.PhotoDTO;
import com.hc.generated.service.mapper.PhotoMapper;

/**
 * Service for executing complex queries for {@link Photo} entities in the database.
 * The main input is a {@link PhotoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PhotoDTO} or a {@link Page} of {@link PhotoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhotoQueryService extends QueryService<Photo> {

    private final Logger log = LoggerFactory.getLogger(PhotoQueryService.class);

    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;

    public PhotoQueryService(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    /**
     * Return a {@link List} of {@link PhotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PhotoDTO> findByCriteria(PhotoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Photo> specification = createSpecification(criteria);
        return photoMapper.toDto(photoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PhotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PhotoDTO> findByCriteria(PhotoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Photo> specification = createSpecification(criteria);
        return photoRepository.findAll(specification, page)
            .map(photoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PhotoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Photo> specification = createSpecification(criteria);
        return photoRepository.count(specification);
    }

    /**
     * Function to convert {@link PhotoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Photo> createSpecification(PhotoCriteria criteria) {
        Specification<Photo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Photo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Photo_.name));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), Photo_.desc));
            }
            if (criteria.getSrc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSrc(), Photo_.src));
            }
            if (criteria.getIsPrivate() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPrivate(), Photo_.isPrivate));
            }
            if (criteria.getAlbumId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlbumId(),
                    root -> root.join(Photo_.album, JoinType.LEFT).get(Album_.id)));
            }
        }
        return specification;
    }
}
