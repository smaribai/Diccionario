package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Pais;
import com.santi.diccionario.service.dto.PaisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pais} and its DTO {@link PaisDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaisMapper extends EntityMapper<PaisDTO, Pais> {}
