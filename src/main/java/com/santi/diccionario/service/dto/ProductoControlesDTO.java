package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.ProductoControles} entity.
 */
public class ProductoControlesDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String descripcion;

    private ProductoDTO codigoArancelario;

    private ControlDTO idControl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ProductoDTO getCodigoArancelario() {
        return codigoArancelario;
    }

    public void setCodigoArancelario(ProductoDTO codigoArancelario) {
        this.codigoArancelario = codigoArancelario;
    }

    public ControlDTO getIdControl() {
        return idControl;
    }

    public void setIdControl(ControlDTO idControl) {
        this.idControl = idControl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoControlesDTO)) {
            return false;
        }

        ProductoControlesDTO productoControlesDTO = (ProductoControlesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productoControlesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoControlesDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", codigoArancelario=" + getCodigoArancelario() +
            ", idControl=" + getIdControl() +
            "}";
    }
}
