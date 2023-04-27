package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.ClasificacionesRepository;
import com.santi.diccionario.service.ClasificacionesQueryService;
import com.santi.diccionario.service.ClasificacionesService;
import com.santi.diccionario.service.criteria.ClasificacionesCriteria;
import com.santi.diccionario.service.dto.ClasificacionesDTO;
import com.santi.diccionario.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
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
 * REST controller for managing {@link com.santi.diccionario.domain.Clasificaciones}.
 */
@RestController
@RequestMapping("/api")
public class ClasificacionesResource {

    private final Logger log = LoggerFactory.getLogger(ClasificacionesResource.class);

    private final ClasificacionesService clasificacionesService;

    private final ClasificacionesRepository clasificacionesRepository;

    private final ClasificacionesQueryService clasificacionesQueryService;

    public ClasificacionesResource(
        ClasificacionesService clasificacionesService,
        ClasificacionesRepository clasificacionesRepository,
        ClasificacionesQueryService clasificacionesQueryService
    ) {
        this.clasificacionesService = clasificacionesService;
        this.clasificacionesRepository = clasificacionesRepository;
        this.clasificacionesQueryService = clasificacionesQueryService;
    }

    /**
     * {@code GET  /clasificaciones} : get all the clasificaciones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clasificaciones in body.
     */
    @GetMapping("/clasificaciones")
    public ResponseEntity<List<ClasificacionesDTO>> getAllClasificaciones(
        ClasificacionesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Clasificaciones by criteria: {}", criteria);
        Page<ClasificacionesDTO> page = clasificacionesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clasificaciones/count} : count all the clasificaciones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/clasificaciones/count")
    public ResponseEntity<Long> countClasificaciones(ClasificacionesCriteria criteria) {
        log.debug("REST request to count Clasificaciones by criteria: {}", criteria);
        return ResponseEntity.ok().body(clasificacionesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /clasificaciones/:id} : get the "id" clasificaciones.
     *
     * @param id the id of the clasificacionesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clasificacionesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clasificaciones/{id}")
    public ResponseEntity<ClasificacionesDTO> getClasificaciones(@PathVariable Long id) {
        log.debug("REST request to get Clasificaciones : {}", id);
        Optional<ClasificacionesDTO> clasificacionesDTO = clasificacionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clasificacionesDTO);
    }
}
