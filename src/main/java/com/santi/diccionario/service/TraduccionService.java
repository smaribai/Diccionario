package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.TraduccionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.Traduccion}.
 */
public interface TraduccionService {
    /**
     * Save a traduccion.
     *
     * @param traduccionDTO the entity to save.
     * @return the persisted entity.
     */
    TraduccionDTO save(TraduccionDTO traduccionDTO);

    /**
     * Updates a traduccion.
     *
     * @param traduccionDTO the entity to update.
     * @return the persisted entity.
     */
    TraduccionDTO update(TraduccionDTO traduccionDTO);

    /**
     * Partially updates a traduccion.
     *
     * @param traduccionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TraduccionDTO> partialUpdate(TraduccionDTO traduccionDTO);

    /**
     * Get all the traduccions.
     *
     * @return the list of entities.
     */
    List<TraduccionDTO> findAll();

    /**
     * Get all the traduccions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TraduccionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" traduccion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TraduccionDTO> findOne(Long id);

    /**
     * Delete the "id" traduccion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
