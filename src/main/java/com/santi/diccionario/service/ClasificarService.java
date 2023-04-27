package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.ClasificarDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.Clasificar}.
 */
public interface ClasificarService {
    /**
     * Save a clasificar.
     *
     * @param clasificarDTO the entity to save.
     * @return the persisted entity.
     */
    ClasificarDTO save(ClasificarDTO clasificarDTO);

    /**
     * Updates a clasificar.
     *
     * @param clasificarDTO the entity to update.
     * @return the persisted entity.
     */
    ClasificarDTO update(ClasificarDTO clasificarDTO);

    /**
     * Partially updates a clasificar.
     *
     * @param clasificarDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClasificarDTO> partialUpdate(ClasificarDTO clasificarDTO);

    /**
     * Get all the clasificars.
     *
     * @return the list of entities.
     */
    List<ClasificarDTO> findAll();

    /**
     * Get all the clasificars with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClasificarDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" clasificar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClasificarDTO> findOne(Long id);

    /**
     * Delete the "id" clasificar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
