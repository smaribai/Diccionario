package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Cliente;
import com.santi.diccionario.domain.Pais;
import com.santi.diccionario.domain.TipoCliente;
import com.santi.diccionario.service.dto.ClienteDTO;
import com.santi.diccionario.service.dto.PaisDTO;
import com.santi.diccionario.service.dto.TipoClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "tipoCliente", source = "tipoCliente", qualifiedByName = "tipoClienteNombre")
    @Mapping(target = "pais", source = "pais", qualifiedByName = "paisCodigoPais")
    ClienteDTO toDto(Cliente s);

    @Named("tipoClienteNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    TipoClienteDTO toDtoTipoClienteNombre(TipoCliente tipoCliente);

    @Named("paisCodigoPais")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigoPais", source = "codigoPais")
    PaisDTO toDtoPaisCodigoPais(Pais pais);
}
