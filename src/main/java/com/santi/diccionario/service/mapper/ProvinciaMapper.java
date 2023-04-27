package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Provincia;
import com.santi.diccionario.service.dto.ProvinciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Provincia} and its DTO {@link ProvinciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProvinciaMapper extends EntityMapper<ProvinciaDTO, Provincia> {}
