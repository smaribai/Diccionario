package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.Provincia} entity.
 */
public class ProvinciaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 3)
    private String codigoProvincia;

    @NotNull
    @Size(max = 30)
    private String nombreProvincia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(String codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvinciaDTO)) {
            return false;
        }

        ProvinciaDTO provinciaDTO = (ProvinciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, provinciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvinciaDTO{" +
            "id=" + getId() +
            ", codigoProvincia='" + getCodigoProvincia() + "'" +
            ", nombreProvincia='" + getNombreProvincia() + "'" +
            "}";
    }
}
