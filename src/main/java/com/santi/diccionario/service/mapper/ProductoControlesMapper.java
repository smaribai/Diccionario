package com.santi.diccionario.service.mapper;

import com.santi.diccionario.domain.Control;
import com.santi.diccionario.domain.Producto;
import com.santi.diccionario.domain.ProductoControles;
import com.santi.diccionario.service.dto.ControlDTO;
import com.santi.diccionario.service.dto.ProductoControlesDTO;
import com.santi.diccionario.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductoControles} and its DTO {@link ProductoControlesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductoControlesMapper extends EntityMapper<ProductoControlesDTO, ProductoControles> {
    @Mapping(target = "codigoArancelario", source = "codigoArancelario", qualifiedByName = "productoCodigoArancelario")
    @Mapping(target = "idControl", source = "idControl", qualifiedByName = "controlNombre")
    ProductoControlesDTO toDto(ProductoControles s);

    @Named("productoCodigoArancelario")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigoArancelario", source = "codigoArancelario")
    ProductoDTO toDtoProductoCodigoArancelario(Producto producto);

    @Named("controlNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    ControlDTO toDtoControlNombre(Control control);
}
