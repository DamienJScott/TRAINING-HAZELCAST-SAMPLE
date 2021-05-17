package com.hc.generated.web.rest;

import com.hc.generated.service.PowerbankService;
import com.hc.generated.web.rest.errors.BadRequestAlertException;
import com.hc.generated.service.dto.PowerbankDTO;
import com.hc.generated.service.dto.PowerbankCriteria;
import com.hc.generated.service.PowerbankQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hc.generated.domain.Powerbank}.
 */
@RestController
@RequestMapping("/api")
public class PowerbankResource {

    private final Logger log = LoggerFactory.getLogger(PowerbankResource.class);

    private static final String ENTITY_NAME = "powerbank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PowerbankService powerbankService;

    private final PowerbankQueryService powerbankQueryService;

    public PowerbankResource(PowerbankService powerbankService, PowerbankQueryService powerbankQueryService) {
        this.powerbankService = powerbankService;
        this.powerbankQueryService = powerbankQueryService;
    }

    /**
     * {@code POST  /powerbanks} : Create a new powerbank.
     *
     * @param powerbankDTO the powerbankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new powerbankDTO, or with status {@code 400 (Bad Request)} if the powerbank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/powerbanks")
    public ResponseEntity<PowerbankDTO> createPowerbank(@Valid @RequestBody PowerbankDTO powerbankDTO) throws URISyntaxException {
        log.debug("REST request to save Powerbank : {}", powerbankDTO);
        if (powerbankDTO.getId() != null) {
            throw new BadRequestAlertException("A new powerbank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PowerbankDTO result = powerbankService.save(powerbankDTO);
        return ResponseEntity.created(new URI("/api/powerbanks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /powerbanks} : Updates an existing powerbank.
     *
     * @param powerbankDTO the powerbankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerbankDTO,
     * or with status {@code 400 (Bad Request)} if the powerbankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the powerbankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/powerbanks")
    public ResponseEntity<PowerbankDTO> updatePowerbank(@Valid @RequestBody PowerbankDTO powerbankDTO) throws URISyntaxException {
        log.debug("REST request to update Powerbank : {}", powerbankDTO);
        if (powerbankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PowerbankDTO result = powerbankService.save(powerbankDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, powerbankDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /powerbanks} : get all the powerbanks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of powerbanks in body.
     */
    @GetMapping("/powerbanks")
    public ResponseEntity<List<PowerbankDTO>> getAllPowerbanks(PowerbankCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Powerbanks by criteria: {}", criteria);
        Page<PowerbankDTO> page = powerbankQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /powerbanks/count} : count all the powerbanks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/powerbanks/count")
    public ResponseEntity<Long> countPowerbanks(PowerbankCriteria criteria) {
        log.debug("REST request to count Powerbanks by criteria: {}", criteria);
        return ResponseEntity.ok().body(powerbankQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /powerbanks/:id} : get the "id" powerbank.
     *
     * @param id the id of the powerbankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the powerbankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/powerbanks/{id}")
    public ResponseEntity<PowerbankDTO> getPowerbank(@PathVariable Long id) {
        log.debug("REST request to get Powerbank : {}", id);
        Optional<PowerbankDTO> powerbankDTO = powerbankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(powerbankDTO);
    }

    /**
     * {@code DELETE  /powerbanks/:id} : delete the "id" powerbank.
     *
     * @param id the id of the powerbankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/powerbanks/{id}")
    public ResponseEntity<Void> deletePowerbank(@PathVariable Long id) {
        log.debug("REST request to delete Powerbank : {}", id);
        powerbankService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
