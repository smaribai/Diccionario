package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.ClasificarRepository;
import com.santi.diccionario.service.ClasificarQueryService;
import com.santi.diccionario.service.ClasificarService;
import com.santi.diccionario.service.criteria.ClasificarCriteria;
import com.santi.diccionario.service.dto.ClasificarDTO;
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
 * REST controller for managing {@link com.santi.diccionario.domain.Clasificar}.
 */
@RestController
@RequestMapping("/api")
public class ClasificarResource {

    private final Logger log = LoggerFactory.getLogger(ClasificarResource.class);

    private static final String ENTITY_NAME = "clasificar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasificarService clasificarService;

    private final ClasificarRepository clasificarRepository;

    private final ClasificarQueryService clasificarQueryService;

    public ClasificarResource(
        ClasificarService clasificarService,
        ClasificarRepository clasificarRepository,
        ClasificarQueryService clasificarQueryService
    ) {
        this.clasificarService = clasificarService;
        this.clasificarRepository = clasificarRepository;
        this.clasificarQueryService = clasificarQueryService;
    }

    /**
     * {@code POST  /clasificars} : Create a new clasificar.
     *
     * @param clasificarDTO the clasificarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clasificarDTO, or with status {@code 400 (Bad Request)} if the clasificar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clasificars")
    public ResponseEntity<ClasificarDTO> createClasificar(@Valid @RequestBody ClasificarDTO clasificarDTO) throws URISyntaxException {
        log.debug("REST request to save Clasificar : {}", clasificarDTO);
        if (clasificarDTO.getId() != null) {
            throw new BadRequestAlertException("A new clasificar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasificarDTO result = clasificarService.save(clasificarDTO);
        return ResponseEntity
            .created(new URI("/api/clasificars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clasificars/:id} : Updates an existing clasificar.
     *
     * @param id the id of the clasificarDTO to save.
     * @param clasificarDTO the clasificarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clasificarDTO,
     * or with status {@code 400 (Bad Request)} if the clasificarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clasificarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clasificars/{id}")
    public ResponseEntity<ClasificarDTO> updateClasificar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClasificarDTO clasificarDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Clasificar : {}, {}", id, clasificarDTO);
        if (clasificarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clasificarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clasificarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClasificarDTO result = clasificarService.update(clasificarDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clasificarDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clasificars/:id} : Partial updates given fields of an existing clasificar, field will ignore if it is null
     *
     * @param id the id of the clasificarDTO to save.
     * @param clasificarDTO the clasificarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clasificarDTO,
     * or with status {@code 400 (Bad Request)} if the clasificarDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clasificarDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clasificarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clasificars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClasificarDTO> partialUpdateClasificar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClasificarDTO clasificarDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Clasificar partially : {}, {}", id, clasificarDTO);
        if (clasificarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clasificarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clasificarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClasificarDTO> result = clasificarService.partialUpdate(clasificarDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clasificarDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /clasificars} : get all the clasificars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clasificars in body.
     */
    @GetMapping("/clasificars")
    public ResponseEntity<List<ClasificarDTO>> getAllClasificars(ClasificarCriteria criteria) {
        log.debug("REST request to get Clasificars by criteria: {}", criteria);
        List<ClasificarDTO> entityList = clasificarQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /clasificars/count} : count all the clasificars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/clasificars/count")
    public ResponseEntity<Long> countClasificars(ClasificarCriteria criteria) {
        log.debug("REST request to count Clasificars by criteria: {}", criteria);
        return ResponseEntity.ok().body(clasificarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /clasificars/:id} : get the "id" clasificar.
     *
     * @param id the id of the clasificarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clasificarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clasificars/{id}")
    public ResponseEntity<ClasificarDTO> getClasificar(@PathVariable Long id) {
        log.debug("REST request to get Clasificar : {}", id);
        Optional<ClasificarDTO> clasificarDTO = clasificarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clasificarDTO);
    }

    /**
     * {@code DELETE  /clasificars/:id} : delete the "id" clasificar.
     *
     * @param id the id of the clasificarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clasificars/{id}")
    public ResponseEntity<Void> deleteClasificar(@PathVariable Long id) {
        log.debug("REST request to delete Clasificar : {}", id);
        clasificarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
