package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.RemitenteRepository;
import com.santi.diccionario.service.RemitenteQueryService;
import com.santi.diccionario.service.RemitenteService;
import com.santi.diccionario.service.criteria.RemitenteCriteria;
import com.santi.diccionario.service.dto.RemitenteDTO;
import com.santi.diccionario.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.santi.diccionario.domain.Remitente}.
 */
@RestController
@RequestMapping("/api")
public class RemitenteResource {

    private final Logger log = LoggerFactory.getLogger(RemitenteResource.class);

    private static final String ENTITY_NAME = "remitente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemitenteService remitenteService;

    private final RemitenteRepository remitenteRepository;

    private final RemitenteQueryService remitenteQueryService;

    public RemitenteResource(
        RemitenteService remitenteService,
        RemitenteRepository remitenteRepository,
        RemitenteQueryService remitenteQueryService
    ) {
        this.remitenteService = remitenteService;
        this.remitenteRepository = remitenteRepository;
        this.remitenteQueryService = remitenteQueryService;
    }

    /**
     * {@code POST  /remitentes} : Create a new remitente.
     *
     * @param remitenteDTO the remitenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remitenteDTO, or with status {@code 400 (Bad Request)} if the remitente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remitentes")
    public ResponseEntity<RemitenteDTO> createRemitente(@Valid @RequestBody RemitenteDTO remitenteDTO) throws URISyntaxException {
        log.debug("REST request to save Remitente : {}", remitenteDTO);
        if (remitenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new remitente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RemitenteDTO result = remitenteService.save(remitenteDTO);
        return ResponseEntity
            .created(new URI("/api/remitentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remitentes/:id} : Updates an existing remitente.
     *
     * @param id the id of the remitenteDTO to save.
     * @param remitenteDTO the remitenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remitenteDTO,
     * or with status {@code 400 (Bad Request)} if the remitenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remitenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remitentes/{id}")
    public ResponseEntity<RemitenteDTO> updateRemitente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RemitenteDTO remitenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Remitente : {}, {}", id, remitenteDTO);
        if (remitenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remitenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remitenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RemitenteDTO result = remitenteService.update(remitenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remitenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /remitentes/:id} : Partial updates given fields of an existing remitente, field will ignore if it is null
     *
     * @param id the id of the remitenteDTO to save.
     * @param remitenteDTO the remitenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remitenteDTO,
     * or with status {@code 400 (Bad Request)} if the remitenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the remitenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the remitenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/remitentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RemitenteDTO> partialUpdateRemitente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RemitenteDTO remitenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Remitente partially : {}, {}", id, remitenteDTO);
        if (remitenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remitenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remitenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RemitenteDTO> result = remitenteService.partialUpdate(remitenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remitenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /remitentes} : get all the remitentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remitentes in body.
     */
    @GetMapping("/remitentes")
    public ResponseEntity<List<RemitenteDTO>> getAllRemitentes(RemitenteCriteria criteria) {
        log.debug("REST request to get Remitentes by criteria: {}", criteria);
        List<RemitenteDTO> entityList = remitenteQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /remitentes/count} : count all the remitentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/remitentes/count")
    public ResponseEntity<Long> countRemitentes(RemitenteCriteria criteria) {
        log.debug("REST request to count Remitentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(remitenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /remitentes/:id} : get the "id" remitente.
     *
     * @param id the id of the remitenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remitenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remitentes/{id}")
    public ResponseEntity<RemitenteDTO> getRemitente(@PathVariable Long id) {
        log.debug("REST request to get Remitente : {}", id);
        Optional<RemitenteDTO> remitenteDTO = remitenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remitenteDTO);
    }

    /**
     * {@code DELETE  /remitentes/:id} : delete the "id" remitente.
     *
     * @param id the id of the remitenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remitentes/{id}")
    public ResponseEntity<Void> deleteRemitente(@PathVariable Long id) {
        log.debug("REST request to delete Remitente : {}", id);
        remitenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
