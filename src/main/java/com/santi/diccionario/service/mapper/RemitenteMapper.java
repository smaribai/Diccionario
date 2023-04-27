package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Remitente;
import com.santi.diccionario.service.dto.RemitenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Remitente} and its DTO {@link RemitenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface RemitenteMapper extends EntityMapper<RemitenteDTO, Remitente> {}
