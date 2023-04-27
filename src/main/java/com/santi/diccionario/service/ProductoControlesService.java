package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.ProductoControlesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.ProductoControles}.
 */
public interface ProductoControlesService {
    /**
     * Save a productoControles.
     *
     * @param productoControlesDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoControlesDTO save(ProductoControlesDTO productoControlesDTO);

    /**
     * Updates a productoControles.
     *
     * @param productoControlesDTO the entity to update.
     * @return the persisted entity.
     */
    ProductoControlesDTO update(ProductoControlesDTO productoControlesDTO);

    /**
     * Partially updates a productoControles.
     *
     * @param productoControlesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoControlesDTO> partialUpdate(ProductoControlesDTO productoControlesDTO);

    /**
     * Get all the productoControles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoControlesDTO> findAll(Pageable pageable);

    /**
     * Get all the productoControles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoControlesDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" productoControles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoControlesDTO> findOne(Long id);

    /**
     * Delete the "id" productoControles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
