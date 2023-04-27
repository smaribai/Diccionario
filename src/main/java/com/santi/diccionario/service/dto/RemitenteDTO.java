package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.Remitente} entity.
 */
public class RemitenteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String idRemitente;

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

    public String getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(String idRemitente) {
        this.idRemitente = idRemitente;
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
        if (!(o instanceof RemitenteDTO)) {
            return false;
        }

        RemitenteDTO remitenteDTO = (RemitenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, remitenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemitenteDTO{" +
            "id=" + getId() +
            ", idRemitente='" + getIdRemitente() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
