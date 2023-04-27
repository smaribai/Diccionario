package com.santi.diccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Traduccion.
 */
@Entity
@Table(name = "traduccion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Traduccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "texto_origen", length = 150, nullable = false)
    private String textoOrigen;

    @NotNull
    @Size(max = 150)
    @Column(name = "texto_destino", length = 150, nullable = false)
    private String textoDestino;

    @ManyToOne(optional = false)
    @NotNull
    private Idioma idiomaOrigen;

    @ManyToOne(optional = false)
    @NotNull
    private Idioma idiomaDestino;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Traduccion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextoOrigen() {
        return this.textoOrigen;
    }

    public Traduccion textoOrigen(String textoOrigen) {
        this.setTextoOrigen(textoOrigen);
        return this;
    }

    public void setTextoOrigen(String textoOrigen) {
        this.textoOrigen = textoOrigen;
    }

    public String getTextoDestino() {
        return this.textoDestino;
    }

    public Traduccion textoDestino(String textoDestino) {
        this.setTextoDestino(textoDestino);
        return this;
    }

    public void setTextoDestino(String textoDestino) {
        this.textoDestino = textoDestino;
    }

    public Idioma getIdiomaOrigen() {
        return this.idiomaOrigen;
    }

    public void setIdiomaOrigen(Idioma idioma) {
        this.idiomaOrigen = idioma;
    }

    public Traduccion idiomaOrigen(Idioma idioma) {
        this.setIdiomaOrigen(idioma);
        return this;
    }

    public Idioma getIdiomaDestino() {
        return this.idiomaDestino;
    }

    public void setIdiomaDestino(Idioma idioma) {
        this.idiomaDestino = idioma;
    }

    public Traduccion idiomaDestino(Idioma idioma) {
        this.setIdiomaDestino(idioma);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Traduccion)) {
            return false;
        }
        return id != null && id.equals(((Traduccion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Traduccion{" +
            "id=" + getId() +
            ", textoOrigen='" + getTextoOrigen() + "'" +
            ", textoDestino='" + getTextoDestino() + "'" +
            "}";
    }
}
