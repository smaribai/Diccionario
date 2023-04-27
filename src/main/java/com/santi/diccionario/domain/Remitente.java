package com.santi.diccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Remitente.
 */
@Entity
@Table(name = "remitente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Remitente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "id_remitente", length = 20, nullable = false, unique = true)
    private String idRemitente;

    @NotNull
    @Size(max = 70)
    @Column(name = "nombre", length = 70, nullable = false, unique = true)
    private String nombre;

    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Size(max = 200)
    @Column(name = "direccion", length = 200)
    private String direccion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Remitente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdRemitente() {
        return this.idRemitente;
    }

    public Remitente idRemitente(String idRemitente) {
        this.setIdRemitente(idRemitente);
        return this;
    }

    public void setIdRemitente(String idRemitente) {
        this.idRemitente = idRemitente;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Remitente nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Remitente descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Remitente direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remitente)) {
            return false;
        }
        return id != null && id.equals(((Remitente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Remitente{" +
            "id=" + getId() +
            ", idRemitente='" + getIdRemitente() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
