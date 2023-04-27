package com.santi.diccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Destinatario.
 */
@Entity
@Table(name = "destinatario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Destinatario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "id_destinatario", length = 20, nullable = false, unique = true)
    private String idDestinatario;

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

    public Destinatario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdDestinatario() {
        return this.idDestinatario;
    }

    public Destinatario idDestinatario(String idDestinatario) {
        this.setIdDestinatario(idDestinatario);
        return this;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Destinatario nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Destinatario descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Destinatario direccion(String direccion) {
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
        if (!(o instanceof Destinatario)) {
            return false;
        }
        return id != null && id.equals(((Destinatario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Destinatario{" +
            "id=" + getId() +
            ", idDestinatario='" + getIdDestinatario() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
