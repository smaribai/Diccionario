package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.ControlRepository;
import com.santi.diccionario.service.ControlQueryService;
import com.santi.diccionario.service.ControlService;
import com.santi.diccionario.service.criteria.ControlCriteria;
import com.santi.diccionario.service.dto.ControlDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.santi.diccionario.domain.Control}.
 */
@RestController
@RequestMapping("/api")
public class ControlResource {

    private final Logger log = LoggerFactory.getLogger(ControlResource.class);

    private static final String ENTITY_NAME = "control";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControlService controlService;

    private final ControlRepository controlRepository;

    private final ControlQueryService controlQueryService;

    public ControlResource(ControlService controlService, ControlRepository controlRepository, ControlQueryService controlQueryService) {
        this.controlService = controlService;
        this.controlRepository = controlRepository;
        this.controlQueryService = controlQueryService;
    }

    /**
     * {@code POST  /controls} : Create a new control.
     *
     * @param controlDTO the controlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controlDTO, or with status {@code 400 (Bad Request)} if the control has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/controls")
    public ResponseEntity<ControlDTO> createControl(@Valid @RequestBody ControlDTO controlDTO) throws URISyntaxException {
        log.debug("REST request to save Control : {}", controlDTO);
        if (controlDTO.getId() != null) {
            throw new BadRequestAlertException("A new control cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControlDTO result = controlService.save(controlDTO);
        return ResponseEntity
            .created(new URI("/api/controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controls/:id} : Updates an existing control.
     *
     * @param id the id of the controlDTO to save.
     * @param controlDTO the controlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlDTO,
     * or with status {@code 400 (Bad Request)} if the controlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/controls/{id}")
    public ResponseEntity<ControlDTO> updateControl(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ControlDTO controlDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Control : {}, {}", id, controlDTO);
        if (controlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ControlDTO result = controlService.update(controlDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controlDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /controls/:id} : Partial updates given fields of an existing control, field will ignore if it is null
     *
     * @param id the id of the controlDTO to save.
     * @param controlDTO the controlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlDTO,
     * or with status {@code 400 (Bad Request)} if the controlDTO is not valid,
     * or with status {@code 404 (Not Found)} if the controlDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the controlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/controls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ControlDTO> partialUpdateControl(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ControlDTO controlDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Control partially : {}, {}", id, controlDTO);
        if (controlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControlDTO> result = controlService.partialUpdate(controlDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controlDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /controls} : get all the controls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controls in body.
     */
    @GetMapping("/controls")
    public ResponseEntity<List<ControlDTO>> getAllControls(
        ControlCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Controls by criteria: {}", criteria);
        Page<ControlDTO> page = controlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /controls/count} : count all the controls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/controls/count")
    public ResponseEntity<Long> countControls(ControlCriteria criteria) {
        log.debug("REST request to count Controls by criteria: {}", criteria);
        return ResponseEntity.ok().body(controlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /controls/:id} : get the "id" control.
     *
     * @param id the id of the controlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controlDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/controls/{id}")
    public ResponseEntity<ControlDTO> getControl(@PathVariable Long id) {
        log.debug("REST request to get Control : {}", id);
        Optional<ControlDTO> controlDTO = controlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controlDTO);
    }

    /**
     * {@code DELETE  /controls/:id} : delete the "id" control.
     *
     * @param id the id of the controlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/controls/{id}")
    public ResponseEntity<Void> deleteControl(@PathVariable Long id) {
        log.debug("REST request to delete Control : {}", id);
        controlService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
