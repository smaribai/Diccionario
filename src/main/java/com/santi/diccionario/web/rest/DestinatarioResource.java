package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.DestinatarioRepository;
import com.santi.diccionario.service.DestinatarioQueryService;
import com.santi.diccionario.service.DestinatarioService;
import com.santi.diccionario.service.criteria.DestinatarioCriteria;
import com.santi.diccionario.service.dto.DestinatarioDTO;
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
 * REST controller for managing {@link com.santi.diccionario.domain.Destinatario}.
 */
@RestController
@RequestMapping("/api")
public class DestinatarioResource {

    private final Logger log = LoggerFactory.getLogger(DestinatarioResource.class);

    private static final String ENTITY_NAME = "destinatario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DestinatarioService destinatarioService;

    private final DestinatarioRepository destinatarioRepository;

    private final DestinatarioQueryService destinatarioQueryService;

    public DestinatarioResource(
        DestinatarioService destinatarioService,
        DestinatarioRepository destinatarioRepository,
        DestinatarioQueryService destinatarioQueryService
    ) {
        this.destinatarioService = destinatarioService;
        this.destinatarioRepository = destinatarioRepository;
        this.destinatarioQueryService = destinatarioQueryService;
    }

    /**
     * {@code POST  /destinatarios} : Create a new destinatario.
     *
     * @param destinatarioDTO the destinatarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new destinatarioDTO, or with status {@code 400 (Bad Request)} if the destinatario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/destinatarios")
    public ResponseEntity<DestinatarioDTO> createDestinatario(@Valid @RequestBody DestinatarioDTO destinatarioDTO)
        throws URISyntaxException {
        log.debug("REST request to save Destinatario : {}", destinatarioDTO);
        if (destinatarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new destinatario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DestinatarioDTO result = destinatarioService.save(destinatarioDTO);
        return ResponseEntity
            .created(new URI("/api/destinatarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /destinatarios/:id} : Updates an existing destinatario.
     *
     * @param id the id of the destinatarioDTO to save.
     * @param destinatarioDTO the destinatarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated destinatarioDTO,
     * or with status {@code 400 (Bad Request)} if the destinatarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the destinatarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/destinatarios/{id}")
    public ResponseEntity<DestinatarioDTO> updateDestinatario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DestinatarioDTO destinatarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Destinatario : {}, {}", id, destinatarioDTO);
        if (destinatarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, destinatarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!destinatarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DestinatarioDTO result = destinatarioService.update(destinatarioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, destinatarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /destinatarios/:id} : Partial updates given fields of an existing destinatario, field will ignore if it is null
     *
     * @param id the id of the destinatarioDTO to save.
     * @param destinatarioDTO the destinatarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated destinatarioDTO,
     * or with status {@code 400 (Bad Request)} if the destinatarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the destinatarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the destinatarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/destinatarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DestinatarioDTO> partialUpdateDestinatario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DestinatarioDTO destinatarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Destinatario partially : {}, {}", id, destinatarioDTO);
        if (destinatarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, destinatarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!destinatarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DestinatarioDTO> result = destinatarioService.partialUpdate(destinatarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, destinatarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /destinatarios} : get all the destinatarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of destinatarios in body.
     */
    @GetMapping("/destinatarios")
    public ResponseEntity<List<DestinatarioDTO>> getAllDestinatarios(DestinatarioCriteria criteria) {
        log.debug("REST request to get Destinatarios by criteria: {}", criteria);
        List<DestinatarioDTO> entityList = destinatarioQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /destinatarios/count} : count all the destinatarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/destinatarios/count")
    public ResponseEntity<Long> countDestinatarios(DestinatarioCriteria criteria) {
        log.debug("REST request to count Destinatarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(destinatarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /destinatarios/:id} : get the "id" destinatario.
     *
     * @param id the id of the destinatarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the destinatarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/destinatarios/{id}")
    public ResponseEntity<DestinatarioDTO> getDestinatario(@PathVariable Long id) {
        log.debug("REST request to get Destinatario : {}", id);
        Optional<DestinatarioDTO> destinatarioDTO = destinatarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(destinatarioDTO);
    }

    /**
     * {@code DELETE  /destinatarios/:id} : delete the "id" destinatario.
     *
     * @param id the id of the destinatarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/destinatarios/{id}")
    public ResponseEntity<Void> deleteDestinatario(@PathVariable Long id) {
        log.debug("REST request to delete Destinatario : {}", id);
        destinatarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
