package com.hc.ms.generated.service;

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

import com.hc.ms.generated.domain.Powerbank;
import com.hc.ms.generated.domain.*; // for static metamodels
import com.hc.ms.generated.repository.PowerbankRepository;
import com.hc.ms.generated.service.dto.PowerbankCriteria;
import com.hc.ms.generated.service.dto.PowerbankDTO;
import com.hc.ms.generated.service.mapper.PowerbankMapper;

/**
 * Service for executing complex queries for {@link Powerbank} entities in the database.
 * The main input is a {@link PowerbankCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PowerbankDTO} or a {@link Page} of {@link PowerbankDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PowerbankQueryService extends QueryService<Powerbank> {

    private final Logger log = LoggerFactory.getLogger(PowerbankQueryService.class);

    private final PowerbankRepository powerbankRepository;

    private final PowerbankMapper powerbankMapper;

    public PowerbankQueryService(PowerbankRepository powerbankRepository, PowerbankMapper powerbankMapper) {
        this.powerbankRepository = powerbankRepository;
        this.powerbankMapper = powerbankMapper;
    }

    /**
     * Return a {@link List} of {@link PowerbankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PowerbankDTO> findByCriteria(PowerbankCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Powerbank> specification = createSpecification(criteria);
        return powerbankMapper.toDto(powerbankRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PowerbankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PowerbankDTO> findByCriteria(PowerbankCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Powerbank> specification = createSpecification(criteria);
        return powerbankRepository.findAll(specification, page)
            .map(powerbankMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PowerbankCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Powerbank> specification = createSpecification(criteria);
        return powerbankRepository.count(specification);
    }

    /**
     * Function to convert {@link PowerbankCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Powerbank> createSpecification(PowerbankCriteria criteria) {
        Specification<Powerbank> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Powerbank_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Powerbank_.name));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), Powerbank_.desc));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Powerbank_.type));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), Powerbank_.position));
            }
            if (criteria.getIsUsing() != null) {
                specification = specification.and(buildSpecification(criteria.getIsUsing(), Powerbank_.isUsing));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Powerbank_.enabled));
            }
            if (criteria.getStationId() != null) {
                specification = specification.and(buildSpecification(criteria.getStationId(),
                    root -> root.join(Powerbank_.station, JoinType.LEFT).get(Station_.id)));
            }
        }
        return specification;
    }
}
