package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.Provincia;
import com.santi.diccionario.repository.ProvinciaRepository;
import com.santi.diccionario.service.ProvinciaService;
import com.santi.diccionario.service.dto.ProvinciaDTO;
import com.santi.diccionario.service.mapper.ProvinciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Provincia}.
 */
@Service
@Transactional
public class ProvinciaServiceImpl implements ProvinciaService {

    private final Logger log = LoggerFactory.getLogger(ProvinciaServiceImpl.class);

    private final ProvinciaRepository provinciaRepository;

    private final ProvinciaMapper provinciaMapper;

    public ProvinciaServiceImpl(ProvinciaRepository provinciaRepository, ProvinciaMapper provinciaMapper) {
        this.provinciaRepository = provinciaRepository;
        this.provinciaMapper = provinciaMapper;
    }

    @Override
    public ProvinciaDTO save(ProvinciaDTO provinciaDTO) {
        log.debug("Request to save Provincia : {}", provinciaDTO);
        Provincia provincia = provinciaMapper.toEntity(provinciaDTO);
        provincia = provinciaRepository.save(provincia);
        return provinciaMapper.toDto(provincia);
    }

    @Override
    public ProvinciaDTO update(ProvinciaDTO provinciaDTO) {
        log.debug("Request to save Provincia : {}", provinciaDTO);
        Provincia provincia = provinciaMapper.toEntity(provinciaDTO);
        provincia = provinciaRepository.save(provincia);
        return provinciaMapper.toDto(provincia);
    }

    @Override
    public Optional<ProvinciaDTO> partialUpdate(ProvinciaDTO provinciaDTO) {
        log.debug("Request to partially update Provincia : {}", provinciaDTO);

        return provinciaRepository
            .findById(provinciaDTO.getId())
            .map(existingProvincia -> {
                provinciaMapper.partialUpdate(existingProvincia, provinciaDTO);

                return existingProvincia;
            })
            .map(provinciaRepository::save)
            .map(provinciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProvinciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Provincias");
        return provinciaRepository.findAll(pageable).map(provinciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProvinciaDTO> findOne(Long id) {
        log.debug("Request to get Provincia : {}", id);
        return provinciaRepository.findById(id).map(provinciaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Provincia : {}", id);
        provinciaRepository.deleteById(id);
    }
}
