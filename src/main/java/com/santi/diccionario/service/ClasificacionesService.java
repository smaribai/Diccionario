package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.ClasificacionesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.Clasificaciones}.
 */
public interface ClasificacionesService {
    /**
     * Save a clasificaciones.
     *
     * @param clasificacionesDTO the entity to save.
     * @return the persisted entity.
     */
    ClasificacionesDTO save(ClasificacionesDTO clasificacionesDTO);

    /**
     * Updates a clasificaciones.
     *
     * @param clasificacionesDTO the entity to update.
     * @return the persisted entity.
     */
    ClasificacionesDTO update(ClasificacionesDTO clasificacionesDTO);

    /**
     * Partially updates a clasificaciones.
     *
     * @param clasificacionesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClasificacionesDTO> partialUpdate(ClasificacionesDTO clasificacionesDTO);

    /**
     * Get all the clasificaciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClasificacionesDTO> findAll(Pageable pageable);

    /**
     * Get all the clasificaciones with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClasificacionesDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" clasificaciones.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClasificacionesDTO> findOne(Long id);

    /**
     * Delete the "id" clasificaciones.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
