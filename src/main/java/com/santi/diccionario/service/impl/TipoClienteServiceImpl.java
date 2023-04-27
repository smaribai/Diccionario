package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.TipoCliente;
import com.santi.diccionario.repository.TipoClienteRepository;
import com.santi.diccionario.service.TipoClienteService;
import com.santi.diccionario.service.dto.TipoClienteDTO;
import com.santi.diccionario.service.mapper.TipoClienteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoCliente}.
 */
@Service
@Transactional
public class TipoClienteServiceImpl implements TipoClienteService {

    private final Logger log = LoggerFactory.getLogger(TipoClienteServiceImpl.class);

    private final TipoClienteRepository tipoClienteRepository;

    private final TipoClienteMapper tipoClienteMapper;

    public TipoClienteServiceImpl(TipoClienteRepository tipoClienteRepository, TipoClienteMapper tipoClienteMapper) {
        this.tipoClienteRepository = tipoClienteRepository;
        this.tipoClienteMapper = tipoClienteMapper;
    }

    @Override
    public TipoClienteDTO save(TipoClienteDTO tipoClienteDTO) {
        log.debug("Request to save TipoCliente : {}", tipoClienteDTO);
        TipoCliente tipoCliente = tipoClienteMapper.toEntity(tipoClienteDTO);
        tipoCliente = tipoClienteRepository.save(tipoCliente);
        return tipoClienteMapper.toDto(tipoCliente);
    }

    @Override
    public TipoClienteDTO update(TipoClienteDTO tipoClienteDTO) {
        log.debug("Request to save TipoCliente : {}", tipoClienteDTO);
        TipoCliente tipoCliente = tipoClienteMapper.toEntity(tipoClienteDTO);
        tipoCliente = tipoClienteRepository.save(tipoCliente);
        return tipoClienteMapper.toDto(tipoCliente);
    }

    @Override
    public Optional<TipoClienteDTO> partialUpdate(TipoClienteDTO tipoClienteDTO) {
        log.debug("Request to partially update TipoCliente : {}", tipoClienteDTO);

        return tipoClienteRepository
            .findById(tipoClienteDTO.getId())
            .map(existingTipoCliente -> {
                tipoClienteMapper.partialUpdate(existingTipoCliente, tipoClienteDTO);

                return existingTipoCliente;
            })
            .map(tipoClienteRepository::save)
            .map(tipoClienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoClientes");
        return tipoClienteRepository.findAll(pageable).map(tipoClienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoClienteDTO> findOne(Long id) {
        log.debug("Request to get TipoCliente : {}", id);
        return tipoClienteRepository.findById(id).map(tipoClienteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoCliente : {}", id);
        tipoClienteRepository.deleteById(id);
    }
}
