package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Divisa;
import com.santi.diccionario.service.dto.DivisaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Divisa} and its DTO {@link DivisaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DivisaMapper extends EntityMapper<DivisaDTO, Divisa> {}
