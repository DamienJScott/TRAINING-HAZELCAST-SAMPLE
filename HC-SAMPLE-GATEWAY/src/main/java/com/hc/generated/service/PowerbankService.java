package com.hc.generated.service;

import com.hc.generated.domain.Powerbank;
import com.hc.generated.repository.PowerbankRepository;
import com.hc.generated.service.dto.PowerbankDTO;
import com.hc.generated.service.mapper.PowerbankMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Powerbank}.
 */
@Service
@Transactional
public class PowerbankService {

    private final Logger log = LoggerFactory.getLogger(PowerbankService.class);

    private final PowerbankRepository powerbankRepository;

    private final PowerbankMapper powerbankMapper;

    public PowerbankService(PowerbankRepository powerbankRepository, PowerbankMapper powerbankMapper) {
        this.powerbankRepository = powerbankRepository;
        this.powerbankMapper = powerbankMapper;
    }

    /**
     * Save a powerbank.
     *
     * @param powerbankDTO the entity to save.
     * @return the persisted entity.
     */
    public PowerbankDTO save(PowerbankDTO powerbankDTO) {
        log.debug("Request to save Powerbank : {}", powerbankDTO);
        Powerbank powerbank = powerbankMapper.toEntity(powerbankDTO);
        powerbank = powerbankRepository.save(powerbank);
        return powerbankMapper.toDto(powerbank);
    }

    /**
     * Get all the powerbanks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PowerbankDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Powerbanks");
        return powerbankRepository.findAll(pageable)
            .map(powerbankMapper::toDto);
    }


    /**
     * Get one powerbank by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PowerbankDTO> findOne(Long id) {
        log.debug("Request to get Powerbank : {}", id);
        return powerbankRepository.findById(id)
            .map(powerbankMapper::toDto);
    }

    /**
     * Delete the powerbank by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Powerbank : {}", id);
        powerbankRepository.deleteById(id);
    }
}
