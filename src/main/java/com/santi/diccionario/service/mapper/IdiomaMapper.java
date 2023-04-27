package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Idioma;
import com.santi.diccionario.service.dto.IdiomaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Idioma} and its DTO {@link IdiomaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IdiomaMapper extends EntityMapper<IdiomaDTO, Idioma> {}
