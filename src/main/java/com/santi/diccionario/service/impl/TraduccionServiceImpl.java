package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.Traduccion;
import com.santi.diccionario.repository.TraduccionRepository;
import com.santi.diccionario.service.TraduccionService;
import com.santi.diccionario.service.dto.TraduccionDTO;
import com.santi.diccionario.service.mapper.TraduccionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Traduccion}.
 */
@Service
@Transactional
public class TraduccionServiceImpl implements TraduccionService {

    private final Logger log = LoggerFactory.getLogger(TraduccionServiceImpl.class);

    private final TraduccionRepository traduccionRepository;

    private final TraduccionMapper traduccionMapper;

    public TraduccionServiceImpl(TraduccionRepository traduccionRepository, TraduccionMapper traduccionMapper) {
        this.traduccionRepository = traduccionRepository;
        this.traduccionMapper = traduccionMapper;
    }

    @Override
    public TraduccionDTO save(TraduccionDTO traduccionDTO) {
        log.debug("Request to save Traduccion : {}", traduccionDTO);
        Traduccion traduccion = traduccionMapper.toEntity(traduccionDTO);
        traduccion = traduccionRepository.save(traduccion);
        return traduccionMapper.toDto(traduccion);
    }

    @Override
    public TraduccionDTO update(TraduccionDTO traduccionDTO) {
        log.debug("Request to save Traduccion : {}", traduccionDTO);
        Traduccion traduccion = traduccionMapper.toEntity(traduccionDTO);
        traduccion = traduccionRepository.save(traduccion);
        return traduccionMapper.toDto(traduccion);
    }

    @Override
    public Optional<TraduccionDTO> partialUpdate(TraduccionDTO traduccionDTO) {
        log.debug("Request to partially update Traduccion : {}", traduccionDTO);

        return traduccionRepository
            .findById(traduccionDTO.getId())
            .map(existingTraduccion -> {
                traduccionMapper.partialUpdate(existingTraduccion, traduccionDTO);

                return existingTraduccion;
            })
            .map(traduccionRepository::save)
            .map(traduccionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraduccionDTO> findAll() {
        log.debug("Request to get all Traduccions");
        return traduccionRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(traduccionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<TraduccionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return traduccionRepository.findAllWithEagerRelationships(pageable).map(traduccionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TraduccionDTO> findOne(Long id) {
        log.debug("Request to get Traduccion : {}", id);
        return traduccionRepository.findOneWithEagerRelationships(id).map(traduccionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Traduccion : {}", id);
        traduccionRepository.deleteById(id);
    }
}
