package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.Destinatario} entity.
 */
public class DestinatarioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String idDestinatario;

    @NotNull
    @Size(max = 70)
    private String nombre;

    @Size(max = 200)
    private String descripcion;

    @Size(max = 200)
    private String direccion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DestinatarioDTO)) {
            return false;
        }

        DestinatarioDTO destinatarioDTO = (DestinatarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, destinatarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DestinatarioDTO{" +
            "id=" + getId() +
            ", idDestinatario='" + getIdDestinatario() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
