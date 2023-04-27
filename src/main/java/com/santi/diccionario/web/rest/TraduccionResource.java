package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.TraduccionRepository;
import com.santi.diccionario.service.TraduccionQueryService;
import com.santi.diccionario.service.TraduccionService;
import com.santi.diccionario.service.criteria.TraduccionCriteria;
import com.santi.diccionario.service.dto.TraduccionDTO;
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
 * REST controller for managing {@link com.santi.diccionario.domain.Traduccion}.
 */
@RestController
@RequestMapping("/api")
public class TraduccionResource {

    private final Logger log = LoggerFactory.getLogger(TraduccionResource.class);

    private static final String ENTITY_NAME = "traduccion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TraduccionService traduccionService;

    private final TraduccionRepository traduccionRepository;

    private final TraduccionQueryService traduccionQueryService;

    public TraduccionResource(
        TraduccionService traduccionService,
        TraduccionRepository traduccionRepository,
        TraduccionQueryService traduccionQueryService
    ) {
        this.traduccionService = traduccionService;
        this.traduccionRepository = traduccionRepository;
        this.traduccionQueryService = traduccionQueryService;
    }

    /**
     * {@code POST  /traduccions} : Create a new traduccion.
     *
     * @param traduccionDTO the traduccionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new traduccionDTO, or with status {@code 400 (Bad Request)} if the traduccion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/traduccions")
    public ResponseEntity<TraduccionDTO> createTraduccion(@Valid @RequestBody TraduccionDTO traduccionDTO) throws URISyntaxException {
        log.debug("REST request to save Traduccion : {}", traduccionDTO);
        if (traduccionDTO.getId() != null) {
            throw new BadRequestAlertException("A new traduccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TraduccionDTO result = traduccionService.save(traduccionDTO);
        return ResponseEntity
            .created(new URI("/api/traduccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /traduccions/:id} : Updates an existing traduccion.
     *
     * @param id the id of the traduccionDTO to save.
     * @param traduccionDTO the traduccionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traduccionDTO,
     * or with status {@code 400 (Bad Request)} if the traduccionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the traduccionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/traduccions/{id}")
    public ResponseEntity<TraduccionDTO> updateTraduccion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TraduccionDTO traduccionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Traduccion : {}, {}", id, traduccionDTO);
        if (traduccionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traduccionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traduccionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TraduccionDTO result = traduccionService.update(traduccionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, traduccionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /traduccions/:id} : Partial updates given fields of an existing traduccion, field will ignore if it is null
     *
     * @param id the id of the traduccionDTO to save.
     * @param traduccionDTO the traduccionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traduccionDTO,
     * or with status {@code 400 (Bad Request)} if the traduccionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the traduccionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the traduccionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/traduccions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TraduccionDTO> partialUpdateTraduccion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TraduccionDTO traduccionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Traduccion partially : {}, {}", id, traduccionDTO);
        if (traduccionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traduccionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traduccionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TraduccionDTO> result = traduccionService.partialUpdate(traduccionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, traduccionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /traduccions} : get all the traduccions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of traduccions in body.
     */
    @GetMapping("/traduccions")
    public ResponseEntity<List<TraduccionDTO>> getAllTraduccions(TraduccionCriteria criteria) {
        log.debug("REST request to get Traduccions by criteria: {}", criteria);
        List<TraduccionDTO> entityList = traduccionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /traduccions/count} : count all the traduccions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/traduccions/count")
    public ResponseEntity<Long> countTraduccions(TraduccionCriteria criteria) {
        log.debug("REST request to count Traduccions by criteria: {}", criteria);
        return ResponseEntity.ok().body(traduccionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /traduccions/:id} : get the "id" traduccion.
     *
     * @param id the id of the traduccionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the traduccionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/traduccions/{id}")
    public ResponseEntity<TraduccionDTO> getTraduccion(@PathVariable Long id) {
        log.debug("REST request to get Traduccion : {}", id);
        Optional<TraduccionDTO> traduccionDTO = traduccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(traduccionDTO);
    }

    /**
     * {@code DELETE  /traduccions/:id} : delete the "id" traduccion.
     *
     * @param id the id of the traduccionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/traduccions/{id}")
    public ResponseEntity<Void> deleteTraduccion(@PathVariable Long id) {
        log.debug("REST request to delete Traduccion : {}", id);
        traduccionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
