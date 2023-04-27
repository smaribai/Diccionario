package com.santi.diccionario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "codigo_arancelario", length = 20, nullable = false, unique = true)
    private String codigoArancelario;

    @Column(name = "nivel")
    private Integer nivel;

    @Size(max = 15)
    @Column(name = "c_n_code", length = 15)
    private String cNCode;

    @Column(name = "longitud_cn_code")
    private Integer longitudCNCode;

    @NotNull
    @Size(max = 2000)
    @Column(name = "descripcion", length = 2000, nullable = false)
    private String descripcion;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Producto parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoArancelario() {
        return this.codigoArancelario;
    }

    public Producto codigoArancelario(String codigoArancelario) {
        this.setCodigoArancelario(codigoArancelario);
        return this;
    }

    public void setCodigoArancelario(String codigoArancelario) {
        this.codigoArancelario = codigoArancelario;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public Producto nivel(Integer nivel) {
        this.setNivel(nivel);
        return this;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getcNCode() {
        return this.cNCode;
    }

    public Producto cNCode(String cNCode) {
        this.setcNCode(cNCode);
        return this;
    }

    public void setcNCode(String cNCode) {
        this.cNCode = cNCode;
    }

    public Integer getLongitudCNCode() {
        return this.longitudCNCode;
    }

    public Producto longitudCNCode(Integer longitudCNCode) {
        this.setLongitudCNCode(longitudCNCode);
        return this;
    }

    public void setLongitudCNCode(Integer longitudCNCode) {
        this.longitudCNCode = longitudCNCode;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Producto getParent() {
        return this.parent;
    }

    public void setParent(Producto producto) {
        this.parent = producto;
    }

    public Producto parent(Producto producto) {
        this.setParent(producto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", codigoArancelario='" + getCodigoArancelario() + "'" +
            ", nivel=" + getNivel() +
            ", cNCode='" + getcNCode() + "'" +
            ", longitudCNCode=" + getLongitudCNCode() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
