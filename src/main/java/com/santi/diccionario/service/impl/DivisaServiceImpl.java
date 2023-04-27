package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.Divisa;
import com.santi.diccionario.repository.DivisaRepository;
import com.santi.diccionario.service.DivisaService;
import com.santi.diccionario.service.dto.DivisaDTO;
import com.santi.diccionario.service.mapper.DivisaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Divisa}.
 */
@Service
@Transactional
public class DivisaServiceImpl implements DivisaService {

    private final Logger log = LoggerFactory.getLogger(DivisaServiceImpl.class);

    private final DivisaRepository divisaRepository;

    private final DivisaMapper divisaMapper;

    public DivisaServiceImpl(DivisaRepository divisaRepository, DivisaMapper divisaMapper) {
        this.divisaRepository = divisaRepository;
        this.divisaMapper = divisaMapper;
    }

    @Override
    public DivisaDTO save(DivisaDTO divisaDTO) {
        log.debug("Request to save Divisa : {}", divisaDTO);
        Divisa divisa = divisaMapper.toEntity(divisaDTO);
        divisa = divisaRepository.save(divisa);
        return divisaMapper.toDto(divisa);
    }

    @Override
    public DivisaDTO update(DivisaDTO divisaDTO) {
        log.debug("Request to save Divisa : {}", divisaDTO);
        Divisa divisa = divisaMapper.toEntity(divisaDTO);
        divisa = divisaRepository.save(divisa);
        return divisaMapper.toDto(divisa);
    }

    @Override
    public Optional<DivisaDTO> partialUpdate(DivisaDTO divisaDTO) {
        log.debug("Request to partially update Divisa : {}", divisaDTO);

        return divisaRepository
            .findById(divisaDTO.getId())
            .map(existingDivisa -> {
                divisaMapper.partialUpdate(existingDivisa, divisaDTO);

                return existingDivisa;
            })
            .map(divisaRepository::save)
            .map(divisaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DivisaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Divisas");
        return divisaRepository.findAll(pageable).map(divisaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DivisaDTO> findOne(Long id) {
        log.debug("Request to get Divisa : {}", id);
        return divisaRepository.findById(id).map(divisaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Divisa : {}", id);
        divisaRepository.deleteById(id);
    }
}
