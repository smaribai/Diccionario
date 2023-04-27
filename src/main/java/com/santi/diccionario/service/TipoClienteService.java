package com.santi.diccionario.service;

import com.santi.diccionario.service.dto.TipoClienteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.diccionario.domain.TipoCliente}.
 */
public interface TipoClienteService {
    /**
     * Save a tipoCliente.
     *
     * @param tipoClienteDTO the entity to save.
     * @return the persisted entity.
     */
    TipoClienteDTO save(TipoClienteDTO tipoClienteDTO);

    /**
     * Updates a tipoCliente.
     *
     * @param tipoClienteDTO the entity to update.
     * @return the persisted entity.
     */
    TipoClienteDTO update(TipoClienteDTO tipoClienteDTO);

    /**
     * Partially updates a tipoCliente.
     *
     * @param tipoClienteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoClienteDTO> partialUpdate(TipoClienteDTO tipoClienteDTO);

    /**
     * Get all the tipoClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoClienteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tipoCliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoClienteDTO> findOne(Long id);

    /**
     * Delete the "id" tipoCliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
