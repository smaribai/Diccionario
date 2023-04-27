package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Destinatario;
import com.santi.diccionario.service.dto.DestinatarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Destinatario} and its DTO {@link DestinatarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface DestinatarioMapper extends EntityMapper<DestinatarioDTO, Destinatario> {}
