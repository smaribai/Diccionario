package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.DivisaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.Divisa}.
 */
public interface DivisaService {
    /**
     * Save a divisa.
     *
     * @param divisaDTO the entity to save.
     * @return the persisted entity.
     */
    DivisaDTO save(DivisaDTO divisaDTO);

    /**
     * Updates a divisa.
     *
     * @param divisaDTO the entity to update.
     * @return the persisted entity.
     */
    DivisaDTO update(DivisaDTO divisaDTO);

    /**
     * Partially updates a divisa.
     *
     * @param divisaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DivisaDTO> partialUpdate(DivisaDTO divisaDTO);

    /**
     * Get all the divisas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DivisaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" divisa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DivisaDTO> findOne(Long id);

    /**
     * Delete the "id" divisa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
