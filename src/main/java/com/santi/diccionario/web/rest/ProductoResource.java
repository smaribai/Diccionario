package com.santi.diccionario.web.rest;

import com.santi.diccionario.repository.ProductoRepository;
import com.santi.diccionario.service.ProductoQueryService;
import com.santi.diccionario.service.ProductoService;
import com.santi.diccionario.service.criteria.ProductoCriteria;
import com.santi.diccionario.service.dto.ProductoDTO;
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
 * REST controller for managing {@link com.santi.diccionario.domain.Producto}.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    private static final String ENTITY_NAME = "producto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoService productoService;

    private final ProductoRepository productoRepository;

    private final ProductoQueryService productoQueryService;

    public ProductoResource(
        ProductoService productoService,
        ProductoRepository productoRepository,
        ProductoQueryService productoQueryService
    ) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
        this.productoQueryService = productoQueryService;
    }

    /**
     * {@code POST  /productos} : Create a new producto.
     *
     * @param productoDTO the productoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoDTO, or with status {@code 400 (Bad Request)} if the producto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody ProductoDTO productoDTO) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", productoDTO);
        if (productoDTO.getId() != null) {
            throw new BadRequestAlertException("A new producto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoDTO result = productoService.save(productoDTO);
        return ResponseEntity
            .created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /productos/:id} : Updates an existing producto.
     *
     * @param id the id of the productoDTO to save.
     * @param productoDTO the productoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoDTO,
     * or with status {@code 400 (Bad Request)} if the productoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductoDTO productoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Producto : {}, {}", id, productoDTO);
        if (productoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoDTO result = productoService.update(productoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /productos/:id} : Partial updates given fields of an existing producto, field will ignore if it is null
     *
     * @param id the id of the productoDTO to save.
     * @param productoDTO the productoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoDTO,
     * or with status {@code 400 (Bad Request)} if the productoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/productos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoDTO> partialUpdateProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductoDTO productoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Producto partially : {}, {}", id, productoDTO);
        if (productoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoDTO> result = productoService.partialUpdate(productoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /productos} : get all the productos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productos in body.
     */
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos(
        ProductoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Productos by criteria: {}", criteria);
        Page<ProductoDTO> page = productoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /productos/count} : count all the productos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/productos/count")
    public ResponseEntity<Long> countProductos(ProductoCriteria criteria) {
        log.debug("REST request to count Productos by criteria: {}", criteria);
        return ResponseEntity.ok().body(productoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /productos/:id} : get the "id" producto.
     *
     * @param id the id of the productoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Optional<ProductoDTO> productoDTO = productoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoDTO);
    }

    /**
     * {@code DELETE  /productos/:id} : delete the "id" producto.
     *
     * @param id the id of the productoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
