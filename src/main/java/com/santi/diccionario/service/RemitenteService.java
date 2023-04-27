package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.RemitenteDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.Remitente}.
 */
public interface RemitenteService {
    /**
     * Save a remitente.
     *
     * @param remitenteDTO the entity to save.
     * @return the persisted entity.
     */
    RemitenteDTO save(RemitenteDTO remitenteDTO);

    /**
     * Updates a remitente.
     *
     * @param remitenteDTO the entity to update.
     * @return the persisted entity.
     */
    RemitenteDTO update(RemitenteDTO remitenteDTO);

    /**
     * Partially updates a remitente.
     *
     * @param remitenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RemitenteDTO> partialUpdate(RemitenteDTO remitenteDTO);

    /**
     * Get all the remitentes.
     *
     * @return the list of entities.
     */
    List<RemitenteDTO> findAll();

    /**
     * Get the "id" remitente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RemitenteDTO> findOne(Long id);

    /**
     * Delete the "id" remitente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
