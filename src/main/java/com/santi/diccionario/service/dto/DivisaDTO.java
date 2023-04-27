package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.Divisa} entity.
 */
public class DivisaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 3)
    private String codigoDivisa;

    @NotNull
    @Size(max = 100)
    private String nombreDivisa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoDivisa() {
        return codigoDivisa;
    }

    public void setCodigoDivisa(String codigoDivisa) {
        this.codigoDivisa = codigoDivisa;
    }

    public String getNombreDivisa() {
        return nombreDivisa;
    }

    public void setNombreDivisa(String nombreDivisa) {
        this.nombreDivisa = nombreDivisa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DivisaDTO)) {
            return false;
        }

        DivisaDTO divisaDTO = (DivisaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, divisaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DivisaDTO{" +
            "id=" + getId() +
            ", codigoDivisa='" + getCodigoDivisa() + "'" +
            ", nombreDivisa='" + getNombreDivisa() + "'" +
            "}";
    }
}
