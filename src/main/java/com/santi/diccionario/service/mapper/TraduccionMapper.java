package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Idioma;
import com.santi.diccionario.domain.Traduccion;
import com.santi.diccionario.service.dto.IdiomaDTO;
import com.santi.diccionario.service.dto.TraduccionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Traduccion} and its DTO {@link TraduccionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TraduccionMapper extends EntityMapper<TraduccionDTO, Traduccion> {
    @Mapping(target = "idiomaOrigen", source = "idiomaOrigen", qualifiedByName = "idiomaNombre")
    @Mapping(target = "idiomaDestino", source = "idiomaDestino", qualifiedByName = "idiomaNombre")
    TraduccionDTO toDto(Traduccion s);

    @Named("idiomaNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    IdiomaDTO toDtoIdiomaNombre(Idioma idioma);
}
