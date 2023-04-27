package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Clasificar;
import com.santi.diccionario.domain.Cliente;
import com.santi.diccionario.domain.Destinatario;
import com.santi.diccionario.domain.Divisa;
import com.santi.diccionario.domain.Idioma;
import com.santi.diccionario.domain.Pais;
import com.santi.diccionario.domain.Provincia;
import com.santi.diccionario.domain.Remitente;
import com.santi.diccionario.service.dto.ClasificarDTO;
import com.santi.diccionario.service.dto.ClienteDTO;
import com.santi.diccionario.service.dto.DestinatarioDTO;
import com.santi.diccionario.service.dto.DivisaDTO;
import com.santi.diccionario.service.dto.IdiomaDTO;
import com.santi.diccionario.service.dto.PaisDTO;
import com.santi.diccionario.service.dto.ProvinciaDTO;
import com.santi.diccionario.service.dto.RemitenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clasificar} and its DTO {@link ClasificarDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClasificarMapper extends EntityMapper<ClasificarDTO, Clasificar> {
    @Mapping(target = "paisOrigen", source = "paisOrigen", qualifiedByName = "paisNombrePais")
    @Mapping(target = "paisDestino", source = "paisDestino", qualifiedByName = "paisNombrePais")
    @Mapping(target = "divisa", source = "divisa", qualifiedByName = "divisaNombreDivisa")
    @Mapping(target = "idioma", source = "idioma", qualifiedByName = "idiomaNombre")
    @Mapping(target = "refCliente", source = "refCliente", qualifiedByName = "clienteNombre")
    @Mapping(target = "provinciaDestino", source = "provinciaDestino", qualifiedByName = "provinciaNombreProvincia")
    @Mapping(target = "idRemitente", source = "idRemitente", qualifiedByName = "remitenteNombre")
    @Mapping(target = "idDestinatario", source = "idDestinatario", qualifiedByName = "destinatarioNombre")
    ClasificarDTO toDto(Clasificar s);

    @Named("paisNombrePais")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombrePais", source = "nombrePais")
    PaisDTO toDtoPaisNombrePais(Pais pais);

    @Named("divisaNombreDivisa")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreDivisa", source = "nombreDivisa")
    DivisaDTO toDtoDivisaNombreDivisa(Divisa divisa);

    @Named("idiomaNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    IdiomaDTO toDtoIdiomaNombre(Idioma idioma);

    @Named("clienteNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    ClienteDTO toDtoClienteNombre(Cliente cliente);

    @Named("provinciaNombreProvincia")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreProvincia", source = "nombreProvincia")
    ProvinciaDTO toDtoProvinciaNombreProvincia(Provincia provincia);

    @Named("remitenteNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    RemitenteDTO toDtoRemitenteNombre(Remitente remitente);

    @Named("destinatarioNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    DestinatarioDTO toDtoDestinatarioNombre(Destinatario destinatario);
}
