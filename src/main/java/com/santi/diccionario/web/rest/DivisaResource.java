package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.DivisaRepository;
import com.santi.diccionario.service.DivisaQueryService;
import com.santi.diccionario.service.DivisaService;
import com.santi.diccionario.service.criteria.DivisaCriteria;
import com.santi.diccionario.service.dto.DivisaDTO;
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
 * REST controller for managing {@link com.santi.diccionario.domain.Divisa}.
 */
@RestController
@RequestMapping("/api")
public class DivisaResource {

    private final Logger log = LoggerFactory.getLogger(DivisaResource.class);

    private static final String ENTITY_NAME = "divisa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DivisaService divisaService;

    private final DivisaRepository divisaRepository;

    private final DivisaQueryService divisaQueryService;

    public DivisaResource(DivisaService divisaService, DivisaRepository divisaRepository, DivisaQueryService divisaQueryService) {
        this.divisaService = divisaService;
        this.divisaRepository = divisaRepository;
        this.divisaQueryService = divisaQueryService;
    }

    /**
     * {@code POST  /divisas} : Create a new divisa.
     *
     * @param divisaDTO the divisaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new divisaDTO, or with status {@code 400 (Bad Request)} if the divisa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/divisas")
    public ResponseEntity<DivisaDTO> createDivisa(@Valid @RequestBody DivisaDTO divisaDTO) throws URISyntaxException {
        log.debug("REST request to save Divisa : {}", divisaDTO);
        if (divisaDTO.getId() != null) {
            throw new BadRequestAlertException("A new divisa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DivisaDTO result = divisaService.save(divisaDTO);
        return ResponseEntity
            .created(new URI("/api/divisas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /divisas/:id} : Updates an existing divisa.
     *
     * @param id the id of the divisaDTO to save.
     * @param divisaDTO the divisaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisaDTO,
     * or with status {@code 400 (Bad Request)} if the divisaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the divisaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/divisas/{id}")
    public ResponseEntity<DivisaDTO> updateDivisa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DivisaDTO divisaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Divisa : {}, {}", id, divisaDTO);
        if (divisaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, divisaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!divisaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DivisaDTO result = divisaService.update(divisaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, divisaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /divisas/:id} : Partial updates given fields of an existing divisa, field will ignore if it is null
     *
     * @param id the id of the divisaDTO to save.
     * @param divisaDTO the divisaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisaDTO,
     * or with status {@code 400 (Bad Request)} if the divisaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the divisaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the divisaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/divisas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DivisaDTO> partialUpdateDivisa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DivisaDTO divisaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Divisa partially : {}, {}", id, divisaDTO);
        if (divisaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, divisaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!divisaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DivisaDTO> result = divisaService.partialUpdate(divisaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, divisaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /divisas} : get all the divisas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of divisas in body.
     */
    @GetMapping("/divisas")
    public ResponseEntity<List<DivisaDTO>> getAllDivisas(
        DivisaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Divisas by criteria: {}", criteria);
        Page<DivisaDTO> page = divisaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /divisas/count} : count all the divisas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/divisas/count")
    public ResponseEntity<Long> countDivisas(DivisaCriteria criteria) {
        log.debug("REST request to count Divisas by criteria: {}", criteria);
        return ResponseEntity.ok().body(divisaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /divisas/:id} : get the "id" divisa.
     *
     * @param id the id of the divisaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the divisaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/divisas/{id}")
    public ResponseEntity<DivisaDTO> getDivisa(@PathVariable Long id) {
        log.debug("REST request to get Divisa : {}", id);
        Optional<DivisaDTO> divisaDTO = divisaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(divisaDTO);
    }

    /**
     * {@code DELETE  /divisas/:id} : delete the "id" divisa.
     *
     * @param id the id of the divisaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/divisas/{id}")
    public ResponseEntity<Void> deleteDivisa(@PathVariable Long id) {
        log.debug("REST request to delete Divisa : {}", id);
        divisaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
