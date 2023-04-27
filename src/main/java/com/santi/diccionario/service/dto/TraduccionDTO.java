package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.Traduccion} entity.
 */
public class TraduccionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String textoOrigen;

    @NotNull
    @Size(max = 150)
    private String textoDestino;

    private IdiomaDTO idiomaOrigen;

    private IdiomaDTO idiomaDestino;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextoOrigen() {
        return textoOrigen;
    }

    public void setTextoOrigen(String textoOrigen) {
        this.textoOrigen = textoOrigen;
    }

    public String getTextoDestino() {
        return textoDestino;
    }

    public void setTextoDestino(String textoDestino) {
        this.textoDestino = textoDestino;
    }

    public IdiomaDTO getIdiomaOrigen() {
        return idiomaOrigen;
    }

    public void setIdiomaOrigen(IdiomaDTO idiomaOrigen) {
        this.idiomaOrigen = idiomaOrigen;
    }

    public IdiomaDTO getIdiomaDestino() {
        return idiomaDestino;
    }

    public void setIdiomaDestino(IdiomaDTO idiomaDestino) {
        this.idiomaDestino = idiomaDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraduccionDTO)) {
            return false;
        }

        TraduccionDTO traduccionDTO = (TraduccionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, traduccionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TraduccionDTO{" +
            "id=" + getId() +
            ", textoOrigen='" + getTextoOrigen() + "'" +
            ", textoDestino='" + getTextoDestino() + "'" +
            ", idiomaOrigen=" + getIdiomaOrigen() +
            ", idiomaDestino=" + getIdiomaDestino() +
            "}";
    }
}
