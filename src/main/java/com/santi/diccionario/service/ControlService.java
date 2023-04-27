package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.ControlDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.Control}.
 */
public interface ControlService {
    /**
     * Save a control.
     *
     * @param controlDTO the entity to save.
     * @return the persisted entity.
     */
    ControlDTO save(ControlDTO controlDTO);

    /**
     * Updates a control.
     *
     * @param controlDTO the entity to update.
     * @return the persisted entity.
     */
    ControlDTO update(ControlDTO controlDTO);

    /**
     * Partially updates a control.
     *
     * @param controlDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ControlDTO> partialUpdate(ControlDTO controlDTO);

    /**
     * Get all the controls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ControlDTO> findAll(Pageable pageable);

    /**
     * Get the "id" control.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ControlDTO> findOne(Long id);

    /**
     * Delete the "id" control.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
