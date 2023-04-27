package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.Producto} entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String codigoArancelario;

    private Integer nivel;

    @Size(max = 15)
    private String cNCode;

    private Integer longitudCNCode;

    @NotNull
    @Size(max = 2000)
    private String descripcion;

    private ProductoDTO parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoArancelario() {
        return codigoArancelario;
    }

    public void setCodigoArancelario(String codigoArancelario) {
        this.codigoArancelario = codigoArancelario;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getcNCode() {
        return cNCode;
    }

    public void setcNCode(String cNCode) {
        this.cNCode = cNCode;
    }

    public Integer getLongitudCNCode() {
        return longitudCNCode;
    }

    public void setLongitudCNCode(Integer longitudCNCode) {
        this.longitudCNCode = longitudCNCode;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ProductoDTO getParent() {
        return parent;
    }

    public void setParent(ProductoDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoDTO)) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", codigoArancelario='" + getCodigoArancelario() + "'" +
            ", nivel=" + getNivel() +
            ", cNCode='" + getcNCode() + "'" +
            ", longitudCNCode=" + getLongitudCNCode() +
            ", descripcion='" + getDescripcion() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
