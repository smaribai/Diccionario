package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.ProductoControlesRepository;
import com.santi.diccionario.service.ProductoControlesQueryService;
import com.santi.diccionario.service.ProductoControlesService;
import com.santi.diccionario.service.criteria.ProductoControlesCriteria;
import com.santi.diccionario.service.dto.ProductoControlesDTO;
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
 * REST controller for managing {@link com.santi.diccionario.domain.ProductoControles}.
 */
@RestController
@RequestMapping("/api")
public class ProductoControlesResource {

    private final Logger log = LoggerFactory.getLogger(ProductoControlesResource.class);

    private static final String ENTITY_NAME = "productoControles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoControlesService productoControlesService;

    private final ProductoControlesRepository productoControlesRepository;

    private final ProductoControlesQueryService productoControlesQueryService;

    public ProductoControlesResource(
        ProductoControlesService productoControlesService,
        ProductoControlesRepository productoControlesRepository,
        ProductoControlesQueryService productoControlesQueryService
    ) {
        this.productoControlesService = productoControlesService;
        this.productoControlesRepository = productoControlesRepository;
        this.productoControlesQueryService = productoControlesQueryService;
    }

    /**
     * {@code POST  /producto-controles} : Create a new productoControles.
     *
     * @param productoControlesDTO the productoControlesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoControlesDTO, or with status {@code 400 (Bad Request)} if the productoControles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-controles")
    public ResponseEntity<ProductoControlesDTO> createProductoControles(@Valid @RequestBody ProductoControlesDTO productoControlesDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductoControles : {}", productoControlesDTO);
        if (productoControlesDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoControles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoControlesDTO result = productoControlesService.save(productoControlesDTO);
        return ResponseEntity
            .created(new URI("/api/producto-controles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-controles/:id} : Updates an existing productoControles.
     *
     * @param id the id of the productoControlesDTO to save.
     * @param productoControlesDTO the productoControlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoControlesDTO,
     * or with status {@code 400 (Bad Request)} if the productoControlesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoControlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-controles/{id}")
    public ResponseEntity<ProductoControlesDTO> updateProductoControles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductoControlesDTO productoControlesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductoControles : {}, {}", id, productoControlesDTO);
        if (productoControlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoControlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoControlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoControlesDTO result = productoControlesService.update(productoControlesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoControlesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producto-controles/:id} : Partial updates given fields of an existing productoControles, field will ignore if it is null
     *
     * @param id the id of the productoControlesDTO to save.
     * @param productoControlesDTO the productoControlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoControlesDTO,
     * or with status {@code 400 (Bad Request)} if the productoControlesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productoControlesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoControlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producto-controles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoControlesDTO> partialUpdateProductoControles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductoControlesDTO productoControlesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductoControles partially : {}, {}", id, productoControlesDTO);
        if (productoControlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoControlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoControlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoControlesDTO> result = productoControlesService.partialUpdate(productoControlesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoControlesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-controles} : get all the productoControles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoControles in body.
     */
    @GetMapping("/producto-controles")
    public ResponseEntity<List<ProductoControlesDTO>> getAllProductoControles(
        ProductoControlesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProductoControles by criteria: {}", criteria);
        Page<ProductoControlesDTO> page = productoControlesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-controles/count} : count all the productoControles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/producto-controles/count")
    public ResponseEntity<Long> countProductoControles(ProductoControlesCriteria criteria) {
        log.debug("REST request to count ProductoControles by criteria: {}", criteria);
        return ResponseEntity.ok().body(productoControlesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /producto-controles/:id} : get the "id" productoControles.
     *
     * @param id the id of the productoControlesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoControlesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-controles/{id}")
    public ResponseEntity<ProductoControlesDTO> getProductoControles(@PathVariable Long id) {
        log.debug("REST request to get ProductoControles : {}", id);
        Optional<ProductoControlesDTO> productoControlesDTO = productoControlesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoControlesDTO);
    }

    /**
     * {@code DELETE  /producto-controles/:id} : delete the "id" productoControles.
     *
     * @param id the id of the productoControlesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-controles/{id}")
    public ResponseEntity<Void> deleteProductoControles(@PathVariable Long id) {
        log.debug("REST request to delete ProductoControles : {}", id);
        productoControlesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
