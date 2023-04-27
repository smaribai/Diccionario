package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.TipoCliente;
import com.santi.diccionario.service.dto.TipoClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoCliente} and its DTO {@link TipoClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoClienteMapper extends EntityMapper<TipoClienteDTO, TipoCliente> {}
