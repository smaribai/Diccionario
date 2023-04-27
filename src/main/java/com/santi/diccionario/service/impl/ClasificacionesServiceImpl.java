package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.Clasificaciones;
import com.santi.diccionario.repository.ClasificacionesRepository;
import com.santi.diccionario.service.ClasificacionesService;
import com.santi.diccionario.service.dto.ClasificacionesDTO;
import com.santi.diccionario.service.mapper.ClasificacionesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Clasificaciones}.
 */
@Service
@Transactional
public class ClasificacionesServiceImpl implements ClasificacionesService {

    private final Logger log = LoggerFactory.getLogger(ClasificacionesServiceImpl.class);

    private final ClasificacionesRepository clasificacionesRepository;

    private final ClasificacionesMapper clasificacionesMapper;

    public ClasificacionesServiceImpl(ClasificacionesRepository clasificacionesRepository, ClasificacionesMapper clasificacionesMapper) {
        this.clasificacionesRepository = clasificacionesRepository;
        this.clasificacionesMapper = clasificacionesMapper;
    }

    @Override
    public ClasificacionesDTO save(ClasificacionesDTO clasificacionesDTO) {
        log.debug("Request to save Clasificaciones : {}", clasificacionesDTO);
        Clasificaciones clasificaciones = clasificacionesMapper.toEntity(clasificacionesDTO);
        clasificaciones = clasificacionesRepository.save(clasificaciones);
        return clasificacionesMapper.toDto(clasificaciones);
    }

    @Override
    public ClasificacionesDTO update(ClasificacionesDTO clasificacionesDTO) {
        log.debug("Request to save Clasificaciones : {}", clasificacionesDTO);
        Clasificaciones clasificaciones = clasificacionesMapper.toEntity(clasificacionesDTO);
        clasificaciones = clasificacionesRepository.save(clasificaciones);
        return clasificacionesMapper.toDto(clasificaciones);
    }

    @Override
    public Optional<ClasificacionesDTO> partialUpdate(ClasificacionesDTO clasificacionesDTO) {
        log.debug("Request to partially update Clasificaciones : {}", clasificacionesDTO);

        return clasificacionesRepository
            .findById(clasificacionesDTO.getId())
            .map(existingClasificaciones -> {
                clasificacionesMapper.partialUpdate(existingClasificaciones, clasificacionesDTO);

                return existingClasificaciones;
            })
            .map(clasificacionesRepository::save)
            .map(clasificacionesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClasificacionesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clasificaciones");
        return clasificacionesRepository.findAll(pageable).map(clasificacionesMapper::toDto);
    }

    public Page<ClasificacionesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return clasificacionesRepository.findAllWithEagerRelationships(pageable).map(clasificacionesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClasificacionesDTO> findOne(Long id) {
        log.debug("Request to get Clasificaciones : {}", id);
        return clasificacionesRepository.findOneWithEagerRelationships(id).map(clasificacionesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clasificaciones : {}", id);
        clasificacionesRepository.deleteById(id);
    }
}
