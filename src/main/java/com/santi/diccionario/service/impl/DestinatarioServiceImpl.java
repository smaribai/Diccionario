package com.santi.diccionario.service.impl;

import com.santi.diccionario.domain.Destinatario;
import com.santi.diccionario.repository.DestinatarioRepository;
import com.santi.diccionario.service.DestinatarioService;
import com.santi.diccionario.service.dto.DestinatarioDTO;
import com.santi.diccionario.service.mapper.DestinatarioMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Destinatario}.
 */
@Service
@Transactional
public class DestinatarioServiceImpl implements DestinatarioService {

    private final Logger log = LoggerFactory.getLogger(DestinatarioServiceImpl.class);

    private final DestinatarioRepository destinatarioRepository;

    private final DestinatarioMapper destinatarioMapper;

    public DestinatarioServiceImpl(DestinatarioRepository destinatarioRepository, DestinatarioMapper destinatarioMapper) {
        this.destinatarioRepository = destinatarioRepository;
        this.destinatarioMapper = destinatarioMapper;
    }

    @Override
    public DestinatarioDTO save(DestinatarioDTO destinatarioDTO) {
        log.debug("Request to save Destinatario : {}", destinatarioDTO);
        Destinatario destinatario = destinatarioMapper.toEntity(destinatarioDTO);
        destinatario = destinatarioRepository.save(destinatario);
        return destinatarioMapper.toDto(destinatario);
    }

    @Override
    public DestinatarioDTO update(DestinatarioDTO destinatarioDTO) {
        log.debug("Request to save Destinatario : {}", destinatarioDTO);
        Destinatario destinatario = destinatarioMapper.toEntity(destinatarioDTO);
        destinatario = destinatarioRepository.save(destinatario);
        return destinatarioMapper.toDto(destinatario);
    }

    @Override
    public Optional<DestinatarioDTO> partialUpdate(DestinatarioDTO destinatarioDTO) {
        log.debug("Request to partially update Destinatario : {}", destinatarioDTO);

        return destinatarioRepository
            .findById(destinatarioDTO.getId())
            .map(existingDestinatario -> {
                destinatarioMapper.partialUpdate(existingDestinatario, destinatarioDTO);

                return existingDestinatario;
            })
            .map(destinatarioRepository::save)
            .map(destinatarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinatarioDTO> findAll() {
        log.debug("Request to get all Destinatarios");
        return destinatarioRepository.findAll().stream().map(destinatarioMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DestinatarioDTO> findOne(Long id) {
        log.debug("Request to get Destinatario : {}", id);
        return destinatarioRepository.findById(id).map(destinatarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Destinatario : {}", id);
        destinatarioRepository.deleteById(id);
    }
}
