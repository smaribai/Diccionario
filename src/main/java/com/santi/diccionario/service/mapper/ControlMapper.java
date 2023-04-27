package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Control;
import com.santi.diccionario.service.dto.ControlDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Control} and its DTO {@link ControlDTO}.
 */
@Mapper(componentModel = "spring")
public interface ControlMapper extends EntityMapper<ControlDTO, Control> {}
