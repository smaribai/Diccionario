package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.ProvinciaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.Provincia}.
 */
public interface ProvinciaService {
    /**
     * Save a provincia.
     *
     * @param provinciaDTO the entity to save.
     * @return the persisted entity.
     */
    ProvinciaDTO save(ProvinciaDTO provinciaDTO);

    /**
     * Updates a provincia.
     *
     * @param provinciaDTO the entity to update.
     * @return the persisted entity.
     */
    ProvinciaDTO update(ProvinciaDTO provinciaDTO);

    /**
     * Partially updates a provincia.
     *
     * @param provinciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProvinciaDTO> partialUpdate(ProvinciaDTO provinciaDTO);

    /**
     * Get all the provincias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProvinciaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" provincia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProvinciaDTO> findOne(Long id);

    /**
     * Delete the "id" provincia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
