package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Producto;
import com.santi.diccionario.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "productoCodigoArancelario")
    ProductoDTO toDto(Producto s);

    @Named("productoCodigoArancelario")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigoArancelario", source = "codigoArancelario")
    ProductoDTO toDtoProductoCodigoArancelario(Producto producto);
}
