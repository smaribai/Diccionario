package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.Control;
import com.santi.diccionario.repository.ControlRepository;
import com.santi.diccionario.service.ControlService;
import com.santi.diccionario.service.dto.ControlDTO;
import com.santi.diccionario.service.mapper.ControlMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Control}.
 */
@Service
@Transactional
public class ControlServiceImpl implements ControlService {

    private final Logger log = LoggerFactory.getLogger(ControlServiceImpl.class);

    private final ControlRepository controlRepository;

    private final ControlMapper controlMapper;

    public ControlServiceImpl(ControlRepository controlRepository, ControlMapper controlMapper) {
        this.controlRepository = controlRepository;
        this.controlMapper = controlMapper;
    }

    @Override
    public ControlDTO save(ControlDTO controlDTO) {
        log.debug("Request to save Control : {}", controlDTO);
        Control control = controlMapper.toEntity(controlDTO);
        control = controlRepository.save(control);
        return controlMapper.toDto(control);
    }

    @Override
    public ControlDTO update(ControlDTO controlDTO) {
        log.debug("Request to save Control : {}", controlDTO);
        Control control = controlMapper.toEntity(controlDTO);
        control = controlRepository.save(control);
        return controlMapper.toDto(control);
    }

    @Override
    public Optional<ControlDTO> partialUpdate(ControlDTO controlDTO) {
        log.debug("Request to partially update Control : {}", controlDTO);

        return controlRepository
            .findById(controlDTO.getId())
            .map(existingControl -> {
                controlMapper.partialUpdate(existingControl, controlDTO);

                return existingControl;
            })
            .map(controlRepository::save)
            .map(controlMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Controls");
        return controlRepository.findAll(pageable).map(controlMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ControlDTO> findOne(Long id) {
        log.debug("Request to get Control : {}", id);
        return controlRepository.findById(id).map(controlMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Control : {}", id);
        controlRepository.deleteById(id);
    }
}
