package com.santi.diccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Divisa.
 */
@Entity
@Table(name = "divisa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Divisa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "codigo_divisa", length = 3, nullable = false, unique = true)
    private String codigoDivisa;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_divisa", length = 100, nullable = false, unique = true)
    private String nombreDivisa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Divisa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoDivisa() {
        return this.codigoDivisa;
    }

    public Divisa codigoDivisa(String codigoDivisa) {
        this.setCodigoDivisa(codigoDivisa);
        return this;
    }

    public void setCodigoDivisa(String codigoDivisa) {
        this.codigoDivisa = codigoDivisa;
    }

    public String getNombreDivisa() {
        return this.nombreDivisa;
    }

    public Divisa nombreDivisa(String nombreDivisa) {
        this.setNombreDivisa(nombreDivisa);
        return this;
    }

    public void setNombreDivisa(String nombreDivisa) {
        this.nombreDivisa = nombreDivisa;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Divisa)) {
            return false;
        }
        return id != null && id.equals(((Divisa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Divisa{" +
            "id=" + getId() +
            ", codigoDivisa='" + getCodigoDivisa() + "'" +
            ", nombreDivisa='" + getNombreDivisa() + "'" +
            "}";
    }
}
