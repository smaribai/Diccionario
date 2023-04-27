package com.santi.diccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pais.
 */
@Entity
@Table(name = "pais")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 2)
    @Column(name = "codigo_pais", length = 2, nullable = false, unique = true)
    private String codigoPais;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_pais", length = 100, nullable = false, unique = true)
    private String nombrePais;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pais id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoPais() {
        return this.codigoPais;
    }

    public Pais codigoPais(String codigoPais) {
        this.setCodigoPais(codigoPais);
        return this;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNombrePais() {
        return this.nombrePais;
    }

    public Pais nombrePais(String nombrePais) {
        this.setNombrePais(nombrePais);
        return this;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pais)) {
            return false;
        }
        return id != null && id.equals(((Pais) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pais{" +
            "id=" + getId() +
            ", codigoPais='" + getCodigoPais() + "'" +
            ", nombrePais='" + getNombrePais() + "'" +
            "}";
    }
}
