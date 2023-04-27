package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.Remitente;
import com.santi.diccionario.repository.RemitenteRepository;
import com.santi.diccionario.service.RemitenteService;
import com.santi.diccionario.service.dto.RemitenteDTO;
import com.santi.diccionario.service.mapper.RemitenteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Remitente}.
 */
@Service
@Transactional
public class RemitenteServiceImpl implements RemitenteService {

    private final Logger log = LoggerFactory.getLogger(RemitenteServiceImpl.class);

    private final RemitenteRepository remitenteRepository;

    private final RemitenteMapper remitenteMapper;

    public RemitenteServiceImpl(RemitenteRepository remitenteRepository, RemitenteMapper remitenteMapper) {
        this.remitenteRepository = remitenteRepository;
        this.remitenteMapper = remitenteMapper;
    }

    @Override
    public RemitenteDTO save(RemitenteDTO remitenteDTO) {
        log.debug("Request to save Remitente : {}", remitenteDTO);
        Remitente remitente = remitenteMapper.toEntity(remitenteDTO);
        remitente = remitenteRepository.save(remitente);
        return remitenteMapper.toDto(remitente);
    }

    @Override
    public RemitenteDTO update(RemitenteDTO remitenteDTO) {
        log.debug("Request to save Remitente : {}", remitenteDTO);
        Remitente remitente = remitenteMapper.toEntity(remitenteDTO);
        remitente = remitenteRepository.save(remitente);
        return remitenteMapper.toDto(remitente);
    }

    @Override
    public Optional<RemitenteDTO> partialUpdate(RemitenteDTO remitenteDTO) {
        log.debug("Request to partially update Remitente : {}", remitenteDTO);

        return remitenteRepository
            .findById(remitenteDTO.getId())
            .map(existingRemitente -> {
                remitenteMapper.partialUpdate(existingRemitente, remitenteDTO);

                return existingRemitente;
            })
            .map(remitenteRepository::save)
            .map(remitenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RemitenteDTO> findAll() {
        log.debug("Request to get all Remitentes");
        return remitenteRepository.findAll().stream().map(remitenteMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RemitenteDTO> findOne(Long id) {
        log.debug("Request to get Remitente : {}", id);
        return remitenteRepository.findById(id).map(remitenteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Remitente : {}", id);
        remitenteRepository.deleteById(id);
    }
}
