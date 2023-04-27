package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Clasificaciones;
import com.santi.diccionario.domain.Cliente;
import com.santi.diccionario.domain.Destinatario;
import com.santi.diccionario.domain.Divisa;
import com.santi.diccionario.domain.Idioma;
import com.santi.diccionario.domain.Pais;
import com.santi.diccionario.domain.Provincia;
import com.santi.diccionario.domain.Remitente;
import com.santi.diccionario.service.dto.ClasificacionesDTO;
import com.santi.diccionario.service.dto.ClienteDTO;
import com.santi.diccionario.service.dto.DestinatarioDTO;
import com.santi.diccionario.service.dto.DivisaDTO;
import com.santi.diccionario.service.dto.IdiomaDTO;
import com.santi.diccionario.service.dto.PaisDTO;
import com.santi.diccionario.service.dto.ProvinciaDTO;
import com.santi.diccionario.service.dto.RemitenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clasificaciones} and its DTO {@link ClasificacionesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClasificacionesMapper extends EntityMapper<ClasificacionesDTO, Clasificaciones> {
    @Mapping(target = "paisOrigen", source = "paisOrigen", qualifiedByName = "paisNombrePais")
    @Mapping(target = "paisDestino", source = "paisDestino", qualifiedByName = "paisNombrePais")
    @Mapping(target = "divisa", source = "divisa", qualifiedByName = "divisaNombreDivisa")
    @Mapping(target = "idioma", source = "idioma", qualifiedByName = "idiomaNombre")
    @Mapping(target = "refCliente", source = "refCliente", qualifiedByName = "clienteNombre")
    @Mapping(target = "provinciaDestino", source = "provinciaDestino", qualifiedByName = "provinciaNombreProvincia")
    @Mapping(target = "idRemitente", source = "idRemitente", qualifiedByName = "remitenteNombre")
    @Mapping(target = "idDestinatario", source = "idDestinatario", qualifiedByName = "destinatarioNombre")
    ClasificacionesDTO toDto(Clasificaciones s);

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
