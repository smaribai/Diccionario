package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.TipoCliente} entity.
 */
public class TipoClienteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String codigo;

    @NotNull
    @Size(max = 100)
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoClienteDTO)) {
            return false;
        }

        TipoClienteDTO tipoClienteDTO = (TipoClienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoClienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoClienteDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
