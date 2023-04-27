package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.DestinatarioDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.Destinatario}.
 */
public interface DestinatarioService {
    /**
     * Save a destinatario.
     *
     * @param destinatarioDTO the entity to save.
     * @return the persisted entity.
     */
    DestinatarioDTO save(DestinatarioDTO destinatarioDTO);

    /**
     * Updates a destinatario.
     *
     * @param destinatarioDTO the entity to update.
     * @return the persisted entity.
     */
    DestinatarioDTO update(DestinatarioDTO destinatarioDTO);

    /**
     * Partially updates a destinatario.
     *
     * @param destinatarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DestinatarioDTO> partialUpdate(DestinatarioDTO destinatarioDTO);

    /**
     * Get all the destinatarios.
     *
     * @return the list of entities.
     */
    List<DestinatarioDTO> findAll();

    /**
     * Get the "id" destinatario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DestinatarioDTO> findOne(Long id);

    /**
     * Delete the "id" destinatario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
