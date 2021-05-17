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

import com.hc.generated.domain.Station;
import com.hc.generated.domain.*; // for static metamodels
import com.hc.generated.repository.StationRepository;
import com.hc.generated.service.dto.StationCriteria;
import com.hc.generated.service.dto.StationDTO;
import com.hc.generated.service.mapper.StationMapper;

/**
 * Service for executing complex queries for {@link Station} entities in the database.
 * The main input is a {@link StationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StationDTO} or a {@link Page} of {@link StationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StationQueryService extends QueryService<Station> {

    private final Logger log = LoggerFactory.getLogger(StationQueryService.class);

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    public StationQueryService(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    /**
     * Return a {@link List} of {@link StationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StationDTO> findByCriteria(StationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Station> specification = createSpecification(criteria);
        return stationMapper.toDto(stationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StationDTO> findByCriteria(StationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Station> specification = createSpecification(criteria);
        return stationRepository.findAll(specification, page)
            .map(stationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Station> specification = createSpecification(criteria);
        return stationRepository.count(specification);
    }

    /**
     * Function to convert {@link StationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Station> createSpecification(StationCriteria criteria) {
        Specification<Station> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Station_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Station_.name));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), Station_.desc));
            }
            if (criteria.getPostion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostion(), Station_.postion));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Station_.enabled));
            }
            if (criteria.getPowerbankId() != null) {
                specification = specification.and(buildSpecification(criteria.getPowerbankId(),
                    root -> root.join(Station_.powerbanks, JoinType.LEFT).get(Powerbank_.id)));
            }
        }
        return specification;
    }
}
