package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.Clasificar;
import com.santi.diccionario.repository.ClasificarRepository;
import com.santi.diccionario.service.ClasificarService;
import com.santi.diccionario.service.dto.ClasificarDTO;
import com.santi.diccionario.service.mapper.ClasificarMapper;
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
 * Service Implementation for managing {@link Clasificar}.
 */
@Service
@Transactional
public class ClasificarServiceImpl implements ClasificarService {

    private final Logger log = LoggerFactory.getLogger(ClasificarServiceImpl.class);

    private final ClasificarRepository clasificarRepository;

    private final ClasificarMapper clasificarMapper;

    public ClasificarServiceImpl(ClasificarRepository clasificarRepository, ClasificarMapper clasificarMapper) {
        this.clasificarRepository = clasificarRepository;
        this.clasificarMapper = clasificarMapper;
    }

    @Override
    public ClasificarDTO save(ClasificarDTO clasificarDTO) {
        log.debug("Request to save Clasificar : {}", clasificarDTO);
        Clasificar clasificar = clasificarMapper.toEntity(clasificarDTO);
        clasificar = clasificarRepository.save(clasificar);
        return clasificarMapper.toDto(clasificar);
    }

    @Override
    public ClasificarDTO update(ClasificarDTO clasificarDTO) {
        log.debug("Request to save Clasificar : {}", clasificarDTO);
        Clasificar clasificar = clasificarMapper.toEntity(clasificarDTO);
        clasificar = clasificarRepository.save(clasificar);
        return clasificarMapper.toDto(clasificar);
    }

    @Override
    public Optional<ClasificarDTO> partialUpdate(ClasificarDTO clasificarDTO) {
        log.debug("Request to partially update Clasificar : {}", clasificarDTO);

        return clasificarRepository
            .findById(clasificarDTO.getId())
            .map(existingClasificar -> {
                clasificarMapper.partialUpdate(existingClasificar, clasificarDTO);

                return existingClasificar;
            })
            .map(clasificarRepository::save)
            .map(clasificarMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClasificarDTO> findAll() {
        log.debug("Request to get all Clasificars");
        return clasificarRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(clasificarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<ClasificarDTO> findAllWithEagerRelationships(Pageable pageable) {
        return clasificarRepository.findAllWithEagerRelationships(pageable).map(clasificarMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClasificarDTO> findOne(Long id) {
        log.debug("Request to get Clasificar : {}", id);
        return clasificarRepository.findOneWithEagerRelationships(id).map(clasificarMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clasificar : {}", id);
        clasificarRepository.deleteById(id);
    }
}
